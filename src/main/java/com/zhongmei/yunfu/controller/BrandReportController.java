package com.zhongmei.yunfu.controller;

import com.zhongmei.yunfu.controller.model.AuthUserModel;
import com.zhongmei.yunfu.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/internal/brand")
public class BrandReportController extends BaseController{

    @Autowired
    TradeService mTradeService;

    @RequestMapping({"/report"})
    public String mianPage(Model model, AuthUserModel mAuthUserModel) {


        model.addAttribute("mAuthUserModel", mAuthUserModel);
        return "brand_main";
    }
}
