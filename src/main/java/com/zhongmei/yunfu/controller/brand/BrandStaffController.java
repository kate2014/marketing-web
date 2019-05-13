package com.zhongmei.yunfu.controller.brand;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.BaseController;
import com.zhongmei.yunfu.controller.model.AuthUserModel;
import com.zhongmei.yunfu.domain.entity.AuthUserEntity;
import com.zhongmei.yunfu.service.AuthRoleService;
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
    @Autowired
    AuthRoleService mAuthRoleService;

    @RequestMapping({"/staffList"})
    public String staffList(Model model, AuthUserModel mAuthUserModel) {

        try{
            Page<AuthUserEntity> listAuthUser = mAuthUserService.queryAuthUserByBrand(mAuthUserModel,mAuthUserModel.getPageNo(),mAuthUserModel.getPageSize());
            setWebPage(model, "/internal/brand/staffList", listAuthUser, mAuthUserModel);
            model.addAttribute("mAuthUserModel", mAuthUserModel);
            model.addAttribute("list", listAuthUser.getRecords());

            model.addAttribute("opType", 0);

        }catch (Exception e){
            e.printStackTrace();
        }

        return "brand_staff_list";
    }
}
