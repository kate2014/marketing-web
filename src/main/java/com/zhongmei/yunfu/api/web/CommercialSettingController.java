package com.zhongmei.yunfu.api.web;

import com.zhongmei.yunfu.controller.model.CommercailSettingModel;
import com.zhongmei.yunfu.domain.entity.CommercialCustomSettingsEntity;
import com.zhongmei.yunfu.service.CommercialCustomSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

/**
 * 设置信息
 */
@Controller
@RequestMapping("/internal/commercailSetting")
public class CommercialSettingController {

    @Autowired
    CommercialCustomSettingsService mCommercialCustomSettingsService;

    /**
     * 会员支付密码验证设置
     * @param model
     * @param mCommercailSettingModel
     * @return
     */
    @RequestMapping("/customer/pay/checkpassword")
    public String payCheckpassword(Model model, CommercailSettingModel mCommercailSettingModel) {
        Boolean isSuccess = true;
        try {
            CommercialCustomSettingsEntity mCommercialCustomSettingsEntity = new CommercialCustomSettingsEntity();

            if(mCommercailSettingModel.getId() == null){
                mCommercialCustomSettingsEntity.setBrandIdenty(mCommercailSettingModel.getBrandIdenty());
                mCommercialCustomSettingsEntity.setShopIdenty(mCommercailSettingModel.getShopIdenty());
                mCommercialCustomSettingsEntity.setType(1l);
                mCommercialCustomSettingsEntity.setSettingKey("IS_CKECK_PASSWORD_DOPAY");
                mCommercialCustomSettingsEntity.setSettingValue(mCommercailSettingModel.getSettingValue());
                mCommercialCustomSettingsEntity.setStatusFlag(1);
                mCommercialCustomSettingsEntity.setCreatorId(mCommercailSettingModel.getCreatorId());
                mCommercialCustomSettingsEntity.setCreatorName(mCommercailSettingModel.getCreatorName());
                mCommercialCustomSettingsEntity.setUpdatorId(mCommercailSettingModel.getCreatorId());
                mCommercialCustomSettingsEntity.setUpdatorName(mCommercailSettingModel.getCreatorName());
                mCommercialCustomSettingsEntity.setServerCreateTime(new Date());
                mCommercialCustomSettingsEntity.setServerUpdateTime(new Date());

                isSuccess = mCommercialCustomSettingsService.installSetting(mCommercialCustomSettingsEntity);
            }else{
                mCommercialCustomSettingsEntity.setId(mCommercailSettingModel.getId());
                mCommercialCustomSettingsEntity.setSettingValue(mCommercailSettingModel.getSettingValue());
                mCommercialCustomSettingsEntity.setServerUpdateTime(new Date());
                mCommercialCustomSettingsEntity.setUpdatorId(mCommercailSettingModel.getCreatorId());
                mCommercialCustomSettingsEntity.setUpdatorName(mCommercailSettingModel.getCreatorName());
                isSuccess = mCommercialCustomSettingsService.modfityById(mCommercialCustomSettingsEntity);
            }

        } catch (Exception e) {
            isSuccess = false;
        }

        String actionSuccess = "";
        if (isSuccess) {
            actionSuccess = "success";
        } else {
            actionSuccess = "fail";
        }
        return String.format("redirect:/internal/customerLevelRule/gotoPage?brandIdenty=%d&shopIdenty=%d&creatorId=%d&creatorName=%s&successOrfail=%s",
                mCommercailSettingModel.getBrandIdenty(), mCommercailSettingModel.getShopIdenty(), mCommercailSettingModel.getCreatorId(), mCommercailSettingModel.getCreatorName(),actionSuccess);
    }
}
