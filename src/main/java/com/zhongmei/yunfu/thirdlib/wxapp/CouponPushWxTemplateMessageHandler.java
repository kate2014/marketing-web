package com.zhongmei.yunfu.thirdlib.wxapp;

import com.zhongmei.yunfu.controller.api.model.WxTemplateSendMsgReq;
import com.zhongmei.yunfu.thirdlib.wxapp.msg.CouponPushMessage;
import com.zhongmei.yunfu.util.DateFormatUtil;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 优惠活动推送消息
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CouponPushWxTemplateMessageHandler extends WxTemplateMessageHandler<CouponPushMessage> {

    @Override
    protected List<String> getTemplateCode() {
        return Arrays.asList("AT0878", "消息已送达通知");
    }

    @Override
    protected void createDateItem(WxTemplateSendMsgReq wxTemplateSendMsgReq, CouponPushMessage wxTempMsg) {
        super.createDateItem(wxTemplateSendMsgReq, wxTempMsg);
        wxTemplateSendMsgReq.template_id = "03BLg3Cl9lvLS-TxH9sqOwE3lSLeoJI9VNqDruM16_w";
        if (wxTempMsg.getSendDate() != null) {
            wxTemplateSendMsgReq.addDataItem("keyword1", DateFormatUtil.formatDate(new Date(wxTempMsg.getSendDate())));
        }
        String keyword2 = null;
        if (wxTempMsg.getEndDate() != null) {
            keyword2 = DateFormatUtil.formatDate(new Date(wxTempMsg.getEndDate()));
        } else {
            keyword2 = "永不过期";
        }
        wxTemplateSendMsgReq.page = "pages/checkNetwork/checkNetwork";
        wxTemplateSendMsgReq.addDataItem("keyword2", keyword2);
        wxTemplateSendMsgReq.addDataItem("keyword3", wxTempMsg.getProductName());
        wxTemplateSendMsgReq.addDataItem("keyword4", wxTempMsg.getNotes());
    }
}
