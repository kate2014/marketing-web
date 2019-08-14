package com.zhongmei.yunfu.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.model.NewDishPushSearchModel;
import com.zhongmei.yunfu.domain.entity.OperationalRecordsEntity;
import com.zhongmei.yunfu.service.LoginManager;
import com.zhongmei.yunfu.service.OperationalRecordsService;
import com.zhongmei.yunfu.util.DateFormatUtil;
import com.zhongmei.yunfu.controller.model.ActivityModifyModel;
import com.zhongmei.yunfu.controller.model.ActivitySearchModel;
import com.zhongmei.yunfu.domain.entity.PushPlanActivityEntity;
import com.zhongmei.yunfu.service.PushPlanActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * 活动推送活动方案 前端控制器
 * </p>
 *
 * @author yangyp
 * @since 2018-08-26
 */
@Controller
@RequestMapping("/pushPlanActivity")
public class PushPlanActivityController extends BaseController {

    @Autowired
    PushPlanActivityService pushPlanActivityService;
    @Autowired
    OperationalRecordsService mOperationalRecordsService;

    @RequestMapping("/list")
    public String list(Model model, ActivitySearchModel searchModel) {

        if (searchModel.getName() != null && searchModel.getName().equals("")) {
            searchModel.setName(null);
        }
        Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
        Long shopIdentity = LoginManager.get().getUser().getShopIdenty();

        Page<PushPlanActivityEntity> listPage = pushPlanActivityService.findListPage(brandIdentity, shopIdentity, searchModel.getPlanState(), searchModel.getName(),null, searchModel.getPageNo(), searchModel.getPageSize());
        setWebPage(model, "/pushPlanActivity/list", listPage, searchModel);
        model.addAttribute("searchModel", searchModel);
        model.addAttribute("list", listPage.getRecords());
        return "activitylist";
    }

    @RequestMapping("/addPlanActivityPage")
    public String addPlanActivityPage(Model model, ActivityModifyModel activityaddModel) {
        model.addAttribute("planActivity", activityaddModel);
        return "activityadd";

    }

    /**
     * 添加活动
     *
     * @param model
     * @param activityaddModel
     * @return
     */
    @RequestMapping("/addPlanActivity")
    public String addPlanActivity(Model model, ActivityModifyModel activityaddModel) {
        try {
            Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
            Long shopIdentity = LoginManager.get().getUser().getShopIdenty();
            Long creatorId = LoginManager.get().getUser().getCreatorId();
            String creatorname = LoginManager.get().getUser().getCreatorName();

            PushPlanActivityEntity mPushPlanActivity = new PushPlanActivityEntity();

            mPushPlanActivity.setName(activityaddModel.getName());

            mPushPlanActivity.setEndTime(DateFormatUtil.parseDate(activityaddModel.getEndTime(),DateFormatUtil.FORMAT_FULL_DATE));
            mPushPlanActivity.setPlanDesc(activityaddModel.getPlanDesc());
            mPushPlanActivity.setImgUrl(activityaddModel.getImgUrl());
            mPushPlanActivity.setDescribe(activityaddModel.getDescribe());
            mPushPlanActivity.setPlanState(activityaddModel.getPlanState());

            Boolean isSuccess = null;
            //如果activityaddModel.getId()有值这表示编辑，反正是则是添加
            if (activityaddModel.getId() == null) {
                if (activityaddModel.getPlanState() == 1) {
                    mPushPlanActivity.setBeginTime(new Date());
                }
                mPushPlanActivity.setBrandIdentity(brandIdentity);
                mPushPlanActivity.setShopIdentity(shopIdentity);
                mPushPlanActivity.setScanNumber(0);
                mPushPlanActivity.setShareNumber(0);
                mPushPlanActivity.setServerCreateTime(new Date());
                mPushPlanActivity.setServerUpdateTime(new Date());
                mPushPlanActivity.setStatusFlag(1);
                mPushPlanActivity.setCreatorId(creatorId);
                mPushPlanActivity.setCreatorName(creatorname);
                isSuccess = pushPlanActivityService.addActivity(mPushPlanActivity);
            } else {
                mPushPlanActivity.setId(activityaddModel.getId());
                mPushPlanActivity.setUpdatorId(creatorId);
                mPushPlanActivity.setUpdatorName(creatorname);
                mPushPlanActivity.setServerUpdateTime(new Date());
                isSuccess = pushPlanActivityService.modifyActivity(mPushPlanActivity);
            }

            if (!isSuccess){
                return "fail";
            }

        } catch (Exception e) {
            e.printStackTrace();

            return "fail";
        }
        return redirect("/pushPlanActivity/list");
    }

    @RequestMapping("/gotoActivityPage")
    public String gotoModifyActivity(Model model, ActivitySearchModel searchModel) {

        PushPlanActivityEntity mPushPlanActivity = pushPlanActivityService.findActivityById(searchModel.getId());
        model.addAttribute("planActivity", mPushPlanActivity);

        return "activityadd";

    }

//    /**
//     * 编辑活动状态
//     *
//     * @param model
//     * @param activityModifyModel
//     * @return
//     */
//    @RequestMapping("/modify")
//    public String modifyPlanActivity(Model model, ActivityModifyModel activityModifyModel) {
//
//        PushPlanActivityEntity mPushPlanActivity = pushPlanActivityService.findActivityById(activityModifyModel.getId());
//        Long creatorId = LoginManager.get().getUser().getCreatorId();
//        String creatorname = LoginManager.get().getUser().getCreatorName();
//        mPushPlanActivity.setUpdatorId(creatorId);
//        mPushPlanActivity.setUpdatorName(creatorname);
//        mPushPlanActivity.setPlanState(activityModifyModel.getPlanState());
//        if (activityModifyModel.getPlanState() == 1) {
//            mPushPlanActivity.setBeginTime(new Date());
//        }
//        Boolean isSuccess = pushPlanActivityService.modifyActivity(mPushPlanActivity);
//        if (!isSuccess) {
//            return "fail";
//        }
//        return redirect("/pushPlanActivity/list");
//    }

    /**
     * 编辑活动状态
     * @param model
     * @param activityModifyModel
     * @return
     */
    @RequestMapping("/updateState")
    public String updatePlanState(Model model, ActivityModifyModel activityModifyModel) {
        Long creatorId = LoginManager.get().getUser().getCreatorId();
        String creatorname = LoginManager.get().getUser().getCreatorName();
        Boolean isSuccess = pushPlanActivityService.updateActivityState(activityModifyModel.getId(), activityModifyModel.getPlanState(), creatorId,creatorname);
        if (!isSuccess) {
            return "fail";
        }
        return redirect("/pushPlanActivity/list");
    }

    /**
     * 删除活动
     *
     * @param model
     * @param activityModifyModel
     * @return
     */
    @RequestMapping("/delelte")
    public String deleteActivity(Model model, ActivityModifyModel activityModifyModel) {
        Boolean isSuccess = pushPlanActivityService.deleteActivity(activityModifyModel.getId());
        if (!isSuccess) {
            return "fail";
        }
        return "success";
    }

    @RequestMapping("/effect")
    public String effect(Model model, ActivitySearchModel searchModel){

        model.addAttribute("searchModel", searchModel);

        return "push_activity_effect";
    }

    @RequestMapping("/effectDetail")
    public String effectDetail(Model model,ActivitySearchModel searchModel){

        try {
            Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
            Long shopIdentity = LoginManager.get().getUser().getShopIdenty();

            OperationalRecordsEntity entity = new OperationalRecordsEntity();
            entity.setBrandIdenty(brandIdentity);
            entity.setShopIdenty(shopIdentity);
            entity.setActivityId(searchModel.getActivityId());
            entity.setType(searchModel.getType());
            entity.setCustomerName(searchModel.getCustomerName());
            entity.setCustomerPhone(searchModel.getCustomerPhone());
            entity.setOperationalCount(searchModel.getOperationalCount());

            Page<OperationalRecordsEntity> listPage = mOperationalRecordsService.queryByActivityId(entity,searchModel.getPageNo(),searchModel.getPageSize());
            setWebPage(model, "/pushPlanNewDish/effectDetail", listPage, searchModel);

            model.addAttribute("listData", listPage.getRecords());
            model.addAttribute("searchModel", searchModel);
            return "push_activity_effect_detail";

        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

    }

}


