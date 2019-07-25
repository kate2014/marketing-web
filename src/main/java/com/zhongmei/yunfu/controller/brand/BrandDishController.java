package com.zhongmei.yunfu.controller.brand;

import com.zhongmei.yunfu.controller.BaseController;
import com.zhongmei.yunfu.controller.model.AuthUserModel;
import com.zhongmei.yunfu.domain.entity.AuthUserEntity;
import com.zhongmei.yunfu.service.LoginManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 品项管理
 */
@Controller
@RequestMapping("/internal/brand/dish")
public class BrandDishController extends BaseController {

    @RequestMapping({"/main"})
    public String mianPage(Model model, AuthUserModel mAuthUserModel) {

        Long shopId = mAuthUserModel.getShopIdenty();
        Long brandId = mAuthUserModel.getBrandIdenty();
        Long creatorId = mAuthUserModel.getCreatorId();
        String creatorName = mAuthUserModel.getCreatorName();

        if(LoginManager.get().getUser() == null){
            LoginManager.get().setLoginUser(new AuthUserEntity());
        }
        LoginManager.get().getUser().setCreatorId(creatorId);

        LoginManager.get().getUser().setCreatorName(creatorName);

        LoginManager.get().getUser().setShopIdenty(shopId);
        LoginManager.get().getUser().setBrandIdenty(brandId);

        model.addAttribute("mAuthUserModel", mAuthUserModel);
        return "brand_dish_main";
    }

}
