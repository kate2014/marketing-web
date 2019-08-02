package com.zhongmei.yunfu.api.web;

import com.zhongmei.yunfu.util.DateFormatUtil;
import com.zhongmei.yunfu.util.ToolsUtil;
import com.zhongmei.yunfu.controller.model.CollageReportModel;
import com.zhongmei.yunfu.controller.model.CutDownReportModel;
import com.zhongmei.yunfu.controller.model.FlashSalesReportModel;
import com.zhongmei.yunfu.controller.model.ReportMarketingModel;
import com.zhongmei.yunfu.domain.entity.BrandEntity;
import com.zhongmei.yunfu.domain.entity.CommercialEntity;
import com.zhongmei.yunfu.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping("/internal/marketingReport")
public class ReportMarketingController {

    @Autowired
    CollageMarketingService mCollageMarketingService;
    @Autowired
    CutDownMarketingService mCutDownMarketingService;
    @Autowired
    FlashSalesMarketingService mFlashSalesMarketingService;
    @Autowired
    BrandService mBrandService;
    @Autowired
    CommercialService mCommercialService;
    @Autowired
    WxTradeCustomerService mWxTradeCustomerService;

    /**
     * 场景营销（拼团、秒杀、砍价活动数据报表）
     * @param model
     * @param mReportMarketingModel
     * @return
     */
    @RequestMapping("/marketing")
    public String marketingReport(Model model, ReportMarketingModel mReportMarketingModel) {

        try {
            //获取门店品牌和门店编号
//            queryShopMessage(model, mReportMarketingModel);

            Date start = null;
            Date end = null;
            //设置默认查询时间
            if (mReportMarketingModel.getStartDate() == null) {
                Calendar c = Calendar.getInstance();
                //过去15天
                c.setTime(new Date());
                c.add(Calendar.DATE, -15);
                start = c.getTime();
                String temp = DateFormatUtil.format(start, DateFormatUtil.FORMAT_FULL_DATE);
                start = DateFormatUtil.parseDate(temp, DateFormatUtil.FORMAT_FULL_DATE);

                mReportMarketingModel.setStartDate(temp);
            } else {
                start = DateFormatUtil.parseDate(mReportMarketingModel.getStartDate(), DateFormatUtil.FORMAT_FULL_DATE);
            }
            if (mReportMarketingModel.getEndDate() == null) {
                end = new Date();
                mReportMarketingModel.setEndDate(DateFormatUtil.format(end, DateFormatUtil.FORMAT_FULL_DATE));
            } else {
                end = DateFormatUtil.parseDate(mReportMarketingModel.getEndDate(), DateFormatUtil.FORMAT_FULL_DATE);
            }

            //拼团报表
            collageReport(model,mReportMarketingModel);
            //砍价报表
            cutDownReport(model,mReportMarketingModel);
            //秒杀报表
            flashSalesReport(model,mReportMarketingModel);

            model.addAttribute("mReportMarketingModel", mReportMarketingModel);
        } catch (Exception e) {
            e.printStackTrace();

        }

        return "report_marketing";
    }

    public void collageReport(Model model, ReportMarketingModel mReportMarketingModel) throws Exception{

        List<CollageReportModel> listData = mCollageMarketingService.queryCollageReport(mReportMarketingModel);

        //获取所以已购买拼团活动
        mReportMarketingModel.setType(1);
        mReportMarketingModel.setEnabledFlag(1);
        List<CollageReportModel> listSaleCollage = mWxTradeCustomerService.queryCollageData(mReportMarketingModel);
        Map<Long,CollageReportModel> tempSaleMap = new LinkedHashMap<>();
        for(CollageReportModel data : listSaleCollage){
            tempSaleMap.put(data.getMarketingId(),data);
        }
        System.out.println("=====tempSaleMap===="+tempSaleMap);

        //获取所以已使用拼团活动
        mReportMarketingModel.setStatus(2);
        List<CollageReportModel> listUsedCollage = mWxTradeCustomerService.queryCollageData(mReportMarketingModel);
        Map<Long,CollageReportModel> tempUsedMap = new LinkedHashMap<>();
        for(CollageReportModel data : listUsedCollage){
            tempUsedMap.put(data.getMarketingId(),data);
        }

        List<String> collageName = new LinkedList<>();
        List<Integer> joinCount = new LinkedList<>();
        List<Integer> openCount = new LinkedList<>();
        List<Integer> finishCount = new LinkedList<>();

        List<Integer> collageSaleCount = new LinkedList<>();
        List<Integer> usedCount = new LinkedList<>();

        Integer maxCount = 0;
        for(CollageReportModel crm : listData){
            collageName.add(crm.getName());
            joinCount.add(crm.getJoinCount());
            openCount.add(crm.getOpenCount());
            finishCount.add(crm.getFinishCount());

            CollageReportModel mCollageReportModel = tempSaleMap.get(crm.getMarketingId());
            System.out.println("=====mCollageReportModel===="+crm.getMarketingId());
            if(mCollageReportModel != null){
                collageSaleCount.add(mCollageReportModel.getCountData());
                System.out.println("=====countData===="+mCollageReportModel.getCountData());
            }else{
                collageSaleCount.add(0);
            }

            CollageReportModel mCollageUsedModel = tempUsedMap.get(crm.getMarketingId());
            if(mCollageUsedModel != null){
                usedCount.add(mCollageUsedModel.getCountData());
            }else{
                usedCount.add(0);
            }

            if(maxCount < crm.getJoinCount()){
                maxCount = crm.getJoinCount();
            }
        }

        maxCount = ToolsUtil.getMaxData(Long.valueOf(maxCount)).intValue();

        model.addAttribute("maxCount", maxCount);
        model.addAttribute("intervalCount", maxCount/10);

        model.addAttribute("collageName", collageName);
        model.addAttribute("joinCount", joinCount);
        model.addAttribute("openCount", openCount);
        model.addAttribute("finishCount", finishCount);
        model.addAttribute("collageSaleCount", collageSaleCount);
        model.addAttribute("usedCount", usedCount);
    }

    public void cutDownReport(Model model, ReportMarketingModel mReportMarketingModel) throws Exception{
        List<CutDownReportModel> listCutDown = mCutDownMarketingService.queryCutDownReport(mReportMarketingModel);

        mReportMarketingModel.setType(2);
        mReportMarketingModel.setEnabledFlag(1);
        mReportMarketingModel.setStatus(2);
        List<CollageReportModel> listSaleCollage = mWxTradeCustomerService.queryCollageData(mReportMarketingModel);
        Map<Long,CollageReportModel> tempSaleMap = new LinkedHashMap<>();
        for(CollageReportModel data : listSaleCollage){
            tempSaleMap.put(data.getMarketingId(),data);
        }


        List<String> cutDownName = new LinkedList<>();
        List<Integer> cutDownJoinCount = new LinkedList<>();
        List<Integer> soldCount = new LinkedList<>();
        List<Integer> saleCutDownCount = new LinkedList<>();

        Integer maxCutJoinCount = 0;
        Integer maxSoldCount = 0;

        for(CutDownReportModel cdm : listCutDown){
            cutDownName.add(cdm.getName());
            cutDownJoinCount.add(cdm.getJoinCount());
            soldCount.add(cdm.getSoldCount());

            CollageReportModel mCollageReportModel = tempSaleMap.get(cdm.getMarketingId());
            if(mCollageReportModel != null){
                saleCutDownCount.add(mCollageReportModel.getCountData());
            }else{
                saleCutDownCount.add(0);
            }

            if(maxCutJoinCount < cdm.getJoinCount()){
                maxCutJoinCount = cdm.getJoinCount();
            }
            if(maxSoldCount < cdm.getSoldCount()){
                maxSoldCount = cdm.getSoldCount();
            }
        }

        maxCutJoinCount = ToolsUtil.getMaxData(Long.valueOf(maxCutJoinCount)).intValue();
        maxSoldCount = ToolsUtil.getMaxData(Long.valueOf(maxSoldCount)).intValue();

        model.addAttribute("maxCutJoinCount", maxCutJoinCount);
        model.addAttribute("intervalCutCount", maxCutJoinCount/10);
        model.addAttribute("maxSoldCount", maxSoldCount);
        model.addAttribute("intervalSoldCount", maxSoldCount/10);

        model.addAttribute("cutDownName", cutDownName);
        model.addAttribute("cutDownJoinCount", cutDownJoinCount);
        model.addAttribute("soldCount", soldCount);
        model.addAttribute("saleCutDownCount", saleCutDownCount);
    }

    public void flashSalesReport(Model model, ReportMarketingModel mReportMarketingModel) throws Exception{

        List<FlashSalesReportModel> listData = mFlashSalesMarketingService.queryFlashSalesReport(mReportMarketingModel);

        mReportMarketingModel.setType(3);
        mReportMarketingModel.setEnabledFlag(1);
        mReportMarketingModel.setStatus(2);
        List<CollageReportModel> listSaleCollage = mWxTradeCustomerService.queryCollageData(mReportMarketingModel);
        Map<Long,CollageReportModel> tempSaleMap = new LinkedHashMap<>();
        for(CollageReportModel data : listSaleCollage){
            tempSaleMap.put(data.getMarketingId(),data);
        }

        List<String> flashSalesName = new ArrayList<>();
        List<Integer> flashSalesSoldCount = new ArrayList<>();
        List<Integer> saleFlashCount = new LinkedList<>();

        Integer maxSaleCount = 0;

        for(FlashSalesReportModel fsrm : listData){
            flashSalesName.add(fsrm.getName());
            flashSalesSoldCount.add(fsrm.getSoldCount());

            if(maxSaleCount < fsrm.getSoldCount()){
                maxSaleCount = fsrm.getSoldCount();
            }

            CollageReportModel mCollageReportModel = tempSaleMap.get(fsrm.getMarketingId());
            if(mCollageReportModel != null){
                saleFlashCount.add(mCollageReportModel.getCountData());
            }else{
                saleFlashCount.add(0);
            }
        }

        model.addAttribute("flashSalesName", flashSalesName);
        model.addAttribute("flashSalesSoldCount", flashSalesSoldCount);
        model.addAttribute("saleFlashCount", saleFlashCount);

        maxSaleCount = ToolsUtil.getMaxData(Long.valueOf(maxSaleCount)).intValue();
        model.addAttribute("maxSaleCount", maxSaleCount);
        model.addAttribute("intervalSaleCount", maxSaleCount/10);

    }

    public Model queryShopMessage(Model model, ReportMarketingModel mReportMarketingModel) throws Exception {

        BrandEntity brand = mBrandService.queryBrandByAppId(mReportMarketingModel.getBrandIdenty());

        List<CommercialEntity> listCommercial = mCommercialService.queryCommercialByBrandId(brand.getId());

        model.addAttribute("brand", brand);
        model.addAttribute("listCommercial", listCommercial);
        return model;
    }


}
