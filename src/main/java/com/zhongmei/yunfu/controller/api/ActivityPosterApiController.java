package com.zhongmei.yunfu.controller.api;


import com.zhongmei.yunfu.api.ApiRespStatusException;
import com.zhongmei.yunfu.controller.api.model.WxAccessToken;
import com.zhongmei.yunfu.controller.api.model.WxQRCodeReq;
import com.zhongmei.yunfu.controller.api.model.WxQRCodeResp;
import com.zhongmei.yunfu.controller.model.BaseDataModel;
import com.zhongmei.yunfu.controller.model.BrandModel;
import com.zhongmei.yunfu.domain.entity.ActivityRedPacketsEntity;
import com.zhongmei.yunfu.domain.entity.BrandEntity;
import com.zhongmei.yunfu.domain.entity.CommercialEntity;
import com.zhongmei.yunfu.domain.entity.CommercialPaySettingEntity;
import com.zhongmei.yunfu.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.nio.Buffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 门店品牌
 */
@RestController
@RequestMapping("/wxapp/poster")
public class ActivityPosterApiController {

    @Autowired
    ActivitySalesService mActivitySalesService;
    @Autowired
    CommercialPaySettingService mCommercialPaySettingService;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    ActivityRedPacketsService mActivityRedPacketsService;


    @GetMapping("/posterDetail")
    public BaseDataModel posterDetail(Model model, WxQRCodeReq mWxQRCodeReq) {
        BaseDataModel mBaseDataModel = new BaseDataModel();
        try {
            WxQRCodeResp mWxQRCodeResp = new WxQRCodeResp();
            //获取二维码信息
//            Buffer mBuffer = getWxQR(mWxQRCodeReq);
            //获取海报图片
            String posterImage = mActivitySalesService.queryPosterById(mWxQRCodeReq.getActivityId());
//            mWxQRCodeResp.setBuffer(mBuffer);
            mWxQRCodeResp.setPosterImage(posterImage);

            //获取红包规则
            ActivityRedPacketsEntity mActivityRedPacketsEntity = new ActivityRedPacketsEntity();
            mActivityRedPacketsEntity.setBrandIdenty(mWxQRCodeReq.getBrandIdenty());
            mActivityRedPacketsEntity.setShopIdenty(mWxQRCodeReq.getShopIdenty());
            mActivityRedPacketsEntity.setActivityId(mWxQRCodeReq.getActivityId());
            mActivityRedPacketsEntity = mActivityRedPacketsService.queryRule(mActivityRedPacketsEntity);

            mWxQRCodeResp.setMinRedPackets(mActivityRedPacketsEntity.getFirstMinAmount());
            mWxQRCodeResp.setMaxRedPackets(mActivityRedPacketsEntity.getFirstMaxAmount());
            mBaseDataModel.setState("1000");
            mBaseDataModel.setMsg("获取门店信息成功");
            mBaseDataModel.setData(mWxQRCodeResp);
        } catch (Exception e) {
            mBaseDataModel.setState("1001");
            mBaseDataModel.setMsg("获取门店信息失败");
            mBaseDataModel.setData(false);
        }

        return mBaseDataModel;
    }


    public Buffer getWxQR(WxQRCodeReq mWxQRCodeReq) {
        try {

            String accessToken = getWxAccessToken(mWxQRCodeReq.getBrandIdenty(), mWxQRCodeReq.getShopIdenty());

            String wxUrl = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="+accessToken;

            Long recommendCustomerId = mWxQRCodeReq.getRecommendCustomerId();
            String recommendOpenId = mWxQRCodeReq.getRecommendOpenId();

            Map<String, Object> params = new HashMap<>();
            params.put("scene", "urlType=9&id=333&cId="+recommendCustomerId);
            params.put("page", "pages/checkNetwork/checkNetwork");

            WxQRCodeResp mBuffer = restTemplate.postForObject(wxUrl, params, WxQRCodeResp.class);
            System.out.println("======qr code==="+mBuffer.toString());
            return mBuffer.getBuffer();

        }catch (Exception e){
            e.printStackTrace();
        }


        return null;
    }

    /**
     * 获取 accessToken
     * @return
     * @throws ApiRespStatusException
     */
    private String getWxAccessToken(Long brandIdenty,Long shopIdenty) throws Exception {

        CommercialPaySettingEntity mCommercialPaySetting = new CommercialPaySettingEntity();
        mCommercialPaySetting.setBrandIdenty(brandIdenty);
        mCommercialPaySetting.setShopIdenty(shopIdenty);
        mCommercialPaySetting.setStatusFlag(1);
        mCommercialPaySetting.setType(1);

        CommercialPaySettingEntity mCommercialPaySettingEntity = mCommercialPaySettingService.queryData(mCommercialPaySetting);
        String appID = mCommercialPaySettingEntity.getAppid();
        String appSecret = mCommercialPaySettingEntity.getAppsecret();

        String accessTokenUrl = String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s", appID, appSecret);
        WxAccessToken wxAccessToken = restTemplate.getForObject(accessTokenUrl, WxAccessToken.class);
        return wxAccessToken.access_token;
    }

}
