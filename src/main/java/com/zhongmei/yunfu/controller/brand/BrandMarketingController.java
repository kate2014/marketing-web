package com.zhongmei.yunfu.controller.brand;

import com.zhongmei.yunfu.controller.BaseController;
import com.zhongmei.yunfu.controller.model.ShopSearchModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/internal/brand/marketing")
public class BrandMarketingController extends BaseController {

    @RequestMapping({"/main"})
    public String shopList(Model model, ShopSearchModel mShopSearchModel) {

        return "brand_marketing_main";
    }
}
