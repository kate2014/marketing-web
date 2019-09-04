package com.zhongmei.yunfu.controller.api;


import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.Buffer;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 门店品牌
 */
@RestController
@RequestMapping("/wxapp/poster")
public class ActivityPosterApiController {

    private static Logger log = LoggerFactory.getLogger(ActivityPosterApiController.class);

    @Autowired
    ActivitySalesService mActivitySalesService;
    @Autowired
    CommercialPaySettingService mCommercialPaySettingService;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    ActivityRedPacketsService mActivityRedPacketsService;


    @GetMapping("/posterDetail")
    public BaseDataModel posterDetail(Model model, WxQRCodeReq mWxQRCodeReq){
        BaseDataModel mBaseDataModel = new BaseDataModel();
        try {

            WxQRCodeResp mWxQRCodeResp = new WxQRCodeResp();
            //获取二维码信息
            String qrCode = getQRCode(mWxQRCodeReq);
            mWxQRCodeResp.setQrCode(qrCode);

            //获取海报图片
            String posterImage = mActivitySalesService.queryPosterById(mWxQRCodeReq.getActivityId());

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
            mBaseDataModel.setMsg("获取海报信息成功");
            mBaseDataModel.setData(mWxQRCodeResp);
        } catch (Exception e) {
            log.info("获取海报信息失败:"+e);
            e.printStackTrace();
            mBaseDataModel.setState("1001");
            mBaseDataModel.setMsg("获取海报信息失败");
            mBaseDataModel.setData(false);
        }

        return mBaseDataModel;
    }

    /**
     * 获取 accessToken
     * @return
     * @throws ApiRespStatusException
     */
    private String getWxAccessToken(Long brandIdenty,Long shopIdenty) throws ApiRespStatusException {

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


    /**
     * 获取顾客小程序分享二维码
     * @param mWxQRCodeReq
     * @return
     */
    String getQRCode(WxQRCodeReq mWxQRCodeReq){

//        Map<String, Object> params = new HashMap<>();
//        params.put("scene", "urlType=9&id=333&cId="+mWxQRCodeReq.getRecommendCustomerId());
//        params.put("page", "pages/checkNetwork/checkNetwork");

        JSONObject params = new JSONObject();
        params.put("scene", "9#"+mWxQRCodeReq.getActivityId()+"#"+mWxQRCodeReq.getRecommendCustomerId());
        params.put("page", "pages/checkNetwork/checkNetwork");
        params.put("width", "430");
        String param = params.toJSONString();
        log.info("=====param===="+param);
//        String param="{ \"scene\":\"19014\" ,\"page\":\"pages/checkNetwork/checkNetwork\",\"width\":430}";

        String accessToken = getWxAccessToken(mWxQRCodeReq.getBrandIdenty(),mWxQRCodeReq.getShopIdenty());
        String requeststr2="https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="+accessToken;
        return sendPost3(requeststr2,param);

    }


    /**
     * post请求返回base64的图片数据
     * @param url
     * @param params
     * @return
     */
    public String sendPost3(String url,String params) {
        PrintWriter out = null;
        String result = "";
        InputStream inputStream=null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
//			conn.setRequestProperty("accept", "*/*");
//			conn.setRequestProperty("connection", "Keep-Alive");
//			conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
//			conn.setCharacterEncoding("gbk");
            conn.setRequestProperty("Content-Type", "application/json;charset-gbk");
            conn.setRequestProperty("responseType", "arraybuffer");

            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(params);
            // flush输出流的缓冲
            out.flush();
            //获取流数据
            inputStream = conn.getInputStream();


            // 将获取流转为base64格式
            byte[] data = null;
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            byte[] buff = new byte[100];
            int rc = 0;
            while ((rc = inputStream.read(buff, 0, 100)) > 0) {
                swapStream.write(buff, 0, rc);
            }
            data = swapStream.toByteArray();

            result = new String(Base64.getEncoder().encode(data));

        }catch (Exception e) {
            log.info("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(inputStream!=null){
                    inputStream.close();
                }

            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }



}
