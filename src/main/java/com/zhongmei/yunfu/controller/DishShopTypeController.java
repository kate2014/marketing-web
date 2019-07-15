package com.zhongmei.yunfu.controller;

import com.zhongmei.yunfu.controller.model.DishShopModel;
import com.zhongmei.yunfu.domain.entity.AuthUserEntity;
import com.zhongmei.yunfu.domain.entity.DishBrandTypeEntity;
import com.zhongmei.yunfu.service.*;
import com.zhongmei.yunfu.util.ToolsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * 商品管理控制器
 */
@Controller
@RequestMapping("/dishShopType")
public class DishShopTypeController extends BaseController{

    @Autowired
    DishShopService mDishShopService;
    @Autowired
    DishBrandTypeService mDishBrandTypeService;
    @Autowired
    DishPropertyService mDishPropertyService;
    @Autowired
    DishSetmealService mDishSetmealService;
    @Autowired
    DishSetmealGroupService mDishSetmealGroupService;
    @Autowired
    AuthUserService authUserService;

    @RequestMapping("/dishShopMainPage")
    public String dishShopMainPage(Model model, DishShopModel mDishShopModel) {

        Long shopId = mDishShopModel.getShopIdenty();
        Long brandId = mDishShopModel.getBrandIdenty();
        Long creatorId = mDishShopModel.getCreatorId();
        String creatorName = mDishShopModel.getCreatorName();

        if(LoginManager.get().getUser() == null){
            LoginManager.get().setLoginUser(new AuthUserEntity());
        }
        LoginManager.get().getUser().setCreatorId(creatorId);

        LoginManager.get().getUser().setCreatorName(creatorName);

        LoginManager.get().getUser().setShopIdenty(shopId);
        LoginManager.get().getUser().setBrandIdenty(brandId);


        model.addAttribute("mDishShopModel", mDishShopModel);

        Map<String, String> permissionData = authUserService.getAuthPermissionMap(creatorId,shopId);

        if(permissionData.get("DISH_GROUP_AUTHORITY") == null || permissionData.get("DISH_GROUP_AUTHORITY").equals("")){
            model.addAttribute("dish_group_authority", 0);
        }else{
            model.addAttribute("dish_group_authority", 1);
        }

        if(permissionData.get("DISH_AUTHORITY") == null || permissionData.get("DISH_AUTHORITY").equals("")){
            model.addAttribute("dish_authority", 0);
        }else{
            model.addAttribute("dish_authority", 1);
        }

        if(permissionData.get("DISH_CARDTIME_AUTHORITY") == null || permissionData.get("DISH_CARDTIME_AUTHORITY").equals("")){
            model.addAttribute("dish_cradtime_authority", 0);
        }else{
            model.addAttribute("dish_cradtime_authority", 1);
        }

        if(permissionData.get("SUPPLIER_AUTHORITY") == null || permissionData.get("SUPPLIER_AUTHORITY").equals("")){
            model.addAttribute("supplier_authority", 0);
        }else{
            model.addAttribute("supplier_authority", 1);
        }

        return "dish_shop_main";

    }

    @RequestMapping("/emptyPage")
    public String emptyPage(){
        return "dish_modle_empty";
    }

    @RequestMapping("/dishGroup")
    public String dishGroup(Model model, DishShopModel mDishShopModel){
        try {
            DishBrandTypeEntity mDishBrandTypeEntity = new DishBrandTypeEntity();
            mDishBrandTypeEntity.setBrandIdenty(mDishShopModel.getBrandIdenty());
            mDishBrandTypeEntity.setShopIdenty(mDishShopModel.getShopIdenty());
            List<DishBrandTypeEntity> listType = mDishBrandTypeService.queryAllDishType(mDishBrandTypeEntity);

            List<DishShopModel> listData = new LinkedList<>();
            Map<Long,List<DishBrandTypeEntity>> tempMap = new HashMap<>();

            for(DishBrandTypeEntity entity : listType){
                if(entity.getParentId() == null || entity.getParentId().equals("")){
                    DishShopModel mModel = new DishShopModel();
                    mModel.setDishBrandTypeEntity(entity);
                    listData.add(mModel);
                }else{
                    List<DishBrandTypeEntity> listValue = tempMap.get(entity.getParentId());
                    if(listValue == null){
                        listValue = new LinkedList<>();
                        tempMap.put(entity.getParentId(),listValue);
                    }
                    listValue.add(entity);
                }
            }

            for(DishShopModel dishModel : listData){
                DishBrandTypeEntity entity = dishModel.getDishBrandTypeEntity();
                List<DishBrandTypeEntity> listChild = tempMap.get(entity.getId());
                dishModel.setListType(listChild);
            }


            model.addAttribute("listData", listData);
            model.addAttribute("mDishShopModel", mDishShopModel);
            return "dish_shop_group";

        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }
    }

    @RequestMapping("/modfityType")
    @ResponseBody
    public String modfityType(Model model, DishShopModel mDishShopModel){
        String actionSuccess = "success";
        try {
            DishBrandTypeEntity mDishBrandTypeEntity = new DishBrandTypeEntity();
            mDishBrandTypeEntity.setId(mDishShopModel.getDishTypeId());
            mDishBrandTypeEntity.setTypeCode(mDishShopModel.getTypeCode());
            mDishBrandTypeEntity.setName(mDishShopModel.getTypeName());
            mDishBrandTypeEntity.setServerUpdateTime(new Date());
            mDishBrandTypeEntity.setUpdatorId(mDishShopModel.getCreatorId());
            mDishBrandTypeEntity.setUpdatorName(mDishShopModel.getCreatorName());

            boolean isSuccess = mDishBrandTypeService.modfityDishType(mDishBrandTypeEntity);
            if(isSuccess){
                return "success";
            }else {
                return "fail";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

    }

    @RequestMapping("/addDishType")
    @ResponseBody
    public String addDishType(Model model, DishShopModel mDishShopModel){
        String actionSuccess = "success";
        try {
            DishBrandTypeEntity mDishBrandTypeEntity = new DishBrandTypeEntity();
            mDishBrandTypeEntity.setParentId(mDishShopModel.getParentId());
            mDishBrandTypeEntity.setTypeCode(mDishShopModel.getTypeCode());
            mDishBrandTypeEntity.setName(mDishShopModel.getTypeName());
            mDishBrandTypeEntity.setAliasName("");
            mDishBrandTypeEntity.setSort(1);//目前该字段未使用
            mDishBrandTypeEntity.setOrder(1);
            mDishBrandTypeEntity.setUuid(ToolsUtil.genOnlyIdentifier());
            mDishBrandTypeEntity.setBrandIdenty(mDishShopModel.getBrandIdenty());
            mDishBrandTypeEntity.setShopIdenty(mDishShopModel.getShopIdenty());
            mDishBrandTypeEntity.setEnabledFlag(1);
            mDishBrandTypeEntity.setStatusFlag(1);
            mDishBrandTypeEntity.setCure(2);
            mDishBrandTypeEntity.setServerUpdateTime(new Date());
            mDishBrandTypeEntity.setCreatorId(mDishShopModel.getCreatorId());
            mDishBrandTypeEntity.setCreatorName(mDishShopModel.getCreatorName());
            mDishBrandTypeEntity.setUpdatorId(mDishShopModel.getCreatorId());
            mDishBrandTypeEntity.setUpdatorName(mDishShopModel.getCreatorName());

            boolean isSuccess = mDishBrandTypeService.addDishType(mDishBrandTypeEntity);
            if(isSuccess){
                return "success";
            }else {
                return "fail";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

    }

    @RequestMapping("/deleteType")
    @ResponseBody
    public String deleteType(Model model, DishShopModel mDishShopModel){
        try {
            boolean isSuccess = mDishBrandTypeService.deleteDishType(mDishShopModel.getDishTypeId());
            if(isSuccess){
                return "success";
            }else {
                return "fail";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

    }

}
