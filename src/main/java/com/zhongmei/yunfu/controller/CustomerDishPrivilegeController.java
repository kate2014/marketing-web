package com.zhongmei.yunfu.controller;


import com.zhongmei.yunfu.controller.model.CustomerDishPrivilegeModel;
import com.zhongmei.yunfu.domain.CustomerDishPrivilegeEntity;
import com.zhongmei.yunfu.domain.entity.DishBrandTypeEntity;
import com.zhongmei.yunfu.domain.entity.DishShopEntity;
import com.zhongmei.yunfu.service.CustomerDishPrivilegeService;
import com.zhongmei.yunfu.service.DishBrandTypeService;
import com.zhongmei.yunfu.service.DishShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 会员特价商品
 */
@Controller
@RequestMapping("/internal/customerDishPrivilege")
public class CustomerDishPrivilegeController {

    @Autowired
    DishShopService mDishShopService;

    @Autowired
    DishBrandTypeService mDishBrandTypeService;

    @Autowired
    CustomerDishPrivilegeService mCustomerDishPrivilegeService;

    @RequestMapping("/gotoDishPrivilegePage")
    public String gotoDishPrivilegePage(Model model, CustomerDishPrivilegeModel mCustomerDishPrivilegeModel){

        model.addAttribute("model", mCustomerDishPrivilegeModel);
        return "customer_dish_privilege_setting";
    }

    @RequestMapping("/queryAllDishData")
    public String queryAllDishData(Model model, CustomerDishPrivilegeModel mCustomerDishPrivilegeModel){

        try {
            //获取门店内所以品项类别
            DishBrandTypeEntity mDishBrandTypeEntity = new DishBrandTypeEntity();
            mDishBrandTypeEntity.setBrandIdenty(mCustomerDishPrivilegeModel.getBrandIdenty());
            mDishBrandTypeEntity.setShopIdenty(mCustomerDishPrivilegeModel.getShopIdenty());
            List<DishBrandTypeEntity> listType = mDishBrandTypeService.queryDishType(mDishBrandTypeEntity);
//            model.addAttribute("listType", listType);

            CustomerDishPrivilegeEntity mCustomerDishPrivilegeEntity = new CustomerDishPrivilegeEntity();
            List<CustomerDishPrivilegeEntity> listCDP = mCustomerDishPrivilegeService.queryDishPrivilege(mCustomerDishPrivilegeEntity);

            //获取门店下所以品项
            DishShopEntity mDishShopEntity = new DishShopEntity();
            mDishShopEntity.setBrandIdenty(mCustomerDishPrivilegeModel.getBrandIdenty());
            mDishShopEntity.setShopIdenty(mCustomerDishPrivilegeModel.getShopIdenty());
            List<DishShopEntity> listDish = mDishShopService.queryAllDishShop(mDishShopEntity);

            for(DishShopEntity dishShop : listDish){

            }

            model.addAttribute("listDish", listDish);

            model.addAttribute("mCustomerDishPrivilegeModel", model);
        }catch (Exception e){
            e.printStackTrace();
        }

        return "dish_privilege_setting";
    }

}