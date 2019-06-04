package com.zhongmei.yunfu.controller;


import com.zhongmei.yunfu.controller.model.CustomerPrivilageRuleModel;
import com.zhongmei.yunfu.domain.entity.CustomerPrivilageRuleEntity;
import com.zhongmei.yunfu.service.CustomerPrivilageRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

import java.util.List;

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
    public String gotoSettingPage(Model model, CustomerPrivilageRuleModel ruleModel){
        try {
            List<CustomerPrivilageRuleEntity> listData = mCustomerPrivilageRuleService.queryAllRule(ruleModel);

            model.addAttribute("listData", listData);

            model.addAttribute("ruleModel", ruleModel);
            return "customer_privilage_setting";
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

    }
}

