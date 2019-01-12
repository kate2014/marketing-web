package com.zhongmei.yunfu.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.model.CardTimeModel;
import com.zhongmei.yunfu.domain.entity.DishSetmealGroupEntity;
import com.zhongmei.yunfu.domain.entity.DishShopEntity;
import com.zhongmei.yunfu.service.DishSetmealGroupService;
import com.zhongmei.yunfu.service.DishSetmealService;
import com.zhongmei.yunfu.service.DishShopService;
import com.zhongmei.yunfu.service.LoginManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cardTime")
public class CardTimeController extends BaseController{

    @Autowired
    DishShopService mDishShopService;
    @Autowired
    DishSetmealGroupService mDishSetmealGroupService;
    @Autowired
    DishSetmealService mDishSetmealService;

    @RequestMapping("/cardList")
    public String queryCardTimeList(Model model, CardTimeModel mCardTimeModel) {
        try {
            Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
            Long shopIdentity = LoginManager.get().getUser().getShopIdenty();

            mCardTimeModel.setShopIdenty(shopIdentity);
            mCardTimeModel.setBrandIdenty(brandIdentity);

            Page<DishShopEntity> listData = mDishShopService.queryDishShopList(mCardTimeModel);
            setWebPage(model, "/cardTime/cardList", listData, mCardTimeModel);
            model.addAttribute("list",listData.getRecords());
            model.addAttribute("mCardTimeModel",mCardTimeModel);

        }catch (Exception e){
            e.printStackTrace();
        }

        return "card_time_list";
    }

    @RequestMapping("/gotoAddCardTime")
    public String gotoAddCardTime(Model model, CardTimeModel mCardTimeModel){
        try {
            DishShopEntity mDishShopEntity = new DishShopEntity();

            if(mCardTimeModel.getId() != null && !mCardTimeModel.getId().equals("")){
                mDishShopEntity = mDishShopService.queryDishShopById(mCardTimeModel);
            }

            model.addAttribute("dishShop",mDishShopEntity);
            model.addAttribute("mCardTimeModel",mCardTimeModel);

        }catch (Exception e){
            e.printStackTrace();
        }


        return "card_time_add";
    }

    @RequestMapping("/createCardTime")
    public String createCardTime(Model model, CardTimeModel mCardTimeModel){

        try {
            Integer isAllDish = mCardTimeModel.getIsAllDish();
            List<Long> dishId = mCardTimeModel.getDishId();
            //isAllDish 1:部分   2: 全部
            if(isAllDish == 2){
                mCardTimeModel.setType(4);//可使用全部商品
            }else{
                mCardTimeModel.setType(3);//指定商品可用
            }

            DishShopEntity mDishShopEntity = mDishShopService.addDishShop(mCardTimeModel);
            if(mCardTimeModel.getType() == 3){
                DishSetmealGroupEntity mDishSetmealGroupEntity = mDishSetmealGroupService.addSetmealGroup(mCardTimeModel,mDishShopEntity);
                mDishSetmealService.addSetmeal(mCardTimeModel.getDishId(),mDishShopEntity,mDishSetmealGroupEntity);
            }

        }catch (Exception e){
            e.printStackTrace();
        }


        return redirect("/cardTime/cardList");
    }

    @RequestMapping("/deleteCradTime")
    public String deleteDishShop(Model model, CardTimeModel mCardTimeModel){

        return redirect("/cardTime/cardList");
    }
}
