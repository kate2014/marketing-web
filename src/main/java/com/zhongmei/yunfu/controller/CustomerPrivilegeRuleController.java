package com.zhongmei.yunfu.controller;


import com.zhongmei.yunfu.controller.model.CommercailSettingModel;
import com.zhongmei.yunfu.controller.model.CustomerPrivilegeRuleModel;
import com.zhongmei.yunfu.domain.entity.CommercialCustomSettingsEntity;
import com.zhongmei.yunfu.domain.entity.CustomerPrivilegeRuleEntity;
import com.zhongmei.yunfu.service.CommercialCustomSettingsService;
import com.zhongmei.yunfu.service.CommercialPaySettingService;
import com.zhongmei.yunfu.service.CustomerPrivilegeRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
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
@RequestMapping("/internal/customerPrivilegeRule")
public class CustomerPrivilegeRuleController {

    @Autowired
    CustomerPrivilegeRuleService mCustomerPrivilegeRuleService;
    @Autowired
    CommercialCustomSettingsService mCommercialCustomSettingsService;

    @RequestMapping("/gotoSettingPage")
    public String gotoSettingPage(Model model, CustomerPrivilegeRuleModel ruleModel){
        try {

            ruleModel.setPrivilegeTypes("3,4");
            List<CustomerPrivilegeRuleEntity> listData = mCustomerPrivilegeRuleService.queryAllRule(ruleModel);

            model.addAttribute("listData", listData);

            CommercailSettingModel mCommercailSettingModel = new CommercailSettingModel();
            mCommercailSettingModel.setBrandIdenty(ruleModel.getBrandIdenty());
            mCommercailSettingModel.setShopIdenty(ruleModel.getShopIdenty());
            mCommercailSettingModel.setSettingKey("IS_NEED_SAVE_PAYMENT");
            CommercialCustomSettingsEntity mCommercialCustomSettingsEntity = mCommercialCustomSettingsService.queryByKey(mCommercailSettingModel);

            model.addAttribute("shopSetting", mCommercialCustomSettingsEntity);

            model.addAttribute("ruleModel", ruleModel);

            return "customer_privilege_setting";
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

    }

    /**
     * 设置储值优惠是否需要使用储值支付
     * @param model
     * @param ruleModel
     * @return
     */
    @RequestMapping("/modfityShopSetting")
    @ResponseBody
    public String modfityShopSetting(Model model, CustomerPrivilegeRuleModel ruleModel){
        try {
            CommercialCustomSettingsEntity mCommercialCustomSettingsEntity = new CommercialCustomSettingsEntity();
            mCommercialCustomSettingsEntity.setId(ruleModel.getSystemSettingId());
            mCommercialCustomSettingsEntity.setBrandIdenty(ruleModel.getBrandIdenty());
            mCommercialCustomSettingsEntity.setShopIdenty(ruleModel.getShopIdenty());
            mCommercialCustomSettingsEntity.setType(1l);
            mCommercialCustomSettingsEntity.setSettingKey("IS_NEED_SAVE_PAYMENT");
            mCommercialCustomSettingsEntity.setSettingValue(ruleModel.getIsNeedSavePayment().toString());
            mCommercialCustomSettingsEntity.setStatusFlag(1);
            mCommercialCustomSettingsEntity.setCreatorId(ruleModel.getCreatorId());
            mCommercialCustomSettingsEntity.setCreatorName(ruleModel.getCreatorName());
            mCommercialCustomSettingsEntity.setUpdatorId(ruleModel.getCreatorId());
            mCommercialCustomSettingsEntity.setUpdatorName(ruleModel.getCreatorName());
            boolean isSuccess = mCommercialCustomSettingsService.installOrUpdate(mCommercialCustomSettingsEntity);
            if(isSuccess){
                return "success";
            }else {
                return "fail";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }
    }

    @RequestMapping("/addSavePriviegeRule")
    public String savePrivilegeRule(Model model, CustomerPrivilegeRuleModel ruleModel){
        String actionSuccess = "success";
        try {
            CustomerPrivilegeRuleEntity entity = new CustomerPrivilegeRuleEntity();
            entity.setPrivilegeType(ruleModel.getPrivilegeType());
            entity.setFullAmount(ruleModel.getFullAmount());
            entity.setSaveAmount(ruleModel.getSaveAmount());
            entity.setPrivilegeValue(ruleModel.getPrivilegeValue());
            entity.setIsNeedSavePayment(ruleModel.getIsNeedSavePayment());
            entity.setBrandIdenty(ruleModel.getBrandIdenty());
            entity.setShopIdenty(ruleModel.getShopIdenty());
            entity.setCreatorId(ruleModel.getCreatorId());
            entity.setCreatorName(ruleModel.getCreatorName());
            entity.setUpdatorId(ruleModel.getCreatorId());
            entity.setUpdatorName(ruleModel.getCreatorName());
            entity.setStatusFlag(1);
            boolean isSuccess = mCustomerPrivilegeRuleService.addRule(entity);
            if(isSuccess){
                actionSuccess = "success";
            }else {
                actionSuccess = "fail";
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return String.format("redirect:/internal/customerPrivilegeRule/gotoSettingPage?brandIdenty=%d&shopIdenty=%d&creatorId=%d&creatorName=%s&successOrfail=%s",
                ruleModel.getBrandIdenty(), ruleModel.getShopIdenty(), ruleModel.getCreatorId(), ruleModel.getCreatorName(),actionSuccess);
    }

    @RequestMapping("/deletePrivilegeRule")
    public String deletePrivilegeRule(Model model, CustomerPrivilegeRuleModel ruleModel) {
        try {
            boolean isSuccess = mCustomerPrivilegeRuleService.deleteRuleById(ruleModel.getId());
            if (isSuccess) {
                return "success";
            } else {
                return "fail";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }
}

