package com.zhongmei.yunfu.thirdlib.wxapp;

import com.zhongmei.yunfu.controller.api.model.WxTemplateSendMsgReq;
import com.zhongmei.yunfu.thirdlib.wxapp.msg.MemberChargeMessage;
import com.zhongmei.yunfu.thirdlib.wxapp.msg.OrderPayMessage;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * 会员充值消息
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MemberChargeMessageHandler extends WxTemplateMessageHandler<MemberChargeMessage> {

    @Override
    protected List<String> getTemplateCode() {
        return Arrays.asList("AT0016", "充值成功通知");
    }

    @Override
    protected void createDateItem(WxTemplateSendMsgReq wxTemplateSendMsgReq, MemberChargeMessage wxTempMsg) {
        super.createDateItem(wxTemplateSendMsgReq, wxTempMsg);
        wxTemplateSendMsgReq.page = "pages/checkNetwork/checkNetwork";
        wxTemplateSendMsgReq
                .addDataItem("keyword1", wxTempMsg.getChargeType())
                .addDataItem("keyword2", wxTempMsg.getMobileAccount())
                .addDataItem("keyword3", wxTempMsg.getChargeTime())
                .addDataItem("keyword4", wxTempMsg.getChargeAmount())
                .addDataItem("keyword5", wxTempMsg.getTradeNo())
                .addDataItem("keyword6", wxTempMsg.getGivenAmount())
                .addDataItem("keyword7", wxTempMsg.getAccountBalance())
                .addDataItem("keyword8", wxTempMsg.getMemo())
                .addDataItem("keyword9", wxTempMsg.getShopName());
    }
}
