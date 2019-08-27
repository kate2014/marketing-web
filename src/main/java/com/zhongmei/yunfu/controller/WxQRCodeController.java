package com.zhongmei.yunfu.controller;

import com.zhongmei.yunfu.api.ApiRespStatusException;
import com.zhongmei.yunfu.controller.api.model.WxAccessToken;
import com.zhongmei.yunfu.controller.api.model.WxQRCodeResp;
import com.zhongmei.yunfu.controller.api.model.WxTemplateAddResp;
import com.zhongmei.yunfu.controller.model.TradeModel;
import com.zhongmei.yunfu.domain.entity.CommercialPaySettingEntity;
import com.zhongmei.yunfu.service.CommercialPaySettingService;
import com.zhongmei.yunfu.thirdlib.wxapp.WxTemplateMessageID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

import java.nio.Buffer;
import java.util.HashMap;
import java.util.Map;

/**
 * 获取活动推广二维码
 */
public class WxQRCodeController{

    @Autowired
    CommercialPaySettingService mCommercialPaySettingService;
    @Autowired
    RestTemplate restTemplate;

    public Buffer getWxQR(Long brandIdenty,Long shopIdenty,Long activityID) {
        try {


            String accessToken = getWxAccessToken(brandIdenty, shopIdenty);

            String wxUrl = "https://api.weixin.qq.com/cgi-bin/wxaapp/createwxaqrcode?access_token="+accessToken;

            String path = "pages/checkNetwork/checkNetwork?urlType=9&id="+activityID;

            Map<String, Object> params = new HashMap<>();
            params.put("path", path);

            WxQRCodeResp mWxQRCodeResp = restTemplate.postForObject(wxUrl, params, WxQRCodeResp.class);
            if(mWxQRCodeResp.getErrcode() == 0){
                return mWxQRCodeResp.getBuffer();
            }else {
                return null;
            }

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
