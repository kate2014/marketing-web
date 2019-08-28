package com.zhongmei.yunfu.thirdlib.wxapp;

import com.zhongmei.yunfu.controller.api.model.WxTemplateSendMsgReq;
import com.zhongmei.yunfu.thirdlib.wxapp.msg.ActivityBuyMessage;
import com.zhongmei.yunfu.thirdlib.wxapp.msg.OrderPayMessage;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * 活动购买成功通知
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ActivityBuyWxTemplateMessageHandler extends WxTemplateMessageHandler<ActivityBuyMessage> {

    @Override
    protected List<String> getTemplateCode() {
        return Arrays.asList("AT0002", "购买成功通知");
    }

    @Override
    protected void createDateItem(WxTemplateSendMsgReq wxTemplateSendMsgReq, ActivityBuyMessage wxTempMsg) {
        super.createDateItem(wxTemplateSendMsgReq, wxTempMsg);
        wxTemplateSendMsgReq.template_id = "aOtzU0CsZ8Id3uqj4gstwzZZafcLneX79oSJ1__eaY0";
        wxTemplateSendMsgReq.page = "/pages/checkNetwork/checkNetwork?urlType=8";
        wxTemplateSendMsgReq
                .addDataItem("keyword1", wxTempMsg.getTradeNo())
                .addDataItem("keyword2", wxTempMsg.getCode())
                .addDataItem("keyword3", wxTempMsg.getTradePayAmount().toString()+"元")
                .addDataItem("keyword4", wxTempMsg.getDishName())
                .addDataItem("keyword5", wxTempMsg.getBuyDate())
                .addDataItem("keyword6", wxTempMsg.getValidityPeriod())
                .addDataItem("keyword7", wxTempMsg.getShopName())
                .addDataItem("keyword8", wxTempMsg.getRemarks());



    }
}
