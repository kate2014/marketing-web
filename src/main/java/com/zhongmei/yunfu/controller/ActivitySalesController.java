package com.zhongmei.yunfu.controller;


import com.zhongmei.yunfu.controller.model.ActivitySalesModel;
import com.zhongmei.yunfu.domain.entity.*;
import com.zhongmei.yunfu.service.*;
import com.zhongmei.yunfu.util.DateFormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/activity")
public class ActivitySalesController extends BaseController{

    @Autowired
    ActivitySalesService mActivitySalesService;
    @Autowired
    ActivitySalesGiftService mActivitySalesGiftService;
    @Autowired
    ActivityRedPacketsService mActivityRedPacketsService;
    @Autowired
    OperationalRecordsService mOperationalRecordsService;
    @Autowired
    RecommendationAssociationService mRecommendationAssociationService;

    @RequestMapping("/salesList")
    public String querySalesList(Model model, ActivitySalesModel mActivitySalesModel) {
        try {
            Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
            Long shopIdentity = LoginManager.get().getUser().getShopIdenty();

            ActivitySalesEntity mActivitySalesEntity = new ActivitySalesEntity();
            mActivitySalesEntity.setBrandIdenty(brandIdentity);
            mActivitySalesEntity.setShopIdenty(shopIdentity);
            mActivitySalesEntity.setEnabledFlag(mActivitySalesModel.getEnabledFlag());
            mActivitySalesEntity.setName(mActivitySalesModel.getName());

            List<ActivitySalesEntity> listData = mActivitySalesService.queryListData(mActivitySalesEntity);

            model.addAttribute("listData",listData);

            model.addAttribute("mActivitySalesModel",mActivitySalesModel);

        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

        return "activity_sales_list";
    }

    /**
     * 删除活动
     * @param model
     * @param mActivitySalesModel
     * @return
     */
    @RequestMapping("/deleteActivity")
    public String deleteActivity(Model model, ActivitySalesModel mActivitySalesModel){

        try {
            if(mActivitySalesModel.getId() != null){
                Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
                Long shopIdentity = LoginManager.get().getUser().getShopIdenty();

                mActivitySalesService.deleteById(mActivitySalesModel.getId());
                mActivitySalesGiftService.deleteByActivityId(mActivitySalesModel.getId());
                mActivityRedPacketsService.deleteByActivityId(mActivitySalesModel.getId());

                //删除活动对应的操作记录
                OperationalRecordsEntity entity = new OperationalRecordsEntity();
                entity.setBrandIdenty(brandIdentity);
                entity.setShopIdenty(shopIdentity);
                entity.setActivityId(mActivitySalesModel.getId());
                entity.setSource(7);
                mOperationalRecordsService.deleteByAction(entity);
                //删除活动推广关联关系
                mRecommendationAssociationService.deleteByActivity(mActivitySalesModel.getId());
            }

            return "success";
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

    }

    @RequestMapping("/addActivityPage")
    public String addActivityPage(Model model, ActivitySalesModel mActivitySalesModel) {
        try {
            ActivitySalesEntity mActivitySalesEntity = new ActivitySalesEntity();

            if(mActivitySalesModel.getId() != null){
                mActivitySalesEntity = mActivitySalesService.queryById(mActivitySalesModel.getId());
            }


            model.addAttribute("mActivitySalesEntity",mActivitySalesEntity);

            model.addAttribute("mActivitySalesModel",mActivitySalesModel);

        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

        return "activity_add";
    }

    @RequestMapping("/modfityBaseData")
    public String addBaseData(Model model, ActivitySalesModel mActivitySalesModel){
        try {
            Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
            Long shopIdentity = LoginManager.get().getUser().getShopIdenty();
            Long creatorId = LoginManager.get().getUser().getCreatorId();
            String creatorName = LoginManager.get().getUser().getCreatorName();

            ActivitySalesEntity mActivitySalesEntity = new ActivitySalesEntity();


            mActivitySalesEntity.setName(mActivitySalesModel.getName());
            mActivitySalesEntity.setImageUrl(mActivitySalesModel.getImageUrl());
            mActivitySalesEntity.setSaleAmount(mActivitySalesModel.getSaleAmount());
            mActivitySalesEntity.setProductId(mActivitySalesModel.getProductId());
            mActivitySalesEntity.setProductName(mActivitySalesModel.getProductName());
            mActivitySalesEntity.setCustomerBuyCount(mActivitySalesModel.getCustomerBuyCount());
            mActivitySalesEntity.setOriginalPrice(mActivitySalesModel.getOriginalPrice());
            mActivitySalesEntity.setDescribe(mActivitySalesModel.getDescribe());
            mActivitySalesEntity.setEndTime(DateFormatUtil.parseDate(mActivitySalesModel.getEndTime(), DateFormatUtil.FORMAT_FULL_DATE));
            mActivitySalesEntity.setValidityPeriod(DateFormatUtil.parseDate(mActivitySalesModel.getValidityPeriod(), DateFormatUtil.FORMAT_FULL_DATE));
            mActivitySalesEntity.setJoinCount(mActivitySalesModel.getJoinCount());
            mActivitySalesEntity.setEnabledFlag(mActivitySalesModel.getEnabledFlag());
            mActivitySalesEntity.setProfile(mActivitySalesModel.getProfile());
            mActivitySalesEntity.setMusic(mActivitySalesModel.getMusic());

            if(mActivitySalesModel.getId() == null){
                mActivitySalesEntity.setBrandIdenty(brandIdentity);
                mActivitySalesEntity.setShopIdenty(shopIdentity);


                mActivitySalesEntity.setCreatorId(creatorId);
                mActivitySalesEntity.setCreatorName(creatorName);
                mActivitySalesEntity.setUpdatorId(creatorId);
                mActivitySalesEntity.setUpdatorName(creatorName);
                mActivitySalesEntity.setServerCreateTime(new Date());
                mActivitySalesEntity.setServerUpdateTime(new Date());
                mActivitySalesEntity.setStatusFlag(1);
            }else{

                mActivitySalesEntity.setId(mActivitySalesModel.getId());
                mActivitySalesEntity.setUpdatorId(creatorId);
                mActivitySalesEntity.setUpdatorName(creatorName);
                mActivitySalesEntity.setServerUpdateTime(new Date());
            }

            ActivitySalesEntity activityEntity = mActivitySalesService.modifityActivity(mActivitySalesEntity);


            if(mActivitySalesModel.getId() != null){
                ActivitySalesGiftEntity mActivitySalesGiftEntity = new ActivitySalesGiftEntity();
                mActivitySalesGiftEntity.setBrandIdenty(brandIdentity);
                mActivitySalesGiftEntity.setShopIdenty(shopIdentity);
                mActivitySalesGiftEntity.setActivityId(mActivitySalesModel.getId());
                List<ActivitySalesGiftEntity> listGiftRule = mActivitySalesGiftService.queryListData(mActivitySalesGiftEntity);

                model.addAttribute("listGiftRule",listGiftRule);
            }

            model.addAttribute("activityId",activityEntity.getId());

        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }


        model.addAttribute("mActivitySalesModel",mActivitySalesModel);

        return "activity_add_gift";
    }

    @RequestMapping("/intoSettingGift")
    public String intoSettingGift(Model model, ActivitySalesModel mActivitySalesModel){
        try {
            Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
            Long shopIdentity = LoginManager.get().getUser().getShopIdenty();

            ActivitySalesGiftEntity mActivitySalesGiftEntity = new ActivitySalesGiftEntity();
            mActivitySalesGiftEntity.setBrandIdenty(brandIdentity);
            mActivitySalesGiftEntity.setShopIdenty(shopIdentity);
            mActivitySalesGiftEntity.setActivityId(mActivitySalesModel.getId());
            List<ActivitySalesGiftEntity> listGiftRule = new ArrayList<>();

            List<ActivitySalesGiftEntity> listData = mActivitySalesGiftService.queryListData(mActivitySalesGiftEntity);
            if(listData != null){
                listGiftRule.addAll(listData);
            }

            model.addAttribute("listGiftRule",listGiftRule);
            model.addAttribute("activityId",mActivitySalesModel.getId());
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

        return "activity_add_gift";
    }

    @RequestMapping("/modiftyActivityGift")
    public String modiftyActivityGift(Model model, ActivitySalesModel mActivitySalesModel){
        try {
            Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
            Long shopIdentity = LoginManager.get().getUser().getShopIdenty();
            Long creatorId = LoginManager.get().getUser().getCreatorId();
            String creatorName = LoginManager.get().getUser().getCreatorName();

            ActivitySalesGiftEntity mActivitySalesGiftEntity = new ActivitySalesGiftEntity();
            mActivitySalesGiftEntity.setBrandIdenty(brandIdentity);
            mActivitySalesGiftEntity.setShopIdenty(shopIdentity);
            mActivitySalesGiftEntity.setActivityId(mActivitySalesModel.getActivityId());
            mActivitySalesGiftEntity.setOrderCount(mActivitySalesModel.getOrderCount());
            mActivitySalesGiftEntity.setGiftId(mActivitySalesModel.getGiftId());
            mActivitySalesGiftEntity.setGiftName(mActivitySalesModel.getGiftName());
            mActivitySalesGiftEntity.setGiftPrice(mActivitySalesModel.getGiftPrice());
            mActivitySalesGiftEntity.setImageUrl(mActivitySalesModel.getImageUrl());
            mActivitySalesGiftEntity.setGiftType(1);
            mActivitySalesGiftEntity.setStatusFlag(1);
            mActivitySalesGiftEntity.setCreatorId(creatorId);
            mActivitySalesGiftEntity.setCreatorName(creatorName);
            mActivitySalesGiftEntity.setUpdatorId(creatorId);
            mActivitySalesGiftEntity.setUpdatorName(creatorName);
            mActivitySalesGiftEntity.setServerCreateTime(new Date());
            mActivitySalesGiftEntity.setServerUpdateTime(new Date());

            mActivitySalesGiftService.addActivityGift(mActivitySalesGiftEntity);

            return String.format("redirect:/activity/intoSettingGift?id=%d",mActivitySalesModel.getActivityId());
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

    }

    @RequestMapping("/deleteGift")
    public String deleteGift(Model model, ActivitySalesModel mActivitySalesModel){

        try {
            mActivitySalesGiftService.deleteGiftById(mActivitySalesModel.getId());
            return "success";

        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

    }

    @RequestMapping("/modiftyRedPackets")
    public String modiftyRedPackets(Model model, ActivitySalesModel mActivitySalesModel){

        try {
            ActivityRedPacketsEntity mActivityRedPacketsEntity = new ActivityRedPacketsEntity();
            Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
            Long shopIdentity = LoginManager.get().getUser().getShopIdenty();

            mActivityRedPacketsEntity.setBrandIdenty(brandIdentity);
            mActivityRedPacketsEntity.setShopIdenty(shopIdentity);
            mActivityRedPacketsEntity.setActivityId(mActivitySalesModel.getActivityId());

            ActivityRedPacketsEntity entity = mActivityRedPacketsService.queryRule(mActivityRedPacketsEntity);
            if(entity != null){
                model.addAttribute("redPackets",entity);
            }else{
                model.addAttribute("redPackets",mActivityRedPacketsEntity);
            }

            model.addAttribute("activityId",mActivitySalesModel.getActivityId());

        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

        return "activity_add_redpackets";
    }

    @RequestMapping("/addRedPacketsRule")
    public String addRedPacketsRule(Model model, ActivitySalesModel mActivitySalesModel){
        try {
            ActivityRedPacketsEntity mActivityRedPacketsEntity = new ActivityRedPacketsEntity();
            Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
            Long shopIdentity = LoginManager.get().getUser().getShopIdenty();
            Long creatorId = LoginManager.get().getUser().getCreatorId();
            String creatorName = LoginManager.get().getUser().getCreatorName();

            mActivityRedPacketsEntity.setFirstMinAmount(mActivitySalesModel.getFirstMinAmount());
            mActivityRedPacketsEntity.setFirstMaxAmount(mActivitySalesModel.getFirstMaxAmount());
            mActivityRedPacketsEntity.setSecondMinAmount(mActivitySalesModel.getSecondMinAmount());
            mActivityRedPacketsEntity.setSecondMaxAmount(mActivitySalesModel.getSecondMaxAmount());

            if(mActivitySalesModel.getId() == null){
                mActivityRedPacketsEntity.setBrandIdenty(brandIdentity);
                mActivityRedPacketsEntity.setShopIdenty(shopIdentity);
                mActivityRedPacketsEntity.setActivityId(mActivitySalesModel.getActivityId());
                mActivityRedPacketsEntity.setActivityId(mActivitySalesModel.getActivityId());
                mActivityRedPacketsEntity.setStatusFlag(1);
                mActivityRedPacketsEntity.setCreatorId(creatorId);
                mActivityRedPacketsEntity.setCreatorName(creatorName);
                mActivityRedPacketsEntity.setUpdatorId(creatorId);
                mActivityRedPacketsEntity.setUpdatorName(creatorName);
                mActivityRedPacketsEntity.setServerCreateTime(new Date());
                mActivityRedPacketsEntity.setServerUpdateTime(new Date());
            }else{
                mActivityRedPacketsEntity.setId(mActivitySalesModel.getId());
                mActivityRedPacketsEntity.setUpdatorId(creatorId);
                mActivityRedPacketsEntity.setUpdatorName(creatorName);
                mActivityRedPacketsEntity.setServerUpdateTime(new Date());
            }




            mActivityRedPacketsService.addOrUpdateRule(mActivityRedPacketsEntity);


        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

        return redirect("/activity/salesList");
    }



}
