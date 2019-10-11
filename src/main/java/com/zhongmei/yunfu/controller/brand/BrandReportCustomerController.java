package com.zhongmei.yunfu.controller.brand;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.model.CustomerModel;
import com.zhongmei.yunfu.controller.model.ShopSearchModel;
import com.zhongmei.yunfu.controller.model.TradeModel;
import com.zhongmei.yunfu.controller.model.excel.ExcelData;
import com.zhongmei.yunfu.controller.model.excel.ExcelUtils;
import com.zhongmei.yunfu.domain.entity.BrandEntity;
import com.zhongmei.yunfu.domain.entity.CommercialEntity;
import com.zhongmei.yunfu.domain.entity.CustomerReport;
import com.zhongmei.yunfu.service.BrandService;
import com.zhongmei.yunfu.service.CommercialService;
import com.zhongmei.yunfu.service.CustomerService;
import com.zhongmei.yunfu.util.DateFormatUtil;
import com.zhongmei.yunfu.util.ToolsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/internal/brand/report")
public class BrandReportCustomerController {

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
            queryShopMessage(model, mCustomerModel);

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

            List<Integer> listPOSCount = new LinkedList<>();
            List<Integer> listWxCount = new LinkedList<>();
            List<String> listCreateDate = new LinkedList<>();


            List<Integer> listCustomerCount = new ArrayList<>();

            for (CustomerReport d : listDate) {


                Integer posCustomer = 0;
                for (CustomerReport cr : listCustomerReport) {
                    if (DateFormatUtil.format(d.getCreateDate(), DateFormatUtil.FORMAT_FULL_DATE).equals(DateFormatUtil.format(cr.getCreateDate(), DateFormatUtil.FORMAT_FULL_DATE))) {
                        posCustomer = cr.getCount();
                        customerCount += posCustomer;
                        break;
                    }
                }
                listPOSCount.add(posCustomer);

                Integer wxCustomer = 0;
                for (CustomerReport wcr : listWXCustomerReport) {
                    if (DateFormatUtil.format(d.getCreateDate(), DateFormatUtil.FORMAT_FULL_DATE).equals(DateFormatUtil.format(wcr.getCreateDate(), DateFormatUtil.FORMAT_FULL_DATE))) {
                        wxCustomer = wcr.getCount();
                        customerCount += wxCustomer;
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

            model.addAttribute("listCustomerCount", listCustomerCount);

            model.addAttribute("customerModel", mCustomerModel);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "brand_report_customer";
    }

    /**
     * 获取各门店次卡销售情况--获取分页门店数据
     * @param mTradeModel
     * @return
     * @throws Exception
     */
    public Page<CommercialEntity> queryCommerical(TradeModel mTradeModel) throws Exception{
        ShopSearchModel mShopSearchModel = new ShopSearchModel ();
        mShopSearchModel.setBrandIdenty(mTradeModel.getBrandIdenty());
        mShopSearchModel.setShopIdenty(mTradeModel.getShopIdenty());
        mShopSearchModel.setInvalidStatus(1);

        Page<CommercialEntity> listData = mCommercialService.queryCommercialList(mShopSearchModel,mTradeModel.getPageNo(),mTradeModel.getPageSize());

        return listData;
    }

    public Model queryShopMessage(Model model, CustomerModel mCustomerModel) throws Exception {

        List<CommercialEntity> listCommercial = mCommercialService.queryCommercialByBrandId(mCustomerModel.getBrandIdenty());

        model.addAttribute("listCommercial", listCommercial);
        return model;
    }

    @RequestMapping("/customerShop/excel")
    public void exportExcel(HttpServletResponse response, CustomerModel mCustomerModel) throws Exception{

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
