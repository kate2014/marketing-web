package com.zhongmei.yunfu.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.model.CardTimeModel;
import com.zhongmei.yunfu.domain.entity.DishSetmealEntity;
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

import java.math.BigDecimal;
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
            return "fail";
        }

        return "card_time_list";
    }

    @RequestMapping("/gotoAddCardTime")
    public String gotoAddCardTime(Model model, CardTimeModel mCardTimeModel){
        try {
            DishShopEntity mDishShopEntity = new DishShopEntity();
            mCardTimeModel.setIsAllDish(1);

            if(mCardTimeModel.getId() != null && !mCardTimeModel.getId().equals("")){
                mDishShopEntity = mDishShopService.queryDishShopById(mCardTimeModel);

                if(mDishShopEntity.getType() == 3){
                    mCardTimeModel.setIsAllDish(1);
                }else if(mDishShopEntity.getType() == 4){
                    mCardTimeModel.setIsAllDish(2);
                }

                List<DishShopEntity> listShop = mDishShopService.listDishShop(mCardTimeModel.getId());

                model.addAttribute("listShop",listShop);
            }

            model.addAttribute("dishShop",mDishShopEntity);

            model.addAttribute("mCardTimeModel",mCardTimeModel);

        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }


        return "card_time_add";
    }

    @RequestMapping("/createCardTime")
    public String createCardTime(Model model, CardTimeModel mCardTimeModel){
        try {
            //isAllDish 1:部分   2: 全部
            Integer isAllDish = mCardTimeModel.getIsAllDish();

            if(isAllDish == 2){
                mCardTimeModel.setType(4);//可使用全部商品
            }else{
                mCardTimeModel.setType(3);//指定商品可用
            }

            if(mCardTimeModel.getId() != null && !mCardTimeModel.getId().equals("")){//编辑操作
                mDishShopService.modifyDishShop(mCardTimeModel);
                if(isAllDish == 1){
                    DishShopEntity mDishShopEntity = new DishShopEntity();
                    mDishShopEntity.setId(mCardTimeModel.getId());
                    DishSetmealGroupEntity mDishSetmealGroupEntity = mDishSetmealGroupService.queryDishSetmealGroupByDishId(mCardTimeModel.getId());
                    if(mDishSetmealGroupEntity == null){
                        mDishSetmealGroupEntity = mDishSetmealGroupService.addSetmealGroup(mCardTimeModel,mDishShopEntity);
                    }

                    mDishSetmealService.delectSetmealByDishId(mCardTimeModel.getId());
                    mDishSetmealService.addSetmeal(mCardTimeModel.getDishId(),mDishShopEntity,mDishSetmealGroupEntity);
                }else{
                    //如为全部品项时 因删除setmeal相关数据
                    mDishSetmealGroupService.delectSetmealGroup(mCardTimeModel.getId());
                    mDishSetmealService.delectSetmealByDishId(mCardTimeModel.getId());
                }

            }else{//新建操作

                DishShopEntity mDishShopEntity = mDishShopService.addDishShop(mCardTimeModel);
                if(mCardTimeModel.getType() == 3){
                    DishSetmealGroupEntity mDishSetmealGroupEntity = mDishSetmealGroupService.addSetmealGroup(mCardTimeModel,mDishShopEntity);
                    mDishSetmealService.addSetmeal(mCardTimeModel.getDishId(),mDishShopEntity,mDishSetmealGroupEntity);
                }


            }
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }


        return redirect("/cardTime/cardList");
    }

    @RequestMapping("/deleteCradTime")
    public String deleteDishShop(Model model, CardTimeModel mCardTimeModel){
        try {
            if(mCardTimeModel.getId() != null && !mCardTimeModel.equals("")){
                mDishShopService.deleteDishShop(mCardTimeModel.getId());
                mDishSetmealGroupService.delectSetmealGroup(mCardTimeModel.getId());
                mDishSetmealService.delectSetmealByDishId(mCardTimeModel.getId());

            }
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

        return "success";
    }
}
