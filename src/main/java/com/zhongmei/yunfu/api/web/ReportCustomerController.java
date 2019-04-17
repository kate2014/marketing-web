package com.zhongmei.yunfu.api.web;

import com.zhongmei.yunfu.controller.model.TradeModel;
import com.zhongmei.yunfu.controller.model.excel.ExcelData;
import com.zhongmei.yunfu.controller.model.excel.ExcelUtils;
import com.zhongmei.yunfu.domain.entity.DishReport;
import com.zhongmei.yunfu.util.DateFormatUtil;
import com.zhongmei.yunfu.controller.model.CustomerModel;
import com.zhongmei.yunfu.domain.entity.BrandEntity;
import com.zhongmei.yunfu.domain.entity.CommercialEntity;
import com.zhongmei.yunfu.domain.entity.CustomerReport;
import com.zhongmei.yunfu.service.BrandService;
import com.zhongmei.yunfu.service.CommercialService;
import com.zhongmei.yunfu.service.CustomerService;
import com.zhongmei.yunfu.util.ToolsUtil;
import org.jooq.util.derby.sys.Sys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/internal/report")
public class ReportCustomerController {

    @Autowired
    CustomerService customerService;
    @Autowired
    BrandService mBrandService;
    @Autowired
    CommercialService mCommercialService;

    /**
     * 会员报表
     *
     * @return
     */
    @RequestMapping("/customerReport")
    public String reportAddCustomer(Model model, CustomerModel mCustomerModel) {
        try {
            //获取门店品牌和门店编号
//            queryShopMessage(model, mCustomerModel);

            Date start = null;
            Date end = null;

            if (mCustomerModel.getStartDate() == null) {
                Calendar c = Calendar.getInstance();
                //过去15天
                c.setTime(new Date());
                c.add(Calendar.DATE, -15);
                start = c.getTime();
                String temp = DateFormatUtil.format(start, DateFormatUtil.FORMAT_FULL_DATE);
                start = DateFormatUtil.parseDate(temp, DateFormatUtil.FORMAT_FULL_DATE);

                mCustomerModel.setStartDate(temp);
            } else {
                start = DateFormatUtil.parseDate(mCustomerModel.getStartDate(), DateFormatUtil.FORMAT_FULL_DATE);
            }
            if (mCustomerModel.getEndDate() == null) {
                end = new Date();
                mCustomerModel.setEndDate(DateFormatUtil.format(end, DateFormatUtil.FORMAT_FULL_DATE));
            } else {
                end = DateFormatUtil.parseDate(mCustomerModel.getEndDate(), DateFormatUtil.FORMAT_FULL_DATE);
            }

            List<CustomerReport> listDate = customerService.queryCustomerReport(mCustomerModel.getBrandIdenty(), mCustomerModel.getShopIdenty(), start, end, null);

            Integer sourceId = 1;
            List<CustomerReport> listCustomerReport = customerService.queryCustomerReport(mCustomerModel.getBrandIdenty(), mCustomerModel.getShopIdenty(), start, end, sourceId);
            sourceId = 2;
            List<CustomerReport> listWXCustomerReport = customerService.queryCustomerReport(mCustomerModel.getBrandIdenty(), mCustomerModel.getShopIdenty(), start, end, sourceId);

            //某一时间点会员总数
            Integer customerCount = customerService.queryCustomerCount(mCustomerModel.getBrandIdenty(), mCustomerModel.getShopIdenty(), start, null);
            //某一时间点pos会员总数
            Integer posCustomerCount = customerService.queryCustomerCount(mCustomerModel.getBrandIdenty(), mCustomerModel.getShopIdenty(), start, 1);
            //某一时间点微信会员总数
            Integer wxCustomerCount = customerService.queryCustomerCount(mCustomerModel.getBrandIdenty(), mCustomerModel.getShopIdenty(), start, 2);

            List<Integer> listPOSCount = new LinkedList<>();
            List<Integer> listWxCount = new LinkedList<>();
            List<String> listCreateDate = new LinkedList<>();

            //获取会员增量来源分步数据
            Integer posCustomerAddCount = 0;
            Integer wxCustomerAddCount = 0;


            List<Integer> listCustomerCount = new ArrayList<>();
            List<Integer> posListCustomerCount = new ArrayList<>();
            List<Integer> wxListCustomerCount = new ArrayList<>();

            for (CustomerReport d : listDate) {


                Integer posCustomer = 0;
                for (CustomerReport cr : listCustomerReport) {
                    if (DateFormatUtil.format(d.getCreateDate(), DateFormatUtil.FORMAT_FULL_DATE).equals(DateFormatUtil.format(cr.getCreateDate(), DateFormatUtil.FORMAT_FULL_DATE))) {
                        posCustomer = cr.getCount();
                        posCustomerAddCount += posCustomer;

                        customerCount += posCustomer;
                        posCustomerCount += posCustomer;
                        break;
                    }
                }
                listPOSCount.add(posCustomer);

                Integer wxCustomer = 0;
                for (CustomerReport wcr : listWXCustomerReport) {
                    if (DateFormatUtil.format(d.getCreateDate(), DateFormatUtil.FORMAT_FULL_DATE).equals(DateFormatUtil.format(wcr.getCreateDate(), DateFormatUtil.FORMAT_FULL_DATE))) {
                        wxCustomer = wcr.getCount();
                        wxCustomerAddCount += wxCustomer;

                        customerCount += wxCustomer;
                        wxCustomerCount += wxCustomer;
                        break;
                    }
                }
                listWxCount.add(wxCustomer);
                //每个时段会员总数
                listCustomerCount.add(customerCount);
                String dateValue = DateFormatUtil.format(d.getCreateDate(), "MM月dd日");
                listCreateDate.add(dateValue);
            }

            model.addAttribute("listPOSCount", listPOSCount);
            model.addAttribute("listWxCount", listWxCount);
            model.addAttribute("listCreateDate", listCreateDate);

            model.addAttribute("posCustomerCount", posCustomerCount);
            model.addAttribute("wxCustomerCount", wxCustomerCount);

            model.addAttribute("listCustomerCount", listCustomerCount);
            model.addAttribute("posCustomerTotal", posCustomerCount);
            model.addAttribute("wxCustomerAddTotal", wxCustomerAddCount);

            model.addAttribute("customerModel", mCustomerModel);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "report_customer";
    }

    public Model queryShopMessage(Model model, CustomerModel mCustomerModel) throws Exception {

        BrandEntity brand = mBrandService.queryBrandByAppId(mCustomerModel.getBrandIdenty());

        List<CommercialEntity> listCommercial = mCommercialService.queryCommercialByBrandId(brand.getId());

        model.addAttribute("brand", brand);
        model.addAttribute("listCommercial", listCommercial);
        return model;
    }

    @RequestMapping("/customerShop")
    public String customerShop(Model model, CustomerModel mCustomerModel){
        try {

            Date start = null;
            Date end = null;
            //设置默认查询时间
            if (mCustomerModel.getStartDate() == null) {
                Calendar c = Calendar.getInstance();
                //过去15天
                c.setTime(new Date());
                c.add(Calendar.DATE, -15);
                start = c.getTime();
                String temp = DateFormatUtil.format(start, DateFormatUtil.FORMAT_FULL_DATE);
                start = DateFormatUtil.parseDate(temp, DateFormatUtil.FORMAT_FULL_DATE);

                mCustomerModel.setStartDate(temp);
            }
            if (mCustomerModel.getEndDate() == null) {
                end = new Date();
                mCustomerModel.setEndDate(DateFormatUtil.format(end, DateFormatUtil.FORMAT_FULL_DATE));
            }

            //会员到店情况
            List<CustomerReport> listData = customerService.customerShopReport(mCustomerModel);

            List<String> listTradeDate = new LinkedList<>();
            List<Integer> listCount = new LinkedList<>();
            List<BigDecimal> listTradeAmount = new LinkedList<>();

            Long maxCount = 0l;
            Long maxAmount = 0l;

            for(CustomerReport entity : listData){

                listTradeDate.add(entity.getTradeDate());
                listCount.add(entity.getCount());
                listTradeAmount.add(entity.getTradeAmount());

                if(maxCount < entity.getCount()){
                    maxCount = entity.getCount().longValue();
                }
                if(maxAmount < entity.getTradeAmount().longValue()){
                    maxAmount = entity.getTradeAmount().longValue();
                }
            }

            maxCount = ToolsUtil.getMaxData(maxCount);
            maxAmount = ToolsUtil.getMaxData(maxAmount);

            model.addAttribute("maxCount", maxCount);
            model.addAttribute("intervalCount", maxCount / 10);
            model.addAttribute("maxAmount", maxAmount);
            model.addAttribute("intervalAmount", maxAmount / 10);

            model.addAttribute("mCustomerModel", mCustomerModel);

            model.addAttribute("listTradeDate", listTradeDate);
            model.addAttribute("listCount", listCount);
            model.addAttribute("listTradeAmount", listTradeAmount);


            //会员到店消费详情
            Map<Long,CustomerReport>tempMap = new LinkedHashMap<>();

            List<CustomerReport> listDetail = customerService.customerShopDetailReport(mCustomerModel);
            for(CustomerReport entity : listDetail){
                CustomerReport cr = tempMap.get(entity.getId());
                if(cr != null){
                    entity.setDishName(cr.getDishName()+","+entity.getDishName());
                }
                tempMap.put(entity.getId(),entity);
            }

            listDetail.clear();

            for (CustomerReport value : tempMap.values()) {
                listDetail.add(value);
            }
            model.addAttribute("listTradeDetail", listDetail);


        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

        return "report_customer_shop";
    }

    @RequestMapping("/export/excel")
    public void exportExcel(HttpServletResponse response, CustomerModel mCustomerModel) throws Exception{

        List<CustomerReport> listDetail = customerService.customerShopDetailReport(mCustomerModel);

        ExcelData data = new ExcelData();
        data.setSheetName("会员到店报表");
        List<String> titles = new ArrayList();
        titles.add("序");
        titles.add("会员名称");
        titles.add("消费类型");
        titles.add("消费金额");
        titles.add("消费服务品项");
        titles.add("消费时间");

        data.setTitles(titles);

        List<List<Object>> rows = new ArrayList();
        data.setRows(rows);

        int i = 1;
        if(listDetail != null){
            for (CustomerReport entity : listDetail) {
                List<Object> row = new ArrayList();
                rows.add(row);
                row.add(i++);
                row.add(entity.getCustomerName());
                if(entity.getBusinessType() == 1){
                    row.add("服务消费");
                }else if(entity.getBusinessType() == 2){
                    row.add("余额充值");
                }else if(entity.getBusinessType() == 3){
                    row.add("服务次卡购买");
                }else if(entity.getBusinessType() == 3){
                    row.add("小程序服务购买");
                }
                row.add(entity.getTradeAmount());
                row.add(entity.getDishName());
                row.add(entity.getTradeDate());
            }
        }

        SimpleDateFormat fdate = new SimpleDateFormat("yyyyMMdd");
        String fileName = String.format("会员到店报表-%s.xls", fdate.format(new Date()));
        ExcelUtils.exportExcel(response, fileName, data);
    }

}
