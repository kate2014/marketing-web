package com.zhongmei.yunfu.controller;

import com.zhongmei.yunfu.controller.model.DishShopModel;
import com.zhongmei.yunfu.domain.entity.DishBrandTypeEntity;
import com.zhongmei.yunfu.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 商品管理控制器
 */
@Controller
@RequestMapping("/internal/dishShop")
public class DishShopController extends BaseController{

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

    @RequestMapping("/dishShopMainPage")
    public String dishShopMainPage(Model model, DishShopModel mDishShopModel) {

        model.addAttribute("mDishShopModel", mDishShopModel);
        model.addAttribute("dish_shop_manager", 1);

        return "dish_shop_main";

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

}
