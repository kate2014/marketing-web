package com.zhongmei.yunfu.api.web;


import com.zhongmei.yunfu.controller.model.CustomerScoreRuleModel;
import com.zhongmei.yunfu.service.CustomerScoreRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 * 会员等级积分成长规则、积分使用规则 前端控制器
 * </p>
 *
 * @author yangyp
 * @since 2018-09-07
 */
@Controller
@RequestMapping("/internal/customerPrivilage")
public class CustomerPrivilageSettingController {

    @Autowired
    CustomerScoreRuleService mCustomerScoreRuleService;

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
        return String.format("redirect:/internal/customerSetting/customerScore?brandIdenty=%d&shopIdenty=%d&creatorId=%d&creatorName=%s&successOrfail=%s",
                mCustomerScoreRuleModel.getBrandIdenty(), mCustomerScoreRuleModel.getShopIdenty(), mCustomerScoreRuleModel.getCreatorId(), mCustomerScoreRuleModel.getCreatorName(),actionSuccess);
    }

}

