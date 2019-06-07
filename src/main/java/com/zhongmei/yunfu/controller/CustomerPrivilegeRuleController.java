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

            List<CustomerPrivilegeRuleEntity> listData = mCustomerPrivilegeRuleService.queryAllRule(ruleModel);

            List<CustomerPrivilegeRuleEntity> listSavePrivilege = new ArrayList<>();

            if(listData != null && listData.size() != 0){

                for(CustomerPrivilegeRuleEntity entity : listData){
                    if(entity.getPrivilegeType() == 1 || entity.getPrivilegeType() == 2){
                        if(ruleModel.getPrivilegeType() != null){
                            ruleModel.setPrivilegeType(entity.getPrivilegeType());
                        }

                        if(entity.getFullAmount()!= null){
                            ruleModel.setFullAmount(entity.getFullAmount());
                        }

                        if(entity.getCustomerLevel() == 0){
                            ruleModel.setPtRuleId(entity.getId());
                            ruleModel.setPtPrivilegeValue(entity.getPrivilegeValue());
                        }
                        if(entity.getCustomerLevel() == 1) {
                            ruleModel.setYkRuleId(entity.getId());
                            ruleModel.setYkPrivilegeValue(entity.getPrivilegeValue());
                        }
                        if(entity.getCustomerLevel() == 2) {
                            ruleModel.setJkRuleId(entity.getId());
                            ruleModel.setJkPrivilegeValue(entity.getPrivilegeValue());
                        }
                        if(entity.getCustomerLevel() == 3) {
                            ruleModel.setBjRuleId(entity.getId());
                            ruleModel.setBjPrivilegeValue(entity.getPrivilegeValue());
                        }
                        if(entity.getCustomerLevel() == 4) {
                            ruleModel.setHjRuleId(entity.getId());
                            ruleModel.setHjPrivilegeValue(entity.getPrivilegeValue());
                        }
                        if(entity.getCustomerLevel() == 5) {
                            ruleModel.setZsRuleId(entity.getId());
                            ruleModel.setZsPrivilegeValue(entity.getPrivilegeValue());
                        }
                        if(entity.getCustomerLevel() == 6) {
                            ruleModel.setZzRuleId(entity.getId());
                            ruleModel.setZzPrivilegeValue(entity.getPrivilegeValue());
                        }
                    }

                    if(entity.getPrivilegeType() == 3 || entity.getPrivilegeType() == 4){
                        listSavePrivilege.add(entity);
                    }

                }
            }

            model.addAttribute("listData", listSavePrivilege);

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

    @RequestMapping("/modfityShopSetting")
    @ResponseBody
    public String modfityShopSetting(Model model, CustomerPrivilegeRuleModel ruleModel){
        try {
            CommercialCustomSettingsEntity mCommercialCustomSettingsEntity = new CommercialCustomSettingsEntity();
            mCommercialCustomSettingsService.installSetting(mCommercialCustomSettingsEntity);
        }catch (Exception e){
            e.printStackTrace();
        }

        return "success";
    }

    @RequestMapping("/customerLevelPrivilege")
    public String addCustomerLevelPrivilege(Model model, CustomerPrivilegeRuleModel ruleModel){
        String actionSuccess = "success";

        try {
            List<CustomerPrivilegeRuleEntity> listAdd = new ArrayList<>();
            String deleteIds = "";

            //普通会员权益
            //判断是否是删除
            if(ruleModel.getPtRuleId() != null && !ruleModel.getPtRuleId().equals("")){
                if(ruleModel.getPtPrivilegeValue() == null || ruleModel.getPtPrivilegeValue().equals("")){
                    deleteIds = ruleModel.getPtRuleId()+"";
                }
            }
            //执行添加或删除
            if(ruleModel.getPtPrivilegeValue() != null && !ruleModel.getPtPrivilegeValue().equals("")){
                CustomerPrivilegeRuleEntity ptEntity = new CustomerPrivilegeRuleEntity();
                ptEntity.setId(ruleModel.getPtRuleId());
                ptEntity.setPrivilegeType(ruleModel.getPrivilegeType());
                ptEntity.setCustomerLevel(0);
                ptEntity.setFullAmount(ruleModel.getFullAmount());
                ptEntity.setPrivilegeValue(ruleModel.getPtPrivilegeValue());
                ptEntity.setIsNeedSavePayment(0);
                ptEntity.setBrandIdenty(ruleModel.getBrandIdenty());
                ptEntity.setShopIdenty(ruleModel.getShopIdenty());
                if(ruleModel.getPtRuleId() == null || ruleModel.getPtRuleId().equals("")){
                    ptEntity.setCreatorId(ruleModel.getCreatorId());
                    ptEntity.setCreatorName(ruleModel.getCreatorName());
                    ptEntity.setUpdatorId(ruleModel.getCreatorId());
                    ptEntity.setUpdatorName(ruleModel.getCreatorName());
                }else{
                    ptEntity.setUpdatorId(ruleModel.getCreatorId());
                    ptEntity.setUpdatorName(ruleModel.getCreatorName());
                    ptEntity.setServerUpdateTime(new Date());
                }
                listAdd.add(ptEntity);
            }


            //银卡会员权益
            if(ruleModel.getYkRuleId() != null && !ruleModel.getYkRuleId().equals("")){
                if(ruleModel.getYkPrivilegeValue() == null || ruleModel.getYkPrivilegeValue().equals("")){
                    if(deleteIds.equals("")){
                        deleteIds = ruleModel.getYkRuleId()+"";
                    }else {
                        deleteIds = ","+ruleModel.getYkRuleId();
                    }

                }
            }
            if(ruleModel.getYkPrivilegeValue() != null && !ruleModel.getYkPrivilegeValue().equals("")){
                CustomerPrivilegeRuleEntity ykEntity = new CustomerPrivilegeRuleEntity();
                ykEntity.setId(ruleModel.getYkRuleId());
                ykEntity.setPrivilegeType(ruleModel.getPrivilegeType());
                ykEntity.setCustomerLevel(1);
                ykEntity.setFullAmount(ruleModel.getFullAmount());
                ykEntity.setPrivilegeValue(ruleModel.getYkPrivilegeValue());
                ykEntity.setIsNeedSavePayment(0);
                ykEntity.setBrandIdenty(ruleModel.getBrandIdenty());
                ykEntity.setShopIdenty(ruleModel.getShopIdenty());
                if(ruleModel.getYkRuleId() == null || ruleModel.getYkRuleId().equals("")){
                    ykEntity.setCreatorId(ruleModel.getCreatorId());
                    ykEntity.setCreatorName(ruleModel.getCreatorName());
                    ykEntity.setUpdatorId(ruleModel.getCreatorId());
                    ykEntity.setUpdatorName(ruleModel.getCreatorName());
                }else{
                    ykEntity.setUpdatorId(ruleModel.getCreatorId());
                    ykEntity.setUpdatorName(ruleModel.getCreatorName());
                    ykEntity.setServerUpdateTime(new Date());

                }
                listAdd.add(ykEntity);
            }



            //金卡会员权益
            if(ruleModel.getJkRuleId() != null && !ruleModel.getJkRuleId().equals("")){
                if(ruleModel.getJkPrivilegeValue() == null || ruleModel.getJkPrivilegeValue().equals("")){
                    if(deleteIds.equals("")){
                        deleteIds = ruleModel.getJkRuleId()+"";
                    }else {
                        deleteIds = ","+ruleModel.getJkRuleId();
                    }

                }
            }
            if(ruleModel.getJkPrivilegeValue() != null && !ruleModel.getJkPrivilegeValue().equals("")){
                CustomerPrivilegeRuleEntity jkEntity = new CustomerPrivilegeRuleEntity();
                jkEntity.setId(ruleModel.getJkRuleId());
                jkEntity.setPrivilegeType(ruleModel.getPrivilegeType());
                jkEntity.setCustomerLevel(2);
                jkEntity.setFullAmount(ruleModel.getFullAmount());
                jkEntity.setPrivilegeValue(ruleModel.getJkPrivilegeValue());
                jkEntity.setIsNeedSavePayment(0);
                jkEntity.setBrandIdenty(ruleModel.getBrandIdenty());
                jkEntity.setShopIdenty(ruleModel.getShopIdenty());
                if(ruleModel.getJkRuleId() == null || ruleModel.getJkRuleId().equals("")){
                    jkEntity.setCreatorId(ruleModel.getCreatorId());
                    jkEntity.setCreatorName(ruleModel.getCreatorName());
                    jkEntity.setUpdatorId(ruleModel.getCreatorId());
                    jkEntity.setUpdatorName(ruleModel.getCreatorName());
                }else{
                    jkEntity.setUpdatorId(ruleModel.getCreatorId());
                    jkEntity.setUpdatorName(ruleModel.getCreatorName());
                    jkEntity.setServerUpdateTime(new Date());
                }
                listAdd.add(jkEntity);
            }


            //白金会员权益
            if(ruleModel.getBjRuleId() != null && !ruleModel.getBjRuleId().equals("")){
                if(ruleModel.getBjPrivilegeValue() == null || ruleModel.getBjPrivilegeValue().equals("")){
                    if(deleteIds.equals("")){
                        deleteIds = ruleModel.getBjRuleId()+"";
                    }else {
                        deleteIds = ","+ruleModel.getBjRuleId();
                    }

                }
            }
            if(ruleModel.getBjPrivilegeValue() != null && !ruleModel.getBjPrivilegeValue().equals("")){
                CustomerPrivilegeRuleEntity bjEntity = new CustomerPrivilegeRuleEntity();
                bjEntity.setId(ruleModel.getBjRuleId());
                bjEntity.setPrivilegeType(ruleModel.getPrivilegeType());
                bjEntity.setCustomerLevel(3);
                bjEntity.setFullAmount(ruleModel.getFullAmount());
                bjEntity.setPrivilegeValue(ruleModel.getBjPrivilegeValue());
                bjEntity.setIsNeedSavePayment(0);
                bjEntity.setBrandIdenty(ruleModel.getBrandIdenty());
                bjEntity.setShopIdenty(ruleModel.getShopIdenty());
                if(ruleModel.getBjRuleId() == null || ruleModel.getBjRuleId().equals("")){
                    bjEntity.setCreatorId(ruleModel.getCreatorId());
                    bjEntity.setCreatorName(ruleModel.getCreatorName());
                    bjEntity.setUpdatorId(ruleModel.getCreatorId());
                    bjEntity.setUpdatorName(ruleModel.getCreatorName());
                }else{
                    bjEntity.setUpdatorId(ruleModel.getCreatorId());
                    bjEntity.setUpdatorName(ruleModel.getCreatorName());
                    bjEntity.setServerUpdateTime(new Date());
                }
                listAdd.add(bjEntity);
            }


            //黑金会员权益
            if(ruleModel.getHjRuleId() != null && !ruleModel.getHjRuleId().equals("")){
                if(ruleModel.getHjPrivilegeValue() == null || ruleModel.getHjPrivilegeValue().equals("")){
                    if(deleteIds.equals("")){
                        deleteIds = ruleModel.getHjRuleId()+"";
                    }else {
                        deleteIds = ","+ruleModel.getHjRuleId();
                    }

                }
            }
            if(ruleModel.getHjPrivilegeValue() != null && !ruleModel.getHjPrivilegeValue().equals("")){
                CustomerPrivilegeRuleEntity hjEntity = new CustomerPrivilegeRuleEntity();
                hjEntity.setId(ruleModel.getHjRuleId());
                hjEntity.setPrivilegeType(ruleModel.getPrivilegeType());
                hjEntity.setCustomerLevel(4);
                hjEntity.setFullAmount(ruleModel.getFullAmount());
                hjEntity.setPrivilegeValue(ruleModel.getHjPrivilegeValue());
                hjEntity.setIsNeedSavePayment(0);
                hjEntity.setBrandIdenty(ruleModel.getBrandIdenty());
                hjEntity.setShopIdenty(ruleModel.getShopIdenty());
                if(ruleModel.getHjRuleId() == null || ruleModel.getHjRuleId().equals("")){
                    hjEntity.setCreatorId(ruleModel.getCreatorId());
                    hjEntity.setCreatorName(ruleModel.getCreatorName());
                    hjEntity.setUpdatorId(ruleModel.getCreatorId());
                    hjEntity.setUpdatorName(ruleModel.getCreatorName());
                }else{
                    hjEntity.setUpdatorId(ruleModel.getCreatorId());
                    hjEntity.setUpdatorName(ruleModel.getCreatorName());
                    hjEntity.setServerUpdateTime(new Date());
                }
                listAdd.add(hjEntity);
            }


            //钻石会员权益
            if(ruleModel.getZsRuleId() != null && !ruleModel.getZsRuleId().equals("")){
                if(ruleModel.getZsPrivilegeValue() == null || ruleModel.getZsPrivilegeValue().equals("")){
                    if(deleteIds.equals("")){
                        deleteIds = ruleModel.getZsRuleId()+"";
                    }else {
                        deleteIds = ","+ruleModel.getZsRuleId();
                    }

                }
            }
            if(ruleModel.getZsPrivilegeValue() != null && !ruleModel.getZsPrivilegeValue().equals("")){
                CustomerPrivilegeRuleEntity zsEntity = new CustomerPrivilegeRuleEntity();
                zsEntity.setId(ruleModel.getZsRuleId());
                zsEntity.setPrivilegeType(ruleModel.getPrivilegeType());
                zsEntity.setCustomerLevel(5);
                zsEntity.setFullAmount(ruleModel.getFullAmount());
                zsEntity.setPrivilegeValue(ruleModel.getZsPrivilegeValue());
                zsEntity.setIsNeedSavePayment(0);
                zsEntity.setBrandIdenty(ruleModel.getBrandIdenty());
                zsEntity.setShopIdenty(ruleModel.getShopIdenty());
                if(ruleModel.getZsRuleId() == null || ruleModel.getZsRuleId().equals("")){
                    zsEntity.setCreatorId(ruleModel.getCreatorId());
                    zsEntity.setCreatorName(ruleModel.getCreatorName());
                    zsEntity.setUpdatorId(ruleModel.getCreatorId());
                    zsEntity.setUpdatorName(ruleModel.getCreatorName());
                }else{
                    zsEntity.setUpdatorId(ruleModel.getCreatorId());
                    zsEntity.setUpdatorName(ruleModel.getCreatorName());
                    zsEntity.setServerUpdateTime(new Date());
                }
                listAdd.add(zsEntity);
            }


            //至尊会员权益
            if(ruleModel.getZzRuleId() != null && !ruleModel.getZzRuleId().equals("")){
                if(ruleModel.getZzPrivilegeValue() == null || ruleModel.getZzPrivilegeValue().equals("")){
                    if(deleteIds.equals("")){
                        deleteIds = ruleModel.getZzRuleId()+"";
                    }else {
                        deleteIds = ","+ruleModel.getZzRuleId();
                    }

                }
            }
            if(ruleModel.getZzPrivilegeValue() != null && !ruleModel.getZzPrivilegeValue().equals("")){
                CustomerPrivilegeRuleEntity zzEntity = new CustomerPrivilegeRuleEntity();
                zzEntity.setId(ruleModel.getZzRuleId());
                zzEntity.setPrivilegeType(ruleModel.getPrivilegeType());
                zzEntity.setCustomerLevel(6);
                zzEntity.setFullAmount(ruleModel.getFullAmount());
                zzEntity.setPrivilegeValue(ruleModel.getZzPrivilegeValue());
                zzEntity.setIsNeedSavePayment(0);
                zzEntity.setBrandIdenty(ruleModel.getBrandIdenty());
                zzEntity.setShopIdenty(ruleModel.getShopIdenty());
                if(ruleModel.getZzRuleId() == null || ruleModel.getZzRuleId().equals("")){
                    zzEntity.setCreatorId(ruleModel.getCreatorId());
                    zzEntity.setCreatorName(ruleModel.getCreatorName());
                    zzEntity.setUpdatorId(ruleModel.getCreatorId());
                    zzEntity.setUpdatorName(ruleModel.getCreatorName());
                }else{
                    zzEntity.setUpdatorId(ruleModel.getCreatorId());
                    zzEntity.setUpdatorName(ruleModel.getCreatorName());
                    zzEntity.setServerUpdateTime(new Date());
                }
                listAdd.add(zzEntity);
            }


            boolean isSuccess = mCustomerPrivilegeRuleService.installOrUpdate(listAdd);
            if(!deleteIds.equals("")){
                mCustomerPrivilegeRuleService.batchDelete(ruleModel.getBrandIdenty(),ruleModel.getShopIdenty(),deleteIds);
            }
            if(isSuccess){
                actionSuccess = "success";
            }else {
                actionSuccess = "fail";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

        return String.format("redirect:/internal/customerPrivilegeRule/gotoSettingPage?brandIdenty=%d&shopIdenty=%d&creatorId=%d&creatorName=%s&successOrfail=%s",
                ruleModel.getBrandIdenty(), ruleModel.getShopIdenty(), ruleModel.getCreatorId(), ruleModel.getCreatorName(),actionSuccess);
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

