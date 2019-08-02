package com.zhongmei.yunfu.api.web;


import com.zhongmei.yunfu.controller.model.TradeModel;
import com.zhongmei.yunfu.controller.model.excel.ExcelData;
import com.zhongmei.yunfu.controller.model.excel.ExcelUtils;
import com.zhongmei.yunfu.domain.entity.*;
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
    @Autowired
    CustomerService mCustomerService;

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

            CustomerExtraEntity mCustomerSaveReport = mCustomerService.queryCustomerSaveReport(mTradeModel.getBrandIdenty(),mTradeModel.getShopIdenty());
            if(mCustomerSaveReport == null){
                mCustomerSaveReport = new CustomerExtraEntity();
                mCustomerSaveReport.setStoredAmount(BigDecimal.ZERO);
                mCustomerSaveReport.setStoredGive(BigDecimal.ZERO);
                mCustomerSaveReport.setStoredBalance(BigDecimal.ZERO);
            }
            BigDecimal saveBaseAmount = mCustomerSaveReport.getStoredAmount().subtract(mCustomerSaveReport.getStoredGive());
            model.addAttribute("saveBaseAmount", saveBaseAmount);
            model.addAttribute("mCustomerSaveReport", mCustomerSaveReport);

            List<CustomerSaveReport> listSaveData = mCustomerStoredService.querySaveData(mTradeModel);

            Map<String,String> dateArray = new LinkedHashMap<>();
            Map<String,Integer> countArray = new LinkedHashMap<>();
            Map<String,BigDecimal> saveArray = new LinkedHashMap();
            Map<String,BigDecimal> giveArray = new LinkedHashMap<>();
            Map<String,BigDecimal> saleArray = new LinkedHashMap<>();

            Long maxCount = 0l;
            Long maxAmount = 0l;

            //交易总笔数
            int totalTrade = 0;
            //储值消费总额
            BigDecimal totalSale = BigDecimal.ZERO;
            //储值总额
            BigDecimal totalSave = BigDecimal.ZERO;
            //赠送总额
            BigDecimal totalGive = BigDecimal.ZERO;

            for(CustomerSaveReport entity : listSaveData){

                dateArray.put(entity.getCreateDate(),entity.getCreateDate());
                Integer count = countArray.get(entity.getCreateDate());

                totalTrade += entity.getTradeCount();

                if(count == null){
                    countArray.put(entity.getCreateDate(),entity.getTradeCount());
                }else {
                    countArray.put(entity.getCreateDate(),entity.getTradeCount()+count);
                }


                //交易类型：1 表示储值  2 表示消费
                if(entity.getRecordype() == 1){
                    saveArray.put(entity.getCreateDate(),entity.getTradeAmount());
                    BigDecimal giveAmount = entity.getGiveAmount();
                    if(giveAmount == null){
                        giveAmount = BigDecimal.ZERO;
                    }
                    giveArray.put(entity.getCreateDate(),giveAmount);

                    BigDecimal saleAmount = saleArray.get(entity.getCreateDate());
                    if(saleAmount == null){
                        saleArray.put(entity.getCreateDate(),BigDecimal.ZERO);
                    }

                    totalGive = totalGive.add(giveAmount);

                    totalSave = totalSave.add(entity.getTradeAmount());
                }
                if(entity.getRecordype() == 2){
                    BigDecimal saveAmount = saveArray.get(entity.getCreateDate());
                    if(saveAmount == null){
                        saveArray.put(entity.getCreateDate(),BigDecimal.ZERO);
                        giveArray.put(entity.getCreateDate(),BigDecimal.ZERO);
                    }

                    saleArray.put(entity.getCreateDate(),entity.getTradeAmount());

                    totalSale = totalSale.add(entity.getTradeAmount());
                }

                //获取最大值，用来做图像展示刻度
                if(maxAmount < entity.getTradeAmount().add(entity.getGiveAmount()).longValue()){
                    maxAmount = entity.getTradeAmount().add(entity.getGiveAmount()).longValue();
                }

            }

            List<Integer> listCount = new LinkedList<>();
            for(String key: countArray.keySet()){
                listCount.add(countArray.get(key));

                //获取最大值，用来做图像展示刻度
                if(maxCount < countArray.get(key)){
                    maxCount = countArray.get(key).longValue();
                }
            }

            List<String> listDate = new LinkedList<>();
            for(String key: dateArray.keySet()){
                listDate.add(dateArray.get(key));
            }

            List<BigDecimal> listSave = new LinkedList<>();
            List<BigDecimal> listGive = new LinkedList<>();
            List<BigDecimal> listSale = new LinkedList<>();

            for(String save : saveArray.keySet()){
                listSave.add(saveArray.get(save));
            }

            for(String give : giveArray.keySet()){
                listGive.add(giveArray.get(give));
            }

            for(String sale : saleArray.keySet()){
                listSale.add(saleArray.get(sale));
            }

            model.addAttribute("listDate", listDate);
            model.addAttribute("listCount", listCount);
            model.addAttribute("saveArray", listSave);
            model.addAttribute("giveArray", listGive);
            model.addAttribute("saleArray", listSale);


            model.addAttribute("totalTrade", totalTrade);
            model.addAttribute("totalSale", totalSale);
            model.addAttribute("totalSave", totalSave);
            model.addAttribute("totalGive", totalGive);

            maxCount = ToolsUtil.getMaxData(maxCount);
            maxAmount = ToolsUtil.getMaxData(maxAmount);

            model.addAttribute("maxCount", maxCount);
            model.addAttribute("intervalCount", maxCount / 10);
            model.addAttribute("maxAmount", maxAmount);
            model.addAttribute("intervalAmount", maxAmount / 10);


            //储值详情
            List<CustomerSaveReport> listDetailData = mTradeService.customerSaveDetailReport(mTradeModel);
            BigDecimal totalGiveAmount = BigDecimal.ZERO;
            BigDecimal totalSaveAmount = BigDecimal.ZERO;
            for(CustomerSaveReport entity : listDetailData){
                if(entity.getGiveAmount() != null){
                    totalGiveAmount = totalGiveAmount.add(entity.getGiveAmount());
                }
                if(entity.getSalesAmount() != null){
                    totalSaveAmount = totalSaveAmount.add(entity.getSalesAmount());
                }
            }
            model.addAttribute("listDetailData", listDetailData);
            model.addAttribute("totalCount", listDetailData.size());
            model.addAttribute("totalAmount", totalSaveAmount);
            model.addAttribute("totalGiveAmount", totalGiveAmount);

            model.addAttribute("mTradeModel", mTradeModel);

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
        titles.add("可用金额");
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
                row.add(entity.getResidueBalance());
                row.add(entity.getCreateDate());
            }
        }

        SimpleDateFormat fdate = new SimpleDateFormat("yyyyMMdd");
        String fileName = String.format("储值报表-%s.xls", fdate.format(new Date()));
        ExcelUtils.exportExcel(response, fileName, data);
    }
}
