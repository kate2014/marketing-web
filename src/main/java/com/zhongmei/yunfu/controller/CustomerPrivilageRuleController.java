package com.zhongmei.yunfu.controller;


import com.zhongmei.yunfu.service.CustomerPrivilageRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

/**
 * <p>
 * 会员权益设置表 前端控制器
 * </p>
 *
 * @author pigeon88
 * @since 2019-06-04
 */
@Controller
@RequestMapping("/internal/customerPrivilageRule")
public class CustomerPrivilageRuleController {

    @Autowired
    CustomerPrivilageRuleService mCustomerPrivilageRuleService;

    @RequestMapping("/gotoSettingPage")
    public String gotoSettingPage(){

        return "customer_save_rule_setting";
    }
}

