package com.zhongmei.yunfu.controller;


import com.zhongmei.yunfu.controller.model.CustomerPrivilegeRuleModel;
import com.zhongmei.yunfu.domain.entity.CustomerPrivilegeRuleEntity;
import com.zhongmei.yunfu.service.CustomerPrivilageRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

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
@RequestMapping("/internal/customerPrivilageRule")
public class CustomerPrivilageRuleController {

    @Autowired
    CustomerPrivilageRuleService mCustomerPrivilageRuleService;

    @RequestMapping("/gotoSettingPage")
    public String gotoSettingPage(Model model, CustomerPrivilegeRuleModel ruleModel){
        try {

            List<CustomerPrivilegeRuleEntity> listData = mCustomerPrivilageRuleService.queryAllRule(ruleModel);

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
                            ruleModel.setPtPrivilageValue(entity.getPrivilageValue());
                        }
                        if(entity.getCustomerLevel() == 1) {
                            ruleModel.setYkRuleId(entity.getId());
                            ruleModel.setYkPrivilageValue(entity.getPrivilageValue());
                        }
                        if(entity.getCustomerLevel() == 2) {
                            ruleModel.setJkRuleId(entity.getId());
                            ruleModel.setJkPrivilageValue(entity.getPrivilageValue());
                        }
                        if(entity.getCustomerLevel() == 3) {
                            ruleModel.setBjRuleId(entity.getId());
                            ruleModel.setBjPrivilageValue(entity.getPrivilageValue());
                        }
                        if(entity.getCustomerLevel() == 4) {
                            ruleModel.setHjRuleId(entity.getId());
                            ruleModel.setHjPrivilageValue(entity.getPrivilageValue());
                        }
                        if(entity.getCustomerLevel() == 5) {
                            ruleModel.setZsRuleId(entity.getId());
                            ruleModel.setZsPrivilageValue(entity.getPrivilageValue());
                        }
                        if(entity.getCustomerLevel() == 6) {
                            ruleModel.setZzRuleId(entity.getId());
                            ruleModel.setZzPrivilageValue(entity.getPrivilageValue());
                        }
                    }

                    if(entity.getPrivilegeType() == 3 || entity.getPrivilegeType() == 4){
                        listSavePrivilege.add(entity);
                    }

                }
            }

            model.addAttribute("listData", listSavePrivilege);

            model.addAttribute("ruleModel", ruleModel);

            return "customer_privilage_setting";
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

    }

    @RequestMapping("/customerLevelPrivilage")
    public String addCustomerLevelPrivilage(Model model, CustomerPrivilegeRuleModel ruleModel){
        String actionSuccess = "success";

        try {
            List<CustomerPrivilegeRuleEntity> listAdd = new ArrayList<>();
            String deleteIds = "";

            //普通会员权益
            //判断是否是删除
            if(ruleModel.getPtRuleId() != null && !ruleModel.getPtRuleId().equals("")){
                if(ruleModel.getPtPrivilageValue() == null || ruleModel.getPtPrivilageValue().equals("")){
                    deleteIds = ruleModel.getPtRuleId()+"";
                }
            }
            //执行添加或删除
            if(ruleModel.getPtPrivilageValue() != null && !ruleModel.getPtPrivilageValue().equals("")){
                CustomerPrivilegeRuleEntity ptEntity = new CustomerPrivilegeRuleEntity();
                ptEntity.setId(ruleModel.getPtRuleId());
                ptEntity.setPrivilegeType(ruleModel.getPrivilegeType());
                ptEntity.setCustomerLevel(0);
                ptEntity.setFullAmount(ruleModel.getFullAmount());
                ptEntity.setPrivilageValue(ruleModel.getPtPrivilageValue());
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
                if(ruleModel.getYkPrivilageValue() == null || ruleModel.getYkPrivilageValue().equals("")){
                    if(deleteIds.equals("")){
                        deleteIds = ruleModel.getYkRuleId()+"";
                    }else {
                        deleteIds = ","+ruleModel.getYkRuleId();
                    }

                }
            }
            if(ruleModel.getYkPrivilageValue() != null && !ruleModel.getYkPrivilageValue().equals("")){
                CustomerPrivilegeRuleEntity ykEntity = new CustomerPrivilegeRuleEntity();
                ykEntity.setId(ruleModel.getYkRuleId());
                ykEntity.setPrivilegeType(ruleModel.getPrivilegeType());
                ykEntity.setCustomerLevel(1);
                ykEntity.setFullAmount(ruleModel.getFullAmount());
                ykEntity.setPrivilageValue(ruleModel.getYkPrivilageValue());
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
                if(ruleModel.getJkPrivilageValue() == null || ruleModel.getJkPrivilageValue().equals("")){
                    if(deleteIds.equals("")){
                        deleteIds = ruleModel.getJkRuleId()+"";
                    }else {
                        deleteIds = ","+ruleModel.getJkRuleId();
                    }

                }
            }
            if(ruleModel.getJkPrivilageValue() != null && !ruleModel.getJkPrivilageValue().equals("")){
                CustomerPrivilegeRuleEntity jkEntity = new CustomerPrivilegeRuleEntity();
                jkEntity.setId(ruleModel.getJkRuleId());
                jkEntity.setPrivilegeType(ruleModel.getPrivilegeType());
                jkEntity.setCustomerLevel(2);
                jkEntity.setFullAmount(ruleModel.getFullAmount());
                jkEntity.setPrivilageValue(ruleModel.getJkPrivilageValue());
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
                if(ruleModel.getBjPrivilageValue() == null || ruleModel.getBjPrivilageValue().equals("")){
                    if(deleteIds.equals("")){
                        deleteIds = ruleModel.getBjRuleId()+"";
                    }else {
                        deleteIds = ","+ruleModel.getBjRuleId();
                    }

                }
            }
            if(ruleModel.getBjPrivilageValue() != null && !ruleModel.getBjPrivilageValue().equals("")){
                CustomerPrivilegeRuleEntity bjEntity = new CustomerPrivilegeRuleEntity();
                bjEntity.setId(ruleModel.getBjRuleId());
                bjEntity.setPrivilegeType(ruleModel.getPrivilegeType());
                bjEntity.setCustomerLevel(3);
                bjEntity.setFullAmount(ruleModel.getFullAmount());
                bjEntity.setPrivilageValue(ruleModel.getBjPrivilageValue());
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
                if(ruleModel.getHjPrivilageValue() == null || ruleModel.getHjPrivilageValue().equals("")){
                    if(deleteIds.equals("")){
                        deleteIds = ruleModel.getHjRuleId()+"";
                    }else {
                        deleteIds = ","+ruleModel.getHjRuleId();
                    }

                }
            }
            if(ruleModel.getHjPrivilageValue() != null && !ruleModel.getHjPrivilageValue().equals("")){
                CustomerPrivilegeRuleEntity hjEntity = new CustomerPrivilegeRuleEntity();
                hjEntity.setId(ruleModel.getHjRuleId());
                hjEntity.setPrivilegeType(ruleModel.getPrivilegeType());
                hjEntity.setCustomerLevel(4);
                hjEntity.setFullAmount(ruleModel.getFullAmount());
                hjEntity.setPrivilageValue(ruleModel.getHjPrivilageValue());
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
                if(ruleModel.getZsPrivilageValue() == null || ruleModel.getZsPrivilageValue().equals("")){
                    if(deleteIds.equals("")){
                        deleteIds = ruleModel.getZsRuleId()+"";
                    }else {
                        deleteIds = ","+ruleModel.getZsRuleId();
                    }

                }
            }
            if(ruleModel.getZsPrivilageValue() != null && !ruleModel.getZsPrivilageValue().equals("")){
                CustomerPrivilegeRuleEntity zsEntity = new CustomerPrivilegeRuleEntity();
                zsEntity.setId(ruleModel.getZsRuleId());
                zsEntity.setPrivilegeType(ruleModel.getPrivilegeType());
                zsEntity.setCustomerLevel(5);
                zsEntity.setFullAmount(ruleModel.getFullAmount());
                zsEntity.setPrivilageValue(ruleModel.getZsPrivilageValue());
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
                if(ruleModel.getZzPrivilageValue() == null || ruleModel.getZzPrivilageValue().equals("")){
                    if(deleteIds.equals("")){
                        deleteIds = ruleModel.getZzRuleId()+"";
                    }else {
                        deleteIds = ","+ruleModel.getZzRuleId();
                    }

                }
            }
            if(ruleModel.getZzPrivilageValue() != null && !ruleModel.getZzPrivilageValue().equals("")){
                CustomerPrivilegeRuleEntity zzEntity = new CustomerPrivilegeRuleEntity();
                zzEntity.setId(ruleModel.getZzRuleId());
                zzEntity.setPrivilegeType(ruleModel.getPrivilegeType());
                zzEntity.setCustomerLevel(6);
                zzEntity.setFullAmount(ruleModel.getFullAmount());
                zzEntity.setPrivilageValue(ruleModel.getZzPrivilageValue());
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


            boolean isSuccess = mCustomerPrivilageRuleService.installOrUpdate(listAdd);
            if(!deleteIds.equals("")){
                mCustomerPrivilageRuleService.batchDelete(ruleModel.getBrandIdenty(),ruleModel.getShopIdenty(),deleteIds);
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

        return String.format("redirect:/internal/customerPrivilageRule/gotoSettingPage?brandIdenty=%d&shopIdenty=%d&creatorId=%d&creatorName=%s&successOrfail=%s",
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
            entity.setPrivilageValue(ruleModel.getPrivilageValue());
            entity.setIsNeedSavePayment(ruleModel.getIsNeedSavePayment());
            entity.setBrandIdenty(ruleModel.getBrandIdenty());
            entity.setShopIdenty(ruleModel.getShopIdenty());
            entity.setCreatorId(ruleModel.getCreatorId());
            entity.setCreatorName(ruleModel.getCreatorName());
            entity.setUpdatorId(ruleModel.getCreatorId());
            entity.setUpdatorName(ruleModel.getCreatorName());

            boolean isSuccess = mCustomerPrivilageRuleService.addRule(entity);
            if(isSuccess){
                actionSuccess = "success";
            }else {
                actionSuccess = "fail";
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return String.format("redirect:/internal/customerPrivilageRule/gotoSettingPage?brandIdenty=%d&shopIdenty=%d&creatorId=%d&creatorName=%s&successOrfail=%s",
                ruleModel.getBrandIdenty(), ruleModel.getShopIdenty(), ruleModel.getCreatorId(), ruleModel.getCreatorName(),actionSuccess);
    }

    @RequestMapping("/deletePrivilegeRule")
    public String deletePrivilegeRule(Model model, CustomerPrivilegeRuleModel ruleModel) {
        try {
            boolean isSuccess = mCustomerPrivilageRuleService.deleteRuleById(ruleModel.getId());
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

