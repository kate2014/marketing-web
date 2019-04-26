package com.zhongmei.yunfu.controller.brand;

import com.zhongmei.yunfu.controller.BaseController;
import com.zhongmei.yunfu.controller.model.ShopSearchModel;
import com.zhongmei.yunfu.service.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 品牌员工管理
 */
@Controller
@RequestMapping("/internal/brand")
public class BrandStaffController extends BaseController {

    @Autowired
    AuthUserService mAuthUserService;

    @RequestMapping({"/staffList"})
    public String staffList(Model model, ShopSearchModel mShopSearchModel) {

        return "brand_staff_list";
    }
}
