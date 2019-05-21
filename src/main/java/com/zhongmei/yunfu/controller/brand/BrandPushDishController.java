package com.zhongmei.yunfu.controller.brand;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.BaseController;
import com.zhongmei.yunfu.controller.model.NewDishPushModel;
import com.zhongmei.yunfu.controller.model.NewDishPushSearchModel;
import com.zhongmei.yunfu.controller.model.ShopSearchModel;
import com.zhongmei.yunfu.domain.entity.PushPlanNewDishEntity;
import com.zhongmei.yunfu.domain.enums.StatusFlag;
import com.zhongmei.yunfu.service.LoginManager;
import com.zhongmei.yunfu.service.PushPlanNewDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/internal/brand/marketing/pushDish")
public class BrandPushDishController extends BaseController {

    @Autowired
    PushPlanNewDishService mPushNewDishService;

    @RequestMapping({"/list"})
    public String pushDishList(Model model, NewDishPushSearchModel newDishPushModel) {

        newDishPushModel.setBrandIdenty(LoginManager.get().getUser().getBrandIdenty());

        newDishPushModel.setSourceType(1);//品牌创建

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
        model.addAttribute("newDishPushModel", newDishPushModel);

        return "brand_push_dish_list";
    }

    @RequestMapping({"/gotoEditPage"})
    public String goToAddPushDish(Model model, NewDishPushModel mNewDishPushModel) {

        PushPlanNewDishEntity newDishPlan = new PushPlanNewDishEntity();
        if(mNewDishPushModel.getId() != null){
            newDishPlan = mPushNewDishService.queryByid(mNewDishPushModel.getId());
        }

        model.addAttribute("newDishPlan", newDishPlan);

        return "brand_push_dish_add";
    }

    @RequestMapping({"/addOrEdit"})
    public String addPushDish(Model model, NewDishPushModel newDishPlanModel) {

        boolean result = false;
        PushPlanNewDishEntity newDishPlan = newDishPlanModel.obtainNewDishPlan();

        newDishPlan.setName(newDishPlan.getDishName());
        newDishPlan.setStatusFlag(StatusFlag.VALiD.value());

        Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
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
            newDishPlan.setSourceType(1);
            newDishPlan.setScanNumber(0);
            newDishPlan.setShareNumber(0);
            newDishPlan.setCreatorId(LoginManager.get().getUser().getCreatorId());
            newDishPlan.setCreatorName(LoginManager.get().getUser().getCreatorName());
            newDishPlan.setServerCreateTime(new Date());
            result = mPushNewDishService.addNewDishPushPlan(newDishPlan);
        }

        return redirect("/internal/brand/marketing/pushDish/list");
    }

    @RequestMapping({"/updateStatus"})
    public String updateStatus(Model model, NewDishPushModel newDishPlanModel) {
        mPushNewDishService.enableNewDishPushPlan(newDishPlanModel.getId(),newDishPlanModel.getPlanState());
        return redirect("/internal/brand/marketing/pushDish/list");
    }

    @RequestMapping({"/deleteData"})
    public String deleteData(Model model, NewDishPushModel newDishPlanModel) {
        mPushNewDishService.deleteNewDishPushPlan(newDishPlanModel.getId());
        return redirect("/internal/brand/marketing/pushDish/list");
    }

}
