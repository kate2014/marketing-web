package com.zhongmei.yunfu.controller;


import com.zhongmei.yunfu.controller.model.CustomerLevelRuleModel;
import com.zhongmei.yunfu.controller.model.CustomerScoreRuleModel;
import com.zhongmei.yunfu.domain.entity.CustomerScoreRuleEntity;
import com.zhongmei.yunfu.service.CustomerScoreRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 会员等级积分成长规则、积分使用规则 前端控制器
 * </p>
 *
 * @author yangyp
 * @since 2018-09-07
 */
@Controller
@RequestMapping("/internal/customerScoreRule")
public class CustomerScoreRuleController {

    @Autowired
    CustomerScoreRuleService mCustomerScoreRuleService;

    @RequestMapping("/customerScoreSetting")
    public String customerScoreSetting(Model model, CustomerLevelRuleModel mCustomerLevelRuleModel){


        Map<String, Object> map = new HashMap<>();
        map.put("brandIdenty", mCustomerLevelRuleModel.getBrandIdenty());
        map.put("shopIdenty", mCustomerLevelRuleModel.getShopIdenty());
        List<CustomerScoreRuleEntity> listSR = mCustomerScoreRuleService.findScoreRule(map);
        CustomerScoreRuleModel cusRM = new CustomerScoreRuleModel();
        for (CustomerScoreRuleEntity cs : listSR) {
            if (cs.getType() == 1) {
                cusRM.setIdS(cs.getId());
                cusRM.setConvertValueS(cs.getConvertValue());
            } else if (cs.getType() == 2) {
                cusRM.setIdD(cs.getId());
                cusRM.setConvertValueD(cs.getConvertValue());
            } else if (cs.getType() == 3) {
                cusRM.setIdM(cs.getId());
                cusRM.setConvertValueM(cs.getConvertValue());
            }
        }

        model.addAttribute("cusRM", cusRM);
        model.addAttribute("customerScore", mCustomerLevelRuleModel);
        return "customer_score_setting";
    }

    @RequestMapping("/modify")
    public String modify(Model model, CustomerScoreRuleModel mCustomerScoreRuleModel) {
        Boolean isSuccess = true;
        try {

            isSuccess = mCustomerScoreRuleService.modifyScoreRule(mCustomerScoreRuleModel);

        } catch (Exception e) {
            e.printStackTrace();
            isSuccess = false;
        }

        String actionSuccess = "";
        if (isSuccess) {
            actionSuccess = "success";
        } else {
            actionSuccess = "fail";
        }
        return String.format("redirect:/internal/customerScoreRule/customerScoreSetting?brandIdenty=%d&shopIdenty=%d&creatorId=%d&creatorName=%s&successOrfail=%s",
                mCustomerScoreRuleModel.getBrandIdenty(), mCustomerScoreRuleModel.getShopIdenty(), mCustomerScoreRuleModel.getCreatorId(), mCustomerScoreRuleModel.getCreatorName(),actionSuccess);
    }

}

