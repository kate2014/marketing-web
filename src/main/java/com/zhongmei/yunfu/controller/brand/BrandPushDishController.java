package com.zhongmei.yunfu.controller.brand;

import com.zhongmei.yunfu.controller.BaseController;
import com.zhongmei.yunfu.controller.model.ShopSearchModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/internal/brand/marketing/pushDish")
public class BrandPushDishController extends BaseController {

    @RequestMapping({"/list"})
    public String pushDishList(Model model, ShopSearchModel mShopSearchModel) {

        return "brand_push_dish_list";
    }
}
