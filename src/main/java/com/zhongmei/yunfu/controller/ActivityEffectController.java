package com.zhongmei.yunfu.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.model.ActivityEffectModel;
import com.zhongmei.yunfu.controller.model.ActivitySalesModel;
import com.zhongmei.yunfu.domain.entity.CollageMarketingEntity;
import com.zhongmei.yunfu.domain.entity.OperationalRecordsEntity;
import com.zhongmei.yunfu.domain.entity.RecommendationAssociationEntity;
import com.zhongmei.yunfu.service.LoginManager;
import com.zhongmei.yunfu.service.OperationalRecordsService;
import com.zhongmei.yunfu.service.RecommendationAssociationService;
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
                }else if(effect.getType() == 3){
                    salesCount = effect;
                }

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

}
