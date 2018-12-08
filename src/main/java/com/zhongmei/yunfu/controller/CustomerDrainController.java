package com.zhongmei.yunfu.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.model.CustomerDrainSearchModel;
import com.zhongmei.yunfu.domain.entity.CustomerEntity;
import com.zhongmei.yunfu.service.CustomerService;
import com.zhongmei.yunfu.service.LoginManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author yangyp
 * @since 2018-08-29
 */
@Controller
@RequestMapping("/customer/drain")
public class CustomerDrainController extends BaseController {

    @Autowired
    CustomerService customerService;

    @RequestMapping
    public String drain(Model model, CustomerDrainSearchModel searchModel) {
        LoginManager.setUser(searchModel);
        Page<CustomerEntity> listPage = customerService.findListPage(searchModel);
        setWebPage(model, "/customer/drain", listPage, searchModel);
        model.addAttribute("searchModel", searchModel);
        model.addAttribute("list", listPage.getRecords());
        return "customerdrain";
    }

    @RequestMapping("/{customerId}/report/expense")
    public String drainReportExpense(Model model, @PathVariable Long customerId) {
        Map<String, String> listPage = customerService.expenseReport(customerId);
        model.addAttribute("listDate", JSON.toJSONString(listPage.keySet()));
        model.addAttribute("listExpense", listPage.values());
        return "customer_report_expense";
    }

}

