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
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            model.addAttribute("listType", listType);

            CustomerDishPrivilegeEntity mCustomerDishPrivilegeEntity = new CustomerDishPrivilegeEntity();
            mCustomerDishPrivilegeEntity.setBrandIdenty(mCustomerDishPrivilegeModel.getBrandIdenty());
            mCustomerDishPrivilegeEntity.setShopIdenty(mCustomerDishPrivilegeModel.getShopIdenty());
            mCustomerDishPrivilegeEntity.setLevelId(mCustomerDishPrivilegeModel.getLevelId());
            List<CustomerDishPrivilegeEntity> listCDP = mCustomerDishPrivilegeService.queryDishPrivilege(mCustomerDishPrivilegeEntity);

            Map<Long,CustomerDishPrivilegeEntity> tempMap = new HashMap<>();
            for(CustomerDishPrivilegeEntity entity : listCDP){
                tempMap.put(entity.getDishId(),entity);
            }

            //获取门店下所以品项
            DishShopEntity mDishShopEntity = new DishShopEntity();
            mDishShopEntity.setBrandIdenty(mCustomerDishPrivilegeModel.getBrandIdenty());
            mDishShopEntity.setShopIdenty(mCustomerDishPrivilegeModel.getShopIdenty());
            mDishShopEntity.setName(mCustomerDishPrivilegeModel.getDishName());
            mDishShopEntity.setDishTypeId(mCustomerDishPrivilegeModel.getDishType());
            List<DishShopEntity> listDish = mDishShopService.queryAllDishShop(mDishShopEntity);

            List<CustomerDishPrivilegeModel> listData = new ArrayList<>();
            for(DishShopEntity dishShop : listDish){
                CustomerDishPrivilegeModel entity = new CustomerDishPrivilegeModel();
                entity.setDishId(dishShop.getId());
                entity.setDishName(dishShop.getName());
                entity.setMarketPrice(dishShop.getMarketPrice());

                CustomerDishPrivilegeEntity cdp = tempMap.get(dishShop.getId());
                if(cdp != null){
                    entity.setPrivilegeType(cdp.getPrivilegeType());
                    entity.setPrivilegeValue(cdp.getPrivilegeValue());
                    entity.setChecked(true);
                }else {
                    entity.setChecked(false);
                }

                listData.add(entity);
            }

            model.addAttribute("listData", listData);

            model.addAttribute("mCustomerDishPrivilegeModel", mCustomerDishPrivilegeModel);
        }catch (Exception e){
            e.printStackTrace();
        }

        return "dish_privilege_setting";
    }

    @RequestMapping("/modfityData")
    @ResponseBody
    public String modfityData(Model model, CustomerDishPrivilegeModel mCustomerDishPrivilegeModel){

        try {
            List<CustomerDishPrivilegeEntity> listPrivilege = new ArrayList<>();
            String selectDishList = mCustomerDishPrivilegeModel.getSelectDishList();
            String[] settingValue = selectDishList.split("#");
            for(String value : settingValue){
                CustomerDishPrivilegeEntity entity = new CustomerDishPrivilegeEntity();
                String[] select = value.split("&");
                entity.setLevelId(mCustomerDishPrivilegeModel.getLevelId());
                entity.setDishId(Long.parseLong(select[0]));
                entity.setPrivilegeType(Integer.parseInt(select[1]));
                entity.setPrivilegeValue(new BigDecimal(select[2]));
                entity.setBrandIdenty(mCustomerDishPrivilegeModel.getBrandIdenty());
                entity.setShopIdenty(mCustomerDishPrivilegeModel.getShopIdenty());
                entity.setCreatorId(mCustomerDishPrivilegeModel.getCreatorId());
                entity.setCreatorName(mCustomerDishPrivilegeModel.getCreatorName());
                entity.setUpdatorId(mCustomerDishPrivilegeModel.getCreatorId());
                entity.setUpdatorName(mCustomerDishPrivilegeModel.getCreatorName());
                entity.setStatusFlag(1);
                listPrivilege.add(entity);
            }
            mCustomerDishPrivilegeService.deleteAllForShop(mCustomerDishPrivilegeModel.getBrandIdenty(),mCustomerDishPrivilegeModel.getShopIdenty(),mCustomerDishPrivilegeModel.getLevelId());
            mCustomerDishPrivilegeService.insertBatch(listPrivilege);


        }catch (Exception e){
            e.printStackTrace();
        }

        return "success";
    }

}