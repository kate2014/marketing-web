package com.zhongmei.yunfu.controller.api;


import com.zhongmei.yunfu.controller.api.model.ShopReq;
import com.zhongmei.yunfu.controller.api.model.ShopResp;
import com.zhongmei.yunfu.controller.model.BaseDataModel;
import com.zhongmei.yunfu.controller.model.BrandModel;
import com.zhongmei.yunfu.controller.model.CommercailSettingModel;
import com.zhongmei.yunfu.domain.entity.BrandEntity;
import com.zhongmei.yunfu.domain.entity.CommercialCustomSettingsEntity;
import com.zhongmei.yunfu.domain.entity.CommercialEntity;
import com.zhongmei.yunfu.service.BrandService;
import com.zhongmei.yunfu.service.CommercialCustomSettingsService;
import com.zhongmei.yunfu.service.CommercialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 门店品牌
 */
@RestController
@RequestMapping("/wxapp/shop")
public class ShopApiController {

    @Autowired
    BrandService mBrandService;
    @Autowired
    CommercialService mCommercialService;
    @Autowired
    CommercialCustomSettingsService mCommercialCustomSettingsService;

    @GetMapping("/baseDetail")
    public BaseDataModel queryBrandByAppId(Model model, ShopReq mShopReq) {
        BaseDataModel mBaseDataModel = new BaseDataModel();
        try {

            ShopResp mShopResp = new ShopResp();
            mShopResp.setStartPicture("http://media.zhongmeiyunfu.com/start_pic.png");

            if(mShopReq.getBrandIdenty() != null && mShopReq.getShopIdenty() != null){
                CommercailSettingModel mCommercailSettingModel = new CommercailSettingModel();
                mCommercailSettingModel.setBrandIdenty(mShopReq.getBrandIdenty());
                mCommercailSettingModel.setShopIdenty(mShopReq.getShopIdenty());
                mCommercailSettingModel.setSettingKey("IS_OPEN_AUTHORIZATION");

                //获取是否打开小程序授权框
                CommercialCustomSettingsEntity MCommercialCustomSettingsEntity =  mCommercialCustomSettingsService.queryByKey(mCommercailSettingModel);
                mShopResp.setOpenAuthorization(MCommercialCustomSettingsEntity.getSettingValue());
            }


            mBaseDataModel.setState("1000");
            mBaseDataModel.setMsg("获取门店信息成功");
            mBaseDataModel.setData(mShopResp);
        } catch (Exception e) {
            mBaseDataModel.setState("1001");
            mBaseDataModel.setMsg("获取门店信息失败");
            mBaseDataModel.setData(false);
        }

        return mBaseDataModel;
    }
}
