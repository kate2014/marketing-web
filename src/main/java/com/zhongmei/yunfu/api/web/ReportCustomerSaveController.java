package com.zhongmei.yunfu.api.web;


import com.zhongmei.yunfu.controller.model.TradeModel;
import com.zhongmei.yunfu.controller.model.excel.ExcelData;
import com.zhongmei.yunfu.controller.model.excel.ExcelUtils;
import com.zhongmei.yunfu.domain.entity.BrandEntity;
import com.zhongmei.yunfu.domain.entity.CommercialEntity;
import com.zhongmei.yunfu.domain.entity.CustomerSaveReport;
import com.zhongmei.yunfu.domain.entity.DishReport;
import com.zhongmei.yunfu.service.*;
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
@RequestMapping("/internal/saveReport")
public class ReportCustomerSaveController {

    @Autowired
    TradeService mTradeService;
    @Autowired
    TradeItemService mTradeItemService;
    @Autowired
    BrandService mBrandService;
    @Autowired
    CommercialService mCommercialService;
    @Autowired
    CustomerStoredService mCustomerStoredService;

    @RequestMapping("/customerSave")
    public String cardTimeReport(Model model, TradeModel mTradeModel) {

        try {
            //获取门店品牌和门店编号
//            queryShopMessage(model, mTradeModel);

            //设置默认查询时间
            if (mTradeModel.getStartDate() == null) {
                Calendar c = Calendar.getInstance();
                //过去15天
                c.setTime(new Date());
                c.add(Calendar.DATE, -15);
                Date start = c.getTime();
                String temp = DateFormatUtil.format(start, DateFormatUtil.FORMAT_FULL_DATE);
                mTradeModel.setStartDate(temp);

            }
            if (mTradeModel.getEndDate() == null) {
                mTradeModel.setEndDate(DateFormatUtil.format(new Date(), DateFormatUtil.FORMAT_FULL_DATE));
            }

            List<CustomerSaveReport> listData = mTradeService.customerSaveReport(mTradeModel);

            List<Integer> listTradeCount = new LinkedList<>();
            List<BigDecimal> listSalesAmount = new LinkedList<>();
            List<String> listCreateDate = new LinkedList<>();

            Long maxCount = 0l;
            Long maxAmount = 0l;
            BigDecimal totalAmount = BigDecimal.ZERO;

            for (CustomerSaveReport dp : listData) {

                listTradeCount.add(dp.getTradeCount());
                listSalesAmount.add(dp.getSalesAmount());
                listCreateDate.add(dp.getCreateDate());

                if (maxCount < dp.getTradeCount()) {
                    maxCount = Long.valueOf(dp.getTradeCount());
                }
                if (maxAmount < dp.getSalesAmount().longValue()) {
                    maxAmount = dp.getSalesAmount().longValue();
                }
                totalAmount = totalAmount.add(dp.getSalesAmount());

            }


            maxCount = ToolsUtil.getMaxData(maxCount);
            maxAmount = ToolsUtil.getMaxData(maxAmount);

            model.addAttribute("maxCount", maxCount);
            model.addAttribute("intervalCount", maxCount / 10);
            model.addAttribute("maxAmount", maxAmount);
            model.addAttribute("intervalAmount", maxAmount / 10);


            model.addAttribute("mTradeModel", mTradeModel);
            model.addAttribute("listTradeCount", listTradeCount);
            model.addAttribute("listSalesAmount", listSalesAmount);
            model.addAttribute("listCreateDate", listCreateDate);

            //储值详情
            List<CustomerSaveReport> listDetailData = mTradeService.customerSaveDetailReport(mTradeModel);
            BigDecimal totalGiveAmount = BigDecimal.ZERO;
            for(CustomerSaveReport entity : listDetailData){
                if(entity.getGiveAmount() != null){
                    totalGiveAmount = totalGiveAmount.add(entity.getGiveAmount());
                }
            }
            model.addAttribute("listDetailData", listDetailData);
            model.addAttribute("totalCount", listDetailData.size());
            model.addAttribute("totalAmount", totalAmount);
            model.addAttribute("totalGiveAmount", totalGiveAmount);

        }catch (Exception e){
            e.printStackTrace();

        }


        return "report_customer_save";
    }

    public Model queryShopMessage(Model model, TradeModel mTradeModel) throws Exception {

        BrandEntity brand = mBrandService.queryBrandByAppId(mTradeModel.getBrandIdenty());

        List<CommercialEntity> listCommercial = mCommercialService.queryCommercialByBrandId(brand.getId());

        model.addAttribute("brand", brand);
        model.addAttribute("listCommercial", listCommercial);
        return model;
    }

    @RequestMapping("/export/excel")
    public void exportExcel(HttpServletResponse response, TradeModel mTradeModel) throws Exception{

        //储值详情
        List<CustomerSaveReport> listData = mTradeService.customerSaveDetailReport(mTradeModel);

        ExcelData data = new ExcelData();
        data.setSheetName("储值报表");
        List<String> titles = new ArrayList();
        titles.add("序");
        titles.add("会员名称");
        titles.add("储值金额");
        titles.add("储值赠送金额");
        titles.add("储值时间");

        data.setTitles(titles);

        List<List<Object>> rows = new ArrayList();
        data.setRows(rows);

        int i = 1;
        if(listData != null){
            for (CustomerSaveReport entity : listData) {
                List<Object> row = new ArrayList();
                rows.add(row);
                row.add(i++);
                row.add(entity.getCustomerName());
                row.add(entity.getSalesAmount());
                row.add(entity.getGiveAmount());
                row.add(entity.getCreateDate());
            }
        }

        SimpleDateFormat fdate = new SimpleDateFormat("yyyyMMdd");
        String fileName = String.format("储值报表-%s.xls", fdate.format(new Date()));
        ExcelUtils.exportExcel(response, fileName, data);
    }
}
