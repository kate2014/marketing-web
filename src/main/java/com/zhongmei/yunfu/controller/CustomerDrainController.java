package com.zhongmei.yunfu.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.model.CustomerDrainSearchModel;
import com.zhongmei.yunfu.controller.model.CustomerSearchModel;
import com.zhongmei.yunfu.controller.model.excel.ExcelData;
import com.zhongmei.yunfu.controller.model.excel.ExcelUtils;
import com.zhongmei.yunfu.domain.entity.CustomerEntity;
import com.zhongmei.yunfu.domain.entity.CustomerSearchRuleEntity;
import com.zhongmei.yunfu.service.CustomerService;
import com.zhongmei.yunfu.service.LoginManager;
import com.zhongmei.yunfu.util.DateFormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    @RequestMapping("/export/excel")
    public void exportExcel(HttpServletResponse response, CustomerDrainSearchModel searchModel) throws Exception {
        LoginManager.setUser(searchModel);
        Page<CustomerEntity> listPage = customerService.findListPage(searchModel, null);

        ExcelData data = new ExcelData();
        data.setSheetName("会员");
        List<String> titles = new ArrayList();
        titles.add("序");
        titles.add("姓名");
        titles.add("性别");
        titles.add("生日");
        titles.add("手机号");
        titles.add("会员等级");
        titles.add("邮箱");
        titles.add("喜好");
        titles.add("所在地址");
        titles.add("备注");
        data.setTitles(titles);

        List<List<Object>> rows = new ArrayList();
        data.setRows(rows);

        int i = 1;
        for (CustomerEntity entity : listPage.getRecords()) {
            List<Object> row = new ArrayList();
            rows.add(row);
            row.add(i++);
            row.add(entity.getName());
            row.add(entity.getGender() != null ? entity.getGender() == 1 ? "男" : "女" : "");
            row.add(DateFormatUtil.formatDate(entity.getBirthday()));
            row.add(entity.getMobile());
            row.add(entity.getGroupLevel());
            row.add(entity.getEmail());
            row.add(entity.getHobby());
            row.add(entity.getAddress());
            row.add(entity.getProfile());
        }

        SimpleDateFormat fdate = new SimpleDateFormat("yyyyMMdd");
        String fileName = String.format("customer-%s.xls", fdate.format(new Date()));
        ExcelUtils.exportExcel(response, fileName, data);
    }
}

