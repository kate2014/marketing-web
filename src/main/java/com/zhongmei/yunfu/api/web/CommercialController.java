package com.zhongmei.yunfu.api.web;


import com.zhongmei.yunfu.controller.BaseController;
import com.zhongmei.yunfu.controller.model.ShopSettingModel;
import com.zhongmei.yunfu.domain.entity.CommercialEntity;
import com.zhongmei.yunfu.domain.entity.CommercialPaySettingEntity;
import com.zhongmei.yunfu.service.CommercialPaySettingService;
import com.zhongmei.yunfu.service.CommercialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 商户信息表 前端控制器
 * </p>
 *
 * @author pigeon88
 * @since 2018-08-26
 */
@Controller
@RequestMapping("/internal/commercial")
public class CommercialController extends BaseController {

    @Autowired
    CommercialPaySettingService mCommercialPaySettingService;
    @Autowired
    CommercialService mCommercialService;

    @RequestMapping("/settingPage")
    public String list(Model model, ShopSettingModel mShopSettingModel) {

        try{
            CommercialEntity mCommercialEntity = mCommercialService.queryCommercialById(mShopSettingModel.getShopIdenty());
            model.addAttribute("commercial", mCommercialEntity);

            CommercialPaySettingEntity mCommercialPaySetting = new CommercialPaySettingEntity();
            mCommercialPaySetting.setBrandIdenty(mShopSettingModel.getBrandIdenty());
            mCommercialPaySetting.setShopIdenty(mShopSettingModel.getShopIdenty());
            mCommercialPaySetting.setStatusFlag(1);

            List<CommercialPaySettingEntity> listData = mCommercialPaySettingService.queryDataList(mCommercialPaySetting);
            for(CommercialPaySettingEntity cps : listData){
                if(cps.getType() == 1){
                    model.addAttribute("wxAppsecret", cps.getAppsecret());
                    model.addAttribute("wxAppid", cps.getAppid());
                    model.addAttribute("wxShopId", cps.getWxShopId());
                    model.addAttribute("apiSecret", cps.getApiSecret());
                    model.addAttribute("secretFilename", cps.getSecretFilename());
                    model.addAttribute("secretFilepath", cps.getSecretFilepath());
                }else if(cps.getType() == 2){
                    model.addAttribute("appsecret", cps.getAppsecret());
                    model.addAttribute("appid", cps.getAppid());
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }
        model.addAttribute("successOrfail", mShopSettingModel.getSuccessOrfail());
        model.addAttribute("brandIdenty", mShopSettingModel.getBrandIdenty());
        model.addAttribute("shopIdenty", mShopSettingModel.getShopIdenty());

        return "shop_setting";
    }

    /**
     *
     * @param model
     * @param mShopSettingModel
     * @return
     */
    @RequestMapping("/modfiyShopMessage")
    public String modfiyShopMessage(Model model, ShopSettingModel mShopSettingModel){
        try {
            CommercialEntity mCommercialEntity = new CommercialEntity();
            mCommercialEntity.setCommercialId(mShopSettingModel.getShopIdenty());
            mCommercialEntity.setCommercialName(mShopSettingModel.getCommercialName());
            mCommercialEntity.setCommercialContact(mShopSettingModel.getCommercialContact());
            mCommercialEntity.setCommercialPhone(mShopSettingModel.getCommercialPhone());
            mCommercialEntity.setCommercialAdress(mShopSettingModel.getCommercialAdress());
            mCommercialEntity.setCommercialDesc(mShopSettingModel.getCommercialDesc());
            mCommercialEntity.setCommercialLogo(mShopSettingModel.getImgUrl());
            Boolean isSuccess = mCommercialService.modifyCommercial(mCommercialEntity);
            if(isSuccess){
                mShopSettingModel.setSuccessOrfail("success");
            }else{
                mShopSettingModel.setSuccessOrfail("fail");
            }
            list(model, mShopSettingModel);
            return "shop_setting";
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }


    }
    /**
     * 门店设置
     * @param model
     * @param mShopSettingModel
     * @return
     */
    @RequestMapping("/setting")
    public String setting(Model model, ShopSettingModel mShopSettingModel){

        Boolean isSuccess = true;
        try {

            CommercialPaySettingEntity mCommercialPaySetting = new CommercialPaySettingEntity();
            mCommercialPaySetting.setBrandIdenty(mShopSettingModel.getBrandIdenty());
            mCommercialPaySetting.setShopIdenty(mShopSettingModel.getShopIdenty());
            mCommercialPaySetting.setStatusFlag(1);
            mCommercialPaySetting.setType(mShopSettingModel.getType());
            CommercialPaySettingEntity settingData = mCommercialPaySettingService.queryData(mCommercialPaySetting);

            if(mShopSettingModel.getType() == 1){
                mCommercialPaySetting.setAppid(mShopSettingModel.getWxAppid());
                mCommercialPaySetting.setAppsecret(mShopSettingModel.getWxAppsecret());
                mCommercialPaySetting.setWxShopId(mShopSettingModel.getWxShopId());
                mCommercialPaySetting.setApiSecret(mShopSettingModel.getApiSecret());
                mCommercialPaySetting.setSecretFilename(mShopSettingModel.getSecretFilepathTitle());
                mCommercialPaySetting.setSecretFilepath(mShopSettingModel.getSecretFilepath());
                mCommercialPaySetting.setType(1);
                mCommercialPaySetting.setBrandIdenty(mShopSettingModel.getBrandIdenty());
                mCommercialPaySetting.setShopIdenty(mShopSettingModel.getShopIdenty());
                mCommercialPaySetting.setStatusFlag(1);
                //判断该条设置是否已经存在，如果不存在这进行新增，存在则进行修改
                if(settingData == null){
                    isSuccess = mCommercialPaySettingService.installData(mCommercialPaySetting);
                }else{
                    mCommercialPaySetting.setServerUpdateTime(new Date());
                    isSuccess = mCommercialPaySettingService.updateData(mCommercialPaySetting);
                }


            }else if(mShopSettingModel.getType() == 2){
                //判断该条设置是否已经存在，如果不存在这进行新增，存在则进行修改
                mCommercialPaySetting.setAppid(mShopSettingModel.getAppid());
                mCommercialPaySetting.setAppsecret(mShopSettingModel.getAppsecret());
                mCommercialPaySetting.setType(2);
                mCommercialPaySetting.setBrandIdenty(mShopSettingModel.getBrandIdenty());
                mCommercialPaySetting.setShopIdenty(mShopSettingModel.getShopIdenty());
                mCommercialPaySetting.setStatusFlag(1);
                if(settingData == null){

                    isSuccess = mCommercialPaySettingService.installData(mCommercialPaySetting);
                }else{
                    mCommercialPaySetting.setId(settingData.getId());
                    mCommercialPaySetting.setServerUpdateTime(new Date());
                    isSuccess = mCommercialPaySettingService.updateData(mCommercialPaySetting);
                }

            }


        }catch (Exception e){
            e.printStackTrace();
            isSuccess = false;
        }

        model.addAttribute("mShopSettingModel", mShopSettingModel);
        model.addAttribute("brandIdenty", mShopSettingModel.getBrandIdenty());
        model.addAttribute("shopIdenty", mShopSettingModel.getShopIdenty());

        if(isSuccess){
            mShopSettingModel.setSuccessOrfail("success");
        }else{
            mShopSettingModel.setSuccessOrfail("fail");
        }
        list(model, mShopSettingModel);
        return "shop_setting";
    }
}

