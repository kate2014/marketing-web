package com.zhongmei.yunfu.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.model.NewDishPushModel;
import com.zhongmei.yunfu.controller.model.NewDishPushSearchModel;
import com.zhongmei.yunfu.domain.entity.OperationalRecordsEntity;
import com.zhongmei.yunfu.domain.entity.PushPlanNewDishEntity;
import com.zhongmei.yunfu.domain.enums.StatusFlag;
import com.zhongmei.yunfu.service.LoginManager;
import com.zhongmei.yunfu.service.OperationalRecordsService;
import com.zhongmei.yunfu.service.PushPlanNewDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 新品推送方案 前端控制器
 * </p>
 *
 * @author yangyp
 * @since 2018-08-26
 */
@Controller
@RequestMapping("/pushPlanNewDish")
public class PushPlanNewDishController extends BaseController {

    @Autowired
    PushPlanNewDishService mPushNewDishService;
    @Autowired
    OperationalRecordsService mOperationalRecordsService;

    @RequestMapping({"", "/list"})
    public String getPlanList(Model model, NewDishPushSearchModel newDishPushModel) {

        newDishPushModel.setShopIdenty(LoginManager.get().getUser().getShopIdenty());
        newDishPushModel.setBrandIdenty(LoginManager.get().getUser().getBrandIdenty());

        Page<PushPlanNewDishEntity> newDishPush = mPushNewDishService.list(newDishPushModel);

        List<NewDishPushModel> planModeList = new ArrayList<>();
        if (newDishPush != null && newDishPush.getRecords() != null) {
            for (PushPlanNewDishEntity plan : newDishPush.getRecords()) {
                NewDishPushModel newDishModel = new NewDishPushModel(plan);
                planModeList.add(newDishModel);
            }
        }

        setWebPage(model, "/pushPlanNewDish/list", newDishPush, newDishPushModel);
        model.addAttribute("list", planModeList);
        model.addAttribute("pageNo", newDishPush.getCurrent());
        model.addAttribute("totalPage", newDishPush.getPages());
        model.addAttribute("keyWord", newDishPushModel.getKeyWord());
        model.addAttribute("planState", newDishPushModel.getPlanState());
        return "dishmarketinglist";
    }


    @RequestMapping("/addOrEdit")
    public String addNewDishPushPlan(Model model, NewDishPushModel newDishPlanModel) {
        boolean result = false;
        PushPlanNewDishEntity newDishPlan = newDishPlanModel.obtainNewDishPlan();

        newDishPlan.setName(newDishPlan.getDishName());
        newDishPlan.setStatusFlag(StatusFlag.VALiD.value());

        Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
        Long shopIdentity = LoginManager.get().getUser().getShopIdenty();

        newDishPlan.setShopIdentity(shopIdentity);
        newDishPlan.setBrandIdentity(brandIdentity);

        newDishPlan.setUpdatorId(LoginManager.get().getUser().getCreatorId());
        newDishPlan.setUpdatorName(LoginManager.get().getUser().getCreatorName());
        newDishPlan.setServerUpdateTime(new Date());

        if (newDishPlan.getId() != null) {
            //编辑
            result = mPushNewDishService.updateNewDishPushPlan(newDishPlan);
        } else {
            //新增
            newDishPlan.setBeginTime(new Date());
            newDishPlan.setScanNumber(0);
            newDishPlan.setShareNumber(0);
            newDishPlan.setCreatorId(LoginManager.get().getUser().getCreatorId());
            newDishPlan.setCreatorName(LoginManager.get().getUser().getCreatorName());
            newDishPlan.setServerCreateTime(new Date());
            result = mPushNewDishService.addNewDishPushPlan(newDishPlan);
        }

        return redirect("/pushPlanNewDish");
    }


    @RequestMapping("/edit")
    public String updateNewDishPushPlan(Model model, PushPlanNewDishEntity newDishPlan) {
        boolean result = mPushNewDishService.updateNewDishPushPlan(newDishPlan);
        return "success";
    }

    @RequestMapping("/disable")
    public String disable(Model model, Long id) {
        boolean result = mPushNewDishService.enableNewDishPushPlan(id, 2);
        return redirect("/pushPlanNewDish");
    }

    @RequestMapping("/enable")
    public String enablePlan(Model model, Long id) {
        boolean result = mPushNewDishService.enableNewDishPushPlan(id, 1);
        return redirect("/pushPlanNewDish");
    }

    @RequestMapping("/accept")
    public String acceptPlan(Model model, Long id,Long sourceId) {
        boolean result = mPushNewDishService.enableNewDishPushPlan(id, 4);

        //更新门店接受数量
        PushPlanNewDishEntity dishPushPlan = mPushNewDishService.queryByid(sourceId);
        PushPlanNewDishEntity entity = new PushPlanNewDishEntity();
        entity.setId(dishPushPlan.getId());
        entity.setShareNumber(dishPushPlan.getShareNumber()+1);
        mPushNewDishService.updateNewDishPushPlan(entity);

        return redirect("/pushPlanNewDish");
    }


    @RequestMapping("/delete")
    public String deleteNewDishPushPlan(Model model, Long id) {
        if(id == null){
            return "fail";
        }
        boolean result = mPushNewDishService.deleteNewDishPushPlan(id);
        return "success";
    }

    @RequestMapping("/addNewDishPushPlanPage")
    public String redirectAddOrEditPage(Model model) {
        model.addAttribute("newDishPlan", new NewDishPushModel());
        return "dishmarketingadd";
    }


    @RequestMapping("/editPage")
    public String editPage(Model model, Long id) {
        PushPlanNewDishEntity newDishPlan = mPushNewDishService.queryByid(id);
        NewDishPushModel newDishPushmodel = new NewDishPushModel(newDishPlan);
        model.addAttribute("newDishPlan", newDishPushmodel);
        return "dishmarketingadd";
    }

    @RequestMapping("/effect")
    public String effect(Model model,NewDishPushSearchModel newDishPushModel){

        model.addAttribute("newDishPushModel", newDishPushModel);

        return "push_dish_effect";
    }

    @RequestMapping("/effectDetail")
    public String effectDetail(Model model,NewDishPushSearchModel newDishPushModel){

        try {
            Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
            Long shopIdentity = LoginManager.get().getUser().getShopIdenty();

            OperationalRecordsEntity entity = new OperationalRecordsEntity();
            entity.setBrandIdenty(brandIdentity);
            entity.setShopIdenty(shopIdentity);
            entity.setActivityId(newDishPushModel.getActivityId());
            entity.setType(newDishPushModel.getType());
            entity.setCustomerName(newDishPushModel.getCustomerName());
            entity.setCustomerPhone(newDishPushModel.getCustomerPhone());
            entity.setOperationalCount(newDishPushModel.getOperationalCount());
            entity.setSource(2);

            Page<OperationalRecordsEntity> listPage = mOperationalRecordsService.queryByActivityId(entity,newDishPushModel.getPageNo(),newDishPushModel.getPageSize());
            setWebPage(model, "/pushPlanNewDish/effectDetail", listPage, newDishPushModel);

            model.addAttribute("listData", listPage.getRecords());
            model.addAttribute("newDishPushModel", newDishPushModel);
            return "push_dish_effect_detail";

        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

    }
}

