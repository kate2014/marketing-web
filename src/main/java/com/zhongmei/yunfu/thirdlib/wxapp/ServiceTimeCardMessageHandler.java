package com.zhongmei.yunfu.thirdlib.wxapp;

import com.zhongmei.yunfu.controller.api.model.WxTemplateSendMsgReq;
import com.zhongmei.yunfu.thirdlib.wxapp.msg.OrderPayMessage;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * 服务次卡购买消息
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ServiceTimeCardMessageHandler extends WxTemplateMessageHandler<OrderPayMessage> {

    @Override
    protected List<String> getTemplateCode() {
        return Arrays.asList("AT0009", "订单支付成功通知");
    }

    @Override
    protected void createDateItem(WxTemplateSendMsgReq wxTemplateSendMsgReq, OrderPayMessage wxTempMsg) {
        super.createDateItem(wxTemplateSendMsgReq, wxTempMsg);
        wxTemplateSendMsgReq.template_id = "y5YfXBtOzkJBQyCfDqLQMU2nxsRGfAgZcTg2M1Zvh18";

        wxTemplateSendMsgReq
                .addDataItem("keyword1", wxTempMsg.getTradeNo())
                .addDataItem("keyword2", wxTempMsg.getTradePayAmount().toString()+"元")
                .addDataItem("keyword3", wxTempMsg.getPayDate())
                .addDataItem("keyword4", wxTempMsg.getDishList());
    }
}
