package com.zhongmei.yunfu.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.model.ActivityEffectModel;
import com.zhongmei.yunfu.controller.model.ActivitySalesModel;
import com.zhongmei.yunfu.controller.model.CustomerGiftModel;
import com.zhongmei.yunfu.controller.model.TradeModel;
import com.zhongmei.yunfu.domain.entity.*;
import com.zhongmei.yunfu.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.LinkedList;
import java.util.List;

/**
 * 特价活动效果
 */
@Controller
@RequestMapping("/activity")
public class ActivityEffectController extends BaseController{

    @Autowired
    OperationalRecordsService mOperationalRecordsService;
    @Autowired
    RecommendationAssociationService mRAService;
    @Autowired
    ActivitySalesGiftService mActivitySalesGiftService;
    @Autowired
    RedPacketsRecordService mRedPacketsRecordService;
    @Autowired
    WxTradeCustomerService mWxTradeCustomerService;

    @RequestMapping("/effect")
    public String querySalesList(Model model, ActivitySalesModel mActivitySalesModel) {

        try {

            Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
            Long shopIdentity = LoginManager.get().getUser().getShopIdenty();

            OperationalRecordsEntity entity = new OperationalRecordsEntity();

            entity.setBrandIdenty(brandIdentity);
            entity.setShopIdenty(shopIdentity);
            entity.setActivityId(mActivitySalesModel.getId());

            List<OperationalRecordsEntity> listCount = mOperationalRecordsService.queryEffectCount(entity);

            OperationalRecordsEntity seeCount = new OperationalRecordsEntity();
            seeCount.setId(0l);
            seeCount.setOperationalCount(0);

            OperationalRecordsEntity shareCount = new OperationalRecordsEntity();
            shareCount.setId(0l);
            shareCount.setOperationalCount(0);

            OperationalRecordsEntity salesCount = new OperationalRecordsEntity();
            salesCount.setId(0l);
            salesCount.setOperationalCount(0);

            for(OperationalRecordsEntity effect : listCount){

                if(effect.getType() == 1){
                    seeCount = effect;
                }else if(effect.getType() == 2){
                    shareCount = effect;
                }

            }

            TradeModel mTradeModel = new TradeModel();
            mTradeModel.setBrandIdenty(brandIdentity);
            mTradeModel.setShopIdenty(shopIdentity);
            mTradeModel.setMarketingId(mActivitySalesModel.getId());
            mTradeModel.setType(4);

            List<WxTradeCustomerEntity> listSalseData = mWxTradeCustomerService.querySalseDetail(mTradeModel);

            if(listSalseData != null && listSalseData.size() > 0){
                Integer salseCount = mWxTradeCustomerService.querySalseCount(mTradeModel);
                salesCount.setId(Long.valueOf(listSalseData.size()));
                salesCount.setOperationalCount(salseCount);
            }

            model.addAttribute("seeCount",seeCount);
            model.addAttribute("shareCount",shareCount);
            model.addAttribute("salesCount",salesCount);

            List<Long> persionCount = new LinkedList<>();
            List<Integer> optionCount = new LinkedList<>();

            persionCount.add(seeCount.getId());
            optionCount.add(seeCount.getOperationalCount());

            persionCount.add(shareCount.getId());
            optionCount.add(shareCount.getOperationalCount());

            persionCount.add(salesCount.getId());
            optionCount.add(salesCount.getOperationalCount());

            model.addAttribute("persionCount",persionCount);
            model.addAttribute("optionCount",optionCount);

            model.addAttribute("mActivitySalesModel",mActivitySalesModel);

        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

        return "activity_effect";
    }

    @RequestMapping("/effect/detail")
    public String querySalesDetail(Model model, ActivityEffectModel mActivityEffectModel) {

        try {
            Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
            Long shopIdentity = LoginManager.get().getUser().getShopIdenty();

            OperationalRecordsEntity entity = new OperationalRecordsEntity();
            entity.setBrandIdenty(brandIdentity);
            entity.setShopIdenty(shopIdentity);
            entity.setActivityId(mActivityEffectModel.getActivityId());
            entity.setType(mActivityEffectModel.getType());
            entity.setCustomerName(mActivityEffectModel.getCustomerName());
            entity.setCustomerPhone(mActivityEffectModel.getCustomerPhone());
            entity.setOperationalCount(mActivityEffectModel.getOperationalCount());

            Page<OperationalRecordsEntity> pageData = mOperationalRecordsService.queryByActivityId(entity,mActivityEffectModel.getPageNo(),mActivityEffectModel.getPageSize());

            setWebPage(model, "/activity/effect/detail", pageData, mActivityEffectModel);
            model.addAttribute("mActivitySalesModel", mActivityEffectModel);
            model.addAttribute("list", pageData.getRecords());

            return "activity_effect_detail";
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

    }

    /**
     * 售卖情况
     * @param model
     * @param mActivityEffectModel
     * @return
     */
    @RequestMapping("/effect/salesEffect")
    public String salesEffect(Model model, ActivityEffectModel mActivityEffectModel){
        try {
            Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
            Long shopIdentity = LoginManager.get().getUser().getShopIdenty();
            OperationalRecordsEntity entity = new OperationalRecordsEntity();
            entity.setBrandIdenty(brandIdentity);
            entity.setShopIdenty(shopIdentity);
            entity.setActivityId(mActivityEffectModel.getActivityId());
            entity.setCustomerName(mActivityEffectModel.getCustomerName());
            entity.setCustomerPhone(mActivityEffectModel.getCustomerPhone());
            entity.setOperationalCount(mActivityEffectModel.getOperationalCount());
            entity.setType(4);//wxtradeCustomer中type为4表示特价商品

            List<OperationalRecordsEntity> listData = mOperationalRecordsService.querySalesEffect(entity);

            model.addAttribute("mActivitySalesModel", mActivityEffectModel);
            model.addAttribute("list", listData);

            return "activity_sales_effect_detail";
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

    }

    /**
     * 推荐成单情况
     * @param model
     * @param mActivityEffectModel
     * @return
     */
    @RequestMapping("/effect/recommend")
    public String queryRecommendDetail(Model model, ActivityEffectModel mActivityEffectModel){

        try {
            Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
            Long shopIdentity = LoginManager.get().getUser().getShopIdenty();

            RecommendationAssociationEntity entity = new RecommendationAssociationEntity();
            entity.setBrandIdenty(brandIdentity);
            entity.setShopIdenty(shopIdentity);
            entity.setActivityId(mActivityEffectModel.getActivityId());
            entity.setMainCustomerName(mActivityEffectModel.getCustomerName());
            entity.setMainCustomerPhone(mActivityEffectModel.getCustomerPhone());

            Page<RecommendationAssociationEntity> listData = mRAService.queryRAByCustomer(entity,mActivityEffectModel.getPageNo(),mActivityEffectModel.getPageSize());

            setWebPage(model, "/activity/effect/detail", listData, mActivityEffectModel);
            model.addAttribute("mActivityEffectModel", mActivityEffectModel);
            model.addAttribute("list", listData.getRecords());


        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

        return "activity_recommendation_detail";
    }

    /**
     * 礼品赠送情况
     * @param model
     * @param mActivityEffectModel
     * @return
     */
    @RequestMapping("/effect/giftDetail")
    public String giftDetail(Model model, ActivityEffectModel mActivityEffectModel){

        try {
            Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
            Long shopIdentity = LoginManager.get().getUser().getShopIdenty();
            mActivityEffectModel.setBrandIdenty(brandIdentity);
            mActivityEffectModel.setShopIdenty(shopIdentity);
            mActivityEffectModel.setCustomerName(mActivityEffectModel.getCustomerName());
            mActivityEffectModel.setCustomerPhone(mActivityEffectModel.getCustomerPhone());
            List<CustomerGiftModel> listGift =  mActivitySalesGiftService.queryActivityGift(mActivityEffectModel);
            model.addAttribute("listGift", listGift);
            model.addAttribute("mActivityEffectModel", mActivityEffectModel);
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

        return "activity_effect_gift";
    }

    /**
     * 获取红包发放情况
     * @param model
     * @param mActivityEffectModel
     * @return
     */
    @RequestMapping("/effect/redPacketsDetail")
    public String redPacketsDetail(Model model, ActivityEffectModel mActivityEffectModel){

        try{
            Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
            Long shopIdentity = LoginManager.get().getUser().getShopIdenty();

            RedPacketsRecordEntity entity = new RedPacketsRecordEntity();
            entity.setBrandIdenty(brandIdentity);
            entity.setShopIdenty(shopIdentity);
            entity.setActivityId(mActivityEffectModel.getActivityId());
            entity.setCustomerName(mActivityEffectModel.getCustomerName());
            entity.setCustomerPhone(mActivityEffectModel.getCustomerPhone());

            Page<RedPacketsRecordEntity> listData = mRedPacketsRecordService.queryRedPackets(entity,mActivityEffectModel.getPageNo(),mActivityEffectModel.getPageSize());

            setWebPage(model, "/activity/effect/redPacketsDetail", listData, mActivityEffectModel);
            model.addAttribute("mActivityEffectModel", mActivityEffectModel);
            model.addAttribute("list", listData.getRecords());

        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

        return "activity_effect_redpackets";
    }

}
