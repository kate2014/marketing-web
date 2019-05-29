package com.zhongmei.yunfu.controller.brand;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.api.ApiResult;
import com.zhongmei.yunfu.controller.BaseController;
import com.zhongmei.yunfu.controller.model.NewDishPushModel;
import com.zhongmei.yunfu.controller.model.NewDishPushSearchModel;
import com.zhongmei.yunfu.domain.entity.PushPlanNewDishEntity;
import com.zhongmei.yunfu.domain.enums.StatusFlag;
import com.zhongmei.yunfu.service.LoginManager;
import com.zhongmei.yunfu.service.PushPlanNewDishService;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("/internal/brand/marketing/pushDish")
public class BrandPushDishController extends BaseController {

    @Autowired
    PushPlanNewDishService mPushNewDishService;

    @RequestMapping({"/list"})
    public String pushDishList(Model model, NewDishPushSearchModel newDishPushModel) {

        newDishPushModel.setSourceType(1);//品牌创建
        Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
        newDishPushModel.setBrandIdenty(brandIdentity);

        Page<PushPlanNewDishEntity> newDishPush = mPushNewDishService.list(newDishPushModel);

        List<NewDishPushModel> planModeList = new ArrayList<>();
        if (newDishPush != null && newDishPush.getRecords() != null) {
            for (PushPlanNewDishEntity plan : newDishPush.getRecords()) {
                NewDishPushModel newDishModel = new NewDishPushModel(plan);
                planModeList.add(newDishModel);
            }
        }

        setWebPage(model, "/internal/brand/marketing/pushDish/list", newDishPush, newDishPushModel);
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
        try {
            Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
            mPushNewDishService.enableNewDishPushPlan(newDishPlanModel.getId(),newDishPlanModel.getPlanState());

            if(newDishPlanModel.getPlanState() == 1){
                mPushNewDishService.batchUpdatePlanState(brandIdentity,newDishPlanModel.getId(),3);
            }else if(newDishPlanModel.getPlanState() == 2){
                mPushNewDishService.batchUpdatePlanState(brandIdentity,newDishPlanModel.getId(),2);
            }


        }catch (Exception e){
            e.printStackTrace();
        }


        return redirect("/internal/brand/marketing/pushDish/list");
    }

    @RequestMapping({"/deleteData"})
    public String deleteData(Model model, NewDishPushModel newDishPlanModel) {
        try {
            mPushNewDishService.deleteNewDishPushPlan(newDishPlanModel.getId());

            Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
            mPushNewDishService.batchDelateBySourceId(brandIdentity,newDishPlanModel.getId());
        }catch (Exception e){
            e.printStackTrace();
        }
        return redirect("/internal/brand/marketing/pushDish/list");
    }

    @RequestMapping({"/sendToShop"})
    @ResponseBody
    public String sendToShop(Model model, NewDishPushModel newDishPlanModel){

        try {
            Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();

            List<PushPlanNewDishEntity> listData = mPushNewDishService.queryBySourceId(brandIdentity,newDishPlanModel.getSourceId());

            PushPlanNewDishEntity mPushPlanNewDishEntity = mPushNewDishService.queryByid(newDishPlanModel.getSourceId());
            mPushPlanNewDishEntity.setPlanState(3);

            String shopList = newDishPlanModel.getSelectShopList();
            String[] tempList = shopList.split(",");

            List<PushPlanNewDishEntity> addListData = new ArrayList<>();
            List<Long> deleteListData = new ArrayList<>();
            List<PushPlanNewDishEntity> updateListData = new ArrayList<>();
            //判断新增和删除
            if(listData == null || listData.size() == 0){
                for(String shop : tempList){
                    Long shopIdentity = Long.valueOf(shop);

                    PushPlanNewDishEntity newEntity = new PushPlanNewDishEntity().cloneEntity(mPushPlanNewDishEntity,shopIdentity,mPushPlanNewDishEntity.getId());

                    addListData.add(newEntity);
                }
            }else{
                Map<Long,PushPlanNewDishEntity> tempMap = new HashMap<>();
                for(PushPlanNewDishEntity item : listData){
                    tempMap.put(item.getShopIdentity(),item);
                }
                for(String shop : tempList){
                    Long shopIdentity = Long.valueOf(shop);
                    PushPlanNewDishEntity entity = tempMap.get(shopIdentity);

                    //如你为空则表示已下发过，此时只需要执行update操作，并将状态都改为为接受状态
                    if(entity != null){
                        PushPlanNewDishEntity newEntity = new PushPlanNewDishEntity().cloneEntity(mPushPlanNewDishEntity,shopIdentity,mPushPlanNewDishEntity.getId());
                        newEntity.setId(entity.getId());
                        updateListData.add(newEntity);
                    }else{
                        //移除前后两次操作过的门店,未移除的这表示前面有选择推送，而本次取消推送的门店
                        PushPlanNewDishEntity newEntity = new PushPlanNewDishEntity().cloneEntity(mPushPlanNewDishEntity,shopIdentity,mPushPlanNewDishEntity.getId());
                        addListData.add(newEntity);
                    }

                    tempMap.remove(shopIdentity);
                }
                for(PushPlanNewDishEntity value : tempMap.values()){
                    deleteListData.add(value.getId());
                }

            }

            //批量操作
            if(addListData.size() != 0){
                mPushNewDishService.batchAddNewDishPushPlan(addListData);
            }
            if(updateListData.size() != 0){
                mPushNewDishService.batchUpdateDishPushPlan(updateListData);
            }
            if(deleteListData.size() != 0){
                mPushNewDishService.batchDeleteDishPushPlan(deleteListData);
            }

            PushPlanNewDishEntity newDishPushPlan = new PushPlanNewDishEntity();
            newDishPushPlan.setId(mPushPlanNewDishEntity.getId());
            newDishPushPlan.setScanNumber(tempList.length);

            mPushNewDishService.updateNewDishPushPlan(newDishPushPlan);
        }catch (Exception e){
            e.printStackTrace();
        }

        return "success";
    }

    @RequestMapping({"/refreshData"})
    @ResponseBody
    public String refreshData(Model model, NewDishPushModel newDishPlanModel){
        try {
            //刷新下发数据，如果门店未接受则直接覆盖，如果门店已接受则将状态修改为已更新为接受
            Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
            //查询出所有下发门店数据
            List<PushPlanNewDishEntity> listData = mPushNewDishService.queryBySourceId(brandIdentity,newDishPlanModel.getSourceId());
            //查询品牌创建的新品活动
            PushPlanNewDishEntity mPushPlanNewDishEntity = mPushNewDishService.queryByid(newDishPlanModel.getSourceId());

            for(PushPlanNewDishEntity entity : listData){
                int planState = entity.getPlanState();
                //1, 进行中;2, 停止; 3：品牌下发未接受 4：品牌下发已接受  5：数据刷新未接受
                if(planState == 1 || planState == 2 || planState == 4){ //更新数据并将状态修改为数据刷新未接受
                    PushPlanNewDishEntity newEntity = new PushPlanNewDishEntity().cloneEntity(mPushPlanNewDishEntity,entity.getShopIdentity(),mPushPlanNewDishEntity.getId());
                    newEntity.setId(entity.getId());
                    newEntity.setPlanState(5);
                    mPushNewDishService.updateNewDishPushPlan(newEntity);
                }else if(planState == 3 || planState == 5){ //直接更新数据不变更当时状态
                    PushPlanNewDishEntity newEntity = new PushPlanNewDishEntity().cloneEntity(mPushPlanNewDishEntity,entity.getShopIdentity(),mPushPlanNewDishEntity.getId());
                    newEntity.setId(entity.getId());
                    newEntity.setPlanState(planState);
                    mPushNewDishService.updateNewDishPushPlan(newEntity);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

        return "success";
    }
}
