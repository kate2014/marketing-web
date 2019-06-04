package com.zhongmei.yunfu.controller;


import com.zhongmei.yunfu.controller.model.CustomerSaveRuleModel;
import com.zhongmei.yunfu.domain.entity.CustomerSaveRuleEntity;
import com.zhongmei.yunfu.service.CustomerSaveRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * 会员储值规则表 前端控制器
 * </p>
 *
 * @author pigeon88
 * @since 2019-06-04
 */
@Controller
@RequestMapping("/internal/customerSaveRule")
public class CustomerSaveRuleController {

    @Autowired
    CustomerSaveRuleService mCustomerSaveRuleService;

    @RequestMapping("/gotoSettingPage")
    public String gotoSettingPage(Model model, CustomerSaveRuleModel mCustomerSaveRuleModel){
        try {
            CustomerSaveRuleEntity mCustomerSaveRuleEntity = new CustomerSaveRuleEntity();
            mCustomerSaveRuleEntity.setBrandIdenty(mCustomerSaveRuleModel.getBrandIdenty());
            mCustomerSaveRuleEntity.setShopIdenty(mCustomerSaveRuleModel.getShopIdenty());
            List<CustomerSaveRuleEntity> listData = mCustomerSaveRuleService.queryAllRule(mCustomerSaveRuleEntity);

            model.addAttribute("listData", listData);

            model.addAttribute("mCustomerSaveRuleModel", mCustomerSaveRuleModel);

        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

        return "customer_save_rule_setting";
    }

    @RequestMapping("/addSaveRule")
    public String addSaveRule(Model model, CustomerSaveRuleModel mCustomerSaveRuleModel){

        String actionSuccess = "success";

        try {

            CustomerSaveRuleEntity mCustomerSaveRuleEntity = new CustomerSaveRuleEntity();
            mCustomerSaveRuleEntity.setStoredValue(mCustomerSaveRuleModel.getStoredValue());
            mCustomerSaveRuleEntity.setGiveValue(mCustomerSaveRuleModel.getGiveValue());
            mCustomerSaveRuleEntity.setBrandIdenty(mCustomerSaveRuleModel.getBrandIdenty());
            mCustomerSaveRuleEntity.setShopIdenty(mCustomerSaveRuleModel.getShopIdenty());
            mCustomerSaveRuleEntity.setCreatorId(mCustomerSaveRuleModel.getCreatorId());
            mCustomerSaveRuleEntity.setCreatorName(mCustomerSaveRuleModel.getCreatorName());
            mCustomerSaveRuleEntity.setUpdatorId(mCustomerSaveRuleModel.getCreatorId());
            mCustomerSaveRuleEntity.setUpdatorName(mCustomerSaveRuleModel.getCreatorName());

            boolean isSuccess = mCustomerSaveRuleService.addSaveRule(mCustomerSaveRuleEntity);
            if(isSuccess){
                actionSuccess = "success";
            }else{
                actionSuccess = "fail";
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return String.format("redirect:/internal/customerSaveRule/gotoSettingPage?brandIdenty=%d&shopIdenty=%d&creatorId=%d&creatorName=%s&successOrfail=%s",
                mCustomerSaveRuleModel.getBrandIdenty(), mCustomerSaveRuleModel.getShopIdenty(), mCustomerSaveRuleModel.getCreatorId(), mCustomerSaveRuleModel.getCreatorName(),actionSuccess);
    }

    @RequestMapping("/deleteSaveRule")
    public String deleteSaveRule(Model model, CustomerSaveRuleModel mCustomerSaveRuleModel){

        String actionSuccess = "success";

        try {

            boolean isSuccess = mCustomerSaveRuleService.deleteRuleById(mCustomerSaveRuleModel.getId());
            if(isSuccess){
                return "success";
            }else{
                return "fail";
            }

        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

    }

}

