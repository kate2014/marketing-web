package com.zhongmei.yunfu.controller.brand;

import com.zhongmei.yunfu.controller.BaseController;
import com.zhongmei.yunfu.controller.model.AuthUserModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 品项管理
 */
@Controller
@RequestMapping("/brand/dish")
public class BrandDishController extends BaseController {

    @RequestMapping({"/main"})
    public String mianPage(Model model, AuthUserModel mAuthUserModel) {

        model.addAttribute("mAuthUserModel", mAuthUserModel);
        return "brand_main";
    }

}
