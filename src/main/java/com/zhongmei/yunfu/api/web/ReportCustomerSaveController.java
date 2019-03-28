package com.zhongmei.yunfu.api.web;


import com.zhongmei.yunfu.controller.model.TradeModel;
import com.zhongmei.yunfu.controller.model.excel.ExcelData;
import com.zhongmei.yunfu.controller.model.excel.ExcelUtils;
import com.zhongmei.yunfu.domain.entity.BrandEntity;
import com.zhongmei.yunfu.domain.entity.CommercialEntity;
import com.zhongmei.yunfu.domain.entity.CustomerSaveReport;
import com.zhongmei.yunfu.domain.entity.DishReport;
import com.zhongmei.yunfu.service.BrandService;
import com.zhongmei.yunfu.service.CommercialService;
import com.zhongmei.yunfu.service.TradeItemService;
import com.zhongmei.yunfu.service.TradeService;
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

        mTradeModel.setBusinessType(2);
        List<CustomerSaveReport> listData = mTradeService.customerSaveReport(mTradeModel);

        ExcelData data = new ExcelData();
        data.setSheetName("储值报表");
        List<String> titles = new ArrayList();
        titles.add("序");
        titles.add("交易日期");
        titles.add("储值单数");
        titles.add("储值金额");

        data.setTitles(titles);

        List<List<Object>> rows = new ArrayList();
        data.setRows(rows);

        int i = 1;
        if(listData != null){
            for (CustomerSaveReport entity : listData) {
                List<Object> row = new ArrayList();
                rows.add(row);
                row.add(i++);
                row.add(entity.getCreateDate());
                row.add(entity.getTradeCount());
                row.add(entity.getSalesAmount());
            }
        }

        SimpleDateFormat fdate = new SimpleDateFormat("yyyyMMdd");
        String fileName = String.format("储值报表-%s.xls", fdate.format(new Date()));
        ExcelUtils.exportExcel(response, fileName, data);
    }
}
