package com.zhongmei.yunfu.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.api.ApiResponseStatus;
import com.zhongmei.yunfu.api.ApiResponseStatusException;
import com.zhongmei.yunfu.controller.model.CustomerEditModel;
import com.zhongmei.yunfu.controller.model.CustomerSearchModel;
import com.zhongmei.yunfu.controller.model.excel.ExcelData;
import com.zhongmei.yunfu.controller.model.excel.ExcelUtils;
import com.zhongmei.yunfu.core.security.Password;
import com.zhongmei.yunfu.domain.entity.AuthUserEntity;
import com.zhongmei.yunfu.domain.entity.CustomerEntity;
import com.zhongmei.yunfu.domain.entity.CustomerSearchRuleEntity;
import com.zhongmei.yunfu.domain.enums.StatusFlag;
import com.zhongmei.yunfu.service.AuthUserService;
import com.zhongmei.yunfu.service.CustomerSearchRuleService;
import com.zhongmei.yunfu.service.CustomerService;
import com.zhongmei.yunfu.service.LoginManager;
import com.zhongmei.yunfu.util.DateFormatUtil;
import org.apache.commons.lang.StringUtils;
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

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author yangyp
 * @since 2018-08-29
 */
@Controller
@RequestMapping("/customer")
public class CustomerController extends BaseController {

    @Autowired
    AuthUserService authUserService;

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerSearchRuleService customerSearchRuleService;

    @RequestMapping
    public String modle() {
        return "custmermodle";
    }

    @RequestMapping("/list")
    public String list(Model model, CustomerSearchModel searchModel) {
        LoginManager.setUser(searchModel);
        Page<CustomerEntity> listPage = null;
        CustomerSearchRuleEntity searchRuleEntity = getCustomerSearchRuleEntity(searchModel);
        if (searchModel.getType() != null) {
            switch (searchModel.getType()) {
                case CustomerSearchModel.consumptionMainCount:
                    listPage = customerService.selectByTrade(searchModel,
                            searchRuleEntity.getConsumptionMainDay(),
                            searchRuleEntity.getConsumptionMainAmount(),
                            searchRuleEntity.getConsumptionMainNumber());
                    break;
                case CustomerSearchModel.membersWillCount:
                    listPage = customerService.selectByTrade(searchModel,
                            searchRuleEntity.getMembersWillDay(),
                            searchRuleEntity.getMembersWillAmount(),
                            searchRuleEntity.getMembersWillNumber());
                    break;
                case CustomerSearchModel.membersLossCount:
                    listPage = customerService.selectByTrade(searchModel,
                            searchRuleEntity.getMembersLossDay(),
                            searchRuleEntity.getMembersLossAmount(),
                            searchRuleEntity.getMembersLossNumber());
                    break;
                case CustomerSearchModel.membersNewIntervalCount:
                    listPage = customerService.selectByNewMember(searchModel, searchRuleEntity.getMembersNewIntervalDay());
                    break;
                case CustomerSearchModel.membersBirthdayCount:
                    listPage = customerService.selectByBirthday(searchModel, searchRuleEntity.getMembersBirthdayBeforeDay());
                    break;
                case CustomerSearchModel.membersAnniversaryCount:
                    listPage = customerService.selectByAnniversary(searchModel, searchRuleEntity.getMembersAnniversaryBeforeDay());
                    break;
            }
        }

        if (listPage == null) {
            listPage = customerService.findListPage(searchModel, searchModel.getPageSize());
        }

        setWebPage(model, "/customer/list", listPage, searchModel);
        model.addAttribute("searchModel", searchModel);
        model.addAttribute("list", listPage.getRecords());

        Integer consumptionMainCount = customerService.selectCountByTrade(searchRuleEntity.getShopIdenty(),
                searchRuleEntity.getConsumptionMainDay(),
                searchRuleEntity.getConsumptionMainAmount(),
                searchRuleEntity.getConsumptionMainNumber());

        Integer membersWillCount = customerService.selectCountByTrade(searchRuleEntity.getShopIdenty(),
                searchRuleEntity.getMembersWillDay(),
                searchRuleEntity.getMembersWillAmount(),
                searchRuleEntity.getMembersWillNumber());

        Integer membersLossCount = customerService.selectCountByTrade(searchRuleEntity.getShopIdenty(),
                searchRuleEntity.getMembersLossDay(),
                searchRuleEntity.getMembersLossAmount(),
                searchRuleEntity.getMembersLossNumber());

        Integer membersNewIntervalCount = customerService.selectCountByNewMember(searchRuleEntity.getShopIdenty(), searchRuleEntity.getMembersNewIntervalDay());
        Integer membersBirthdayCount = customerService.selectCountByBirthday(searchRuleEntity.getShopIdenty(), searchRuleEntity.getMembersBirthdayBeforeDay());
        Integer membersAnniversaryCount = customerService.selectCountByAnniversary(searchRuleEntity.getShopIdenty(), searchRuleEntity.getMembersAnniversaryBeforeDay());

        int memberCount = customerService.selectCountByShop(searchModel.getUser().getShopIdenty());
        model.addAttribute("membersCount", memberCount);
        model.addAttribute("consumptionMainCount", consumptionMainCount);
        model.addAttribute("consumptionMainCountPercent", consumptionMainCount * 100 / Math.max(memberCount, 1));
        model.addAttribute("membersWillCount", membersWillCount);
        model.addAttribute("membersWillCountPercent", membersWillCount * 100 / Math.max(memberCount, 1));
        model.addAttribute("membersLossCount", membersLossCount);
        model.addAttribute("membersLossCountPercent", membersLossCount * 100 / Math.max(memberCount, 1));
        model.addAttribute("membersNewIntervalCount", membersNewIntervalCount);
        model.addAttribute("membersNewIntervalCountPercent", membersNewIntervalCount * 100 / Math.max(memberCount, 1));
        model.addAttribute("membersBirthdayCount", membersBirthdayCount);
        model.addAttribute("membersAnniversaryCount", membersAnniversaryCount);

        return "customerlist";
    }

    private CustomerSearchRuleEntity getCustomerSearchRuleEntity(CustomerSearchModel searchModel) {
        CustomerSearchRuleEntity searchRuleEntity = customerSearchRuleService.selectByShopId(
                searchModel.getUser().getBrandIdenty(),
                searchModel.getUser().getShopIdenty());
        if (searchRuleEntity == null) {
            searchRuleEntity = new CustomerSearchRuleEntity();
            searchRuleEntity.setShopIdenty(searchModel.getUser().getShopIdenty());
            searchRuleEntity.setBrandIdenty(searchModel.getUser().getBrandIdenty());
            searchRuleEntity.setConsumptionMainDay(365);
            searchRuleEntity.setConsumptionMainAmount(5000);
            searchRuleEntity.setConsumptionMainNumber(12);
            searchRuleEntity.setMembersWillDay(365);
            searchRuleEntity.setMembersWillAmount(2000);
            searchRuleEntity.setMembersWillNumber(6);
            searchRuleEntity.setMembersLossDay(365);
            searchRuleEntity.setMembersLossAmount(1000);
            searchRuleEntity.setMembersLossNumber(3);
            searchRuleEntity.setMembersNewIntervalDay(0);
            searchRuleEntity.setMembersBirthdayBeforeDay(0);
            searchRuleEntity.setMembersAnniversaryBeforeDay(0);
        }
        return searchRuleEntity;
    }

    @RequestMapping(value = {"/{id}/edit", "/edit"})
    public String edit(Model model, @PathVariable(required = false) Long id, CustomerEditModel editModel) {
        LoginManager.setUser(editModel);
        if (id != null) {
            CustomerEntity coupon = customerService.selectById(id);
            editModel.setId(coupon.getId());
            editModel.setName(coupon.getName());
            editModel.setBirthday(formatDateString(coupon.getBirthday()));
            editModel.setGender(coupon.getGender());
            editModel.setGroupLevel(coupon.getGroupLevel());
            editModel.setMobile(coupon.getMobile());
            editModel.setTelephone(coupon.getTelephone());
            editModel.setAddress(coupon.getAddress());
            editModel.setHobby(coupon.getHobby());
            editModel.setProfile(coupon.getProfile());
            editModel.setPassword(coupon.getPassword());
            editModel.setCheckPassword(coupon.getPassword());
        }
        model.addAttribute("editModel", editModel);
        return "customer_modify";
    }

    @RequestMapping("/save")
    public String save(Model model,CustomerEditModel editModel) {
        AuthUserEntity loginUser = LoginManager.get().getUser();
        CustomerEntity customer = new CustomerEntity();
        customer.setId(editModel.getId());
        if (customer.getId() == null) {
            customer.baseCreate(loginUser.getCreatorId(), loginUser.getCreatorName());
            if (StringUtils.isNotBlank(editModel.getMobile())) {
                //判断在数据库里是否存在
                if (customerService.existsMobile(loginUser.getShopIdenty(), editModel.getMobile(), editModel.getId())) {
                    model.addAttribute("errorMsg", ApiResponseStatus.CUSTOMER_MOBILE_INVALID.getReason());
                    return edit(model, editModel.getId(), editModel);
                }
            }
        } else {
            if (StringUtils.isNotBlank(editModel.getMobile())) {
                //判断在数据库里是否存在
                if (customerService.existsMobile(loginUser.getShopIdenty(), editModel.getMobile(), editModel.getId())) {
                    model.addAttribute("errorMsg", ApiResponseStatus.CUSTOMER_MOBILE_INVALID.getReason());
                    return edit(model, editModel.getId(), editModel);
                }
            }
        }
        customer.setShopIdenty(loginUser.getShopIdenty());
        customer.setBrandIdenty(loginUser.getBrandIdenty());
        customer.baseUpdate(loginUser.getCreatorId(), loginUser.getCreatorName());
        customer.setName(editModel.getName());
        customer.setBirthday(stringToDate(editModel.getBirthday()));
        customer.setGender(editModel.getGender());
        customer.setGroupLevel(editModel.getGroupLevel());
        customer.setMobile(editModel.getMobile());
        customer.setTelephone(editModel.getTelephone());
        customer.setAddress(editModel.getAddress());
        customer.setHobby(editModel.getHobby());
        customer.setProfile(editModel.getProfile());
        String password = editModel.getPassword();
        if (StringUtils.isBlank(editModel.getCheckPassword()) || !editModel.getCheckPassword().equals(editModel.getPassword())) {
            password = Password.create().generate(editModel.getMobile(), password);
        }

        customer.setPassword(password);
        customerService.insertOrUpdate(customer);
        return redirect("/customer/list");
    }

    @RequestMapping(value = {"/{id}/del"})
    public String del(Model model, @PathVariable Long id, CustomerSearchModel searchModel) {
        CustomerEntity customerEntity = customerService.selectById(id);
        if (customerEntity != null) {
            customerEntity.setStatusFlag(StatusFlag.INVALID.value());
            customerService.updateById(customerEntity);
        }
        return list(model, searchModel);
    }

    @RequestMapping("/export/excel")
    public void exportExcel(HttpServletResponse response, CustomerSearchModel searchModel) throws Exception {
        LoginManager.setUser(searchModel);
        Page<CustomerEntity> listPage = null;
        CustomerSearchRuleEntity searchRuleEntity = getCustomerSearchRuleEntity(searchModel);
        if (searchModel.getType() != null) {
            switch (searchModel.getType()) {
                case CustomerSearchModel.consumptionMainCount:
                    listPage = customerService.selectByTrade(null,
                            searchModel,
                            searchRuleEntity.getConsumptionMainDay(),
                            searchRuleEntity.getConsumptionMainAmount(),
                            searchRuleEntity.getConsumptionMainNumber());
                    break;
                case CustomerSearchModel.membersWillCount:
                    listPage = customerService.selectByTrade(null,
                            searchModel,
                            searchRuleEntity.getMembersWillDay(),
                            searchRuleEntity.getMembersWillAmount(),
                            searchRuleEntity.getMembersWillNumber());
                    break;
                case CustomerSearchModel.membersLossCount:
                    listPage = customerService.selectByTrade(null,
                            searchModel,
                            searchRuleEntity.getMembersLossDay(),
                            searchRuleEntity.getMembersLossAmount(),
                            searchRuleEntity.getMembersLossNumber());
                    break;
                case CustomerSearchModel.membersNewIntervalCount:
                    listPage = customerService.selectByNewMember(null, searchModel, searchRuleEntity.getMembersNewIntervalDay());
                    break;
                case CustomerSearchModel.membersBirthdayCount:
                    listPage = customerService.selectByBirthday(null, searchModel, searchRuleEntity.getMembersBirthdayBeforeDay());
                    break;
                case CustomerSearchModel.membersAnniversaryCount:
                    listPage = customerService.selectByAnniversary(null, searchModel, searchRuleEntity.getMembersAnniversaryBeforeDay());
                    break;
            }
        }

        if (listPage == null) {
            listPage = customerService.findListPage(null, searchModel);
        }


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

