package com.zhongmei.yunfu.thirdlib.wxapp;

import com.alibaba.fastjson.JSON;
import com.zhongmei.yunfu.api.ApiResponseStatus;
import com.zhongmei.yunfu.api.ApiResponseStatusException;
import com.zhongmei.yunfu.controller.api.model.WxAccessToken;
import com.zhongmei.yunfu.controller.api.model.WxTemplateSendMsgReq;
import com.zhongmei.yunfu.controller.api.model.WxTemplateSendMsgResp;
import com.zhongmei.yunfu.domain.entity.CommercialPaySettingEntity;
import com.zhongmei.yunfu.domain.entity.WxFormEntity;
import com.zhongmei.yunfu.service.CommercialPaySettingService;
import com.zhongmei.yunfu.service.WxFormService;
import com.zhongmei.yunfu.thirdlib.UploadFile;
import com.zhongmei.yunfu.thirdlib.wxapp.msg.WxTempMsg;
import com.zhongmei.yunfu.util.SpringUtil;
import com.zhongmei.yunfu.util.TypeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信模板消息
 *
 * @param <T>
 */
public abstract class WxTemplateMessageHandler<T extends WxTempMsg> {

    protected Logger log = LoggerFactory.getLogger(UploadFile.class);
    private static final Map<Integer, Class<? extends WxTemplateMessageHandler>> WxTemplateMessage = new HashMap<>();

    static {
        //WxTemplateMessage.put(OrderPayMessage.class, OrderPayWxTemplateMessageHandler.class);
        //WxTemplateMessage.put(CouponPushMessage.class, CouponPushWxTemplateMessageHandler.class);

        WxTemplateMessage.put(WxTempMsg.msgType_OrderPay, OrderPayWxTemplateMessageHandler.class);
        WxTemplateMessage.put(WxTempMsg.msgType_CouponPush, CouponPushWxTemplateMessageHandler.class);
        WxTemplateMessage.put(WxTempMsg.msgCollage_fail, ColloageFailWxTemplateMessageHandler.class);
    }

    @Autowired
    WxFormService mWxFormService;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    CommercialPaySettingService commercialPaySettingService;

    @Autowired
    WxTemplateMessageID wxTemplateMessageID;

    //private T wxTempMsg;

    /*public static <T extends WxTempMsg> WxTemplateMessageHandler create(T wxTempMsg) {
        try {
            Class<? extends WxTemplateMessageHandler> aClass = WxTemplateMessage.get(wxTempMsg.getClass());
            WxTemplateMessageHandler bean = SpringUtil.getBean(aClass);
            bean.wxTempMsg = wxTempMsg;
            return bean;
        } catch (Exception e) {
            return null;
        }
    }*/

    public static WxTemplateMessageHandler create(int msgType) {
        try {
            Class<? extends WxTemplateMessageHandler> aClass = WxTemplateMessage.get(msgType);
            WxTemplateMessageHandler bean = SpringUtil.getBean(aClass);
            //bean.wxTempMsg = wxTempMsg;
            return bean;
        } catch (Exception e) {
            return null;
        }
    }

    public static void sendWxTemplateMessage(WxTempMsg wxTempMsg) {
        WxTemplateMessageHandler wxTemplateMessageHandler = WxTemplateMessageHandler.create(wxTempMsg.getMsgType());
        wxTemplateMessageHandler.send(wxTempMsg);
    }

    protected abstract List<String> getTemplateCode();

    private WxAccessToken getWxAccessToken(String appID, String appSecret) throws ApiResponseStatusException {
        String accessTokenUrl = String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s", appID, appSecret);
        WxAccessToken wxAccessToken = restTemplate.getForObject(accessTokenUrl, WxAccessToken.class);
        return wxAccessToken;
    }

    public void send(String jsonBody) throws Exception {
        T wxTempMsg = (T) JSON.parseObject(jsonBody, TypeUtils.getGenericClass(getClass()));
        send(wxTempMsg);
    }

    @Async
    public void send(T wxTempMsg) {
        try {
            log.info("sending... wxTempMsg=" + wxTempMsg);
            WxFormEntity wxFormEntity = mWxFormService.queryFormUnusedByOpenId(wxTempMsg.getShopIdenty(), wxTempMsg.getBrandIdenty(), wxTempMsg.getCustomerId());
            if (wxFormEntity == null) {
                throw new ApiResponseStatusException(ApiResponseStatus.FOUND, "wxFormEntity is null");
            }

            CommercialPaySettingEntity paySettingEntity = new CommercialPaySettingEntity();
            paySettingEntity.setBrandIdenty(wxTempMsg.getBrandIdenty());
            paySettingEntity.setShopIdenty(wxTempMsg.getShopIdenty());
            paySettingEntity.setType(1);
            paySettingEntity.setStatusFlag(1);
            CommercialPaySettingEntity commercialPaySettingEntity = commercialPaySettingService.queryData(paySettingEntity);
            WxAccessToken wxAccessToken = getWxAccessToken(commercialPaySettingEntity.getAppid(), commercialPaySettingEntity.getAppsecret());
            if (!wxAccessToken.isOk()) {
                throw new ApiResponseStatusException(ApiResponseStatus.FOUND, wxAccessToken.errmsg);
            }

            sendInternal(wxFormEntity, wxAccessToken, wxTempMsg);
            wxFormEntity.setStatus(2); //更新为已使用
            mWxFormService.updateById(wxFormEntity);

            log.info("send success");
        } catch (Exception e) {
            log.error("send error: ", e);
        }
    }

    protected void sendInternal(WxFormEntity wxFormEntity, WxAccessToken wxAccessToken, T wxTempMsg) throws Exception {
        //发送模版消息
        String wxSendMessageUrl = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=" + wxAccessToken.access_token;
        WxTemplateSendMsgReq wxTemplateSendMsgReq = createWxTemplateSendMsgReq(wxFormEntity, wxTempMsg);
        String templateMessageID = wxTemplateMessageID.getTemplateMessageID(wxAccessToken.access_token, getTemplateCode().get(0), getTemplateCode().get(1));
        wxTemplateSendMsgReq.template_id = templateMessageID;
        WxTemplateSendMsgResp wxTemplateSendMsgResp = restTemplate.postForObject(wxSendMessageUrl, wxTemplateSendMsgReq, WxTemplateSendMsgResp.class);
        if (!wxTemplateSendMsgResp.isOk()) {
            wxFormEntity.setStatus(3); //更新为已使用
            mWxFormService.updateById(wxFormEntity);
            throw new ApiResponseStatusException(ApiResponseStatus.FOUND, wxTemplateSendMsgResp.errmsg);
        }
    }

    protected WxTemplateSendMsgReq createWxTemplateSendMsgReq(WxFormEntity wxFormEntity, T wxTempMsg) throws Exception {
        WxTemplateSendMsgReq wxTemplateSendMsgReq = new WxTemplateSendMsgReq();
        wxTemplateSendMsgReq.touser = wxFormEntity.getOpenId();
        wxTemplateSendMsgReq.form_id = wxFormEntity.getFormId();
        //wxTemplateSendMsgReq.template_id = "y5YfXBtOzkJBQyCfDqLQMTm7QBlT2XX3aUXpzT38HF8";
        //wxTemplateSendMsgReq.page;
        createDateItem(wxTemplateSendMsgReq, wxTempMsg);
        //wxTemplateSendMsgReq.template_id = templateMessageID;
        return wxTemplateSendMsgReq;
    }

    protected void createDateItem(WxTemplateSendMsgReq wxTemplateSendMsgReq, T wxTempMsg) {
        /*wxTemplateSendMsgReq
                .addDataItem("keyword1", "105")
                .addDataItem("keyword2", "100")
                .addDataItem("keyword3", "100000000100000000001")
                .addDataItem("keyword4", "粤海喜来登酒店");*/
    }

}
