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
@RequestMapping("/internal/brand/send")
public class BrandSendToShopController extends BaseController {

    @Autowired
    PushPlanNewDishService mPushNewDishService;

    @RequestMapping({"/pushDish"})
    public String pushDishList(Model model, ShopSearchModel mShopSearchModel) {

        System.out.println("======mShopSearchModel===="+mShopSearchModel.getSelectShop().size());

        return "brand_push_dish_list";
    }
}
