package com.zhongmei.yunfu.thirdlib.wxapp;

import com.zhongmei.yunfu.controller.api.model.WxTemplateSendMsgReq;
import com.zhongmei.yunfu.thirdlib.wxapp.msg.ColloageFailMessage;
import com.zhongmei.yunfu.thirdlib.wxapp.msg.CouponPushMessage;
import com.zhongmei.yunfu.util.DateFormatUtil;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 拼团失败推送消息
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ColloageFailWxTemplateMessageHandler extends WxTemplateMessageHandler<ColloageFailMessage> {

    @Override
    protected List<String> getTemplateCode() {
        return Arrays.asList("AT0310", "拼团失败通知");
    }

    @Override
    protected void createDateItem(WxTemplateSendMsgReq wxTemplateSendMsgReq, ColloageFailMessage wxTempMsg) {
        super.createDateItem(wxTemplateSendMsgReq, wxTempMsg);

        wxTemplateSendMsgReq.template_id = "LlpoUbj2qIwCRoRJudZ0x6MDAsUyEVFCFPuRHRjs9u8";

        wxTemplateSendMsgReq.addDataItem("keyword1", wxTempMsg.getTradeNo());
        wxTemplateSendMsgReq.addDataItem("keyword2", wxTempMsg.getCollageName());
        wxTemplateSendMsgReq.addDataItem("keyword3", wxTempMsg.getTradeAmount());
        wxTemplateSendMsgReq.addDataItem("keyword4", wxTempMsg.getJoinCount());
        wxTemplateSendMsgReq.addDataItem("keyword5", wxTempMsg.getFinishCount());
        wxTemplateSendMsgReq.addDataItem("keyword6", wxTempMsg.getEndTime());
        wxTemplateSendMsgReq.addDataItem("keyword7", wxTempMsg.getCollageStart());
        wxTemplateSendMsgReq.addDataItem("keyword8", wxTempMsg.getFailMessage());
        wxTemplateSendMsgReq.addDataItem("keyword9", wxTempMsg.getTradeStart());
    }
}
