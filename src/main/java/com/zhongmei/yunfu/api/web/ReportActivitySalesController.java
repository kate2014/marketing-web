package com.zhongmei.yunfu.api.web;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.BaseController;
import com.zhongmei.yunfu.controller.model.ReportMarketingModel;
import com.zhongmei.yunfu.domain.entity.WxTradeCustomerEntity;
import com.zhongmei.yunfu.service.*;
import com.zhongmei.yunfu.util.DateFormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
@RequestMapping("/internal/activitySalesReport")
public class ReportActivitySalesController extends BaseController {

    @Autowired
    ActivitySalesService mActivitySalesService;
    @Autowired
    WxTradeCustomerService mWxTradeCustomerService;

    @RequestMapping("/activitySales")
    public String activitySalesReport(Model model, ReportMarketingModel mReportMarketingModel) {

        try{
            WxTradeCustomerEntity mWxTradeCustomerEntity = new WxTradeCustomerEntity();
            mWxTradeCustomerEntity.setBrandIdenty(mReportMarketingModel.getBrandIdenty());
            mWxTradeCustomerEntity.setShopIdenty(mReportMarketingModel.getShopIdenty());
            mWxTradeCustomerEntity.setType(4);


            if(mReportMarketingModel.getValidityPeriod() != null && !mReportMarketingModel.getValidityPeriod().equals("")){
                mWxTradeCustomerEntity.setValidityPeriod(DateFormatUtil.parseDate(mReportMarketingModel.getValidityPeriod(),DateFormatUtil.FORMAT_FULL_DATE));
            }else{
                mWxTradeCustomerEntity.setValidityPeriod(new Date());
            }


            List<WxTradeCustomerEntity> listAllReport = mWxTradeCustomerService.queryReport(mWxTradeCustomerEntity);

            List<WxTradeCustomerEntity> reportData = new ArrayList<>();

            List<String> allName = new LinkedList<>();
            List<Long> allData = new LinkedList<>();
            List<Long> usedData = new LinkedList<>();


            Map<Long,Long> tempMap = new LinkedHashMap<>();

            for(WxTradeCustomerEntity entity : listAllReport){

                allData.add(entity.getId());
                allName.add(entity.getMarketingName());
                tempMap.put(entity.getMarketingId(),0l);
            }

            mWxTradeCustomerEntity.setStatus(2);
            List<WxTradeCustomerEntity> listUsedReport = mWxTradeCustomerService.queryReport(mWxTradeCustomerEntity);

            for(WxTradeCustomerEntity entity : listUsedReport){
                tempMap.put(entity.getMarketingId(),entity.getId());
            }

            for(Long key:tempMap.keySet()){
                usedData.add(tempMap.get(key));
            }

            model.addAttribute("allName", allName);
            model.addAttribute("allData", allData);
            model.addAttribute("usedData", usedData);


            mWxTradeCustomerEntity.setStatus(mReportMarketingModel.getStatus());

            Page<WxTradeCustomerEntity> listData = mWxTradeCustomerService.queryAllMarketing(mReportMarketingModel);

            setWebPage(model, "/internal/activitySalesReport/activitySales", listData, mReportMarketingModel);
            model.addAttribute("listData", listData.getRecords());
            model.addAttribute("mReportMarketingModel", mReportMarketingModel);
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }


        return "report_activity_sales";
    }


}
