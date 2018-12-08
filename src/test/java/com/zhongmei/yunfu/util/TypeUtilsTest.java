package com.zhongmei.yunfu.util;

import com.alibaba.fastjson.JSON;
import com.zhongmei.yunfu.thirdlib.wxapp.msg.OrderPayMessage;
import com.zhongmei.yunfu.thirdlib.wxapp.msg.WxTempMsg;
import com.zhongmei.yunfu.thirdlib.wxapp.OrderPayWxTemplateMessageHandler;
import org.junit.Test;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;

public class TypeUtilsTest {

    @Test
    public void getGenericClass() {
        OrderPayWxTemplateMessageHandler orderPayWxTemplateMessageHandler = new OrderPayWxTemplateMessageHandler();
        Class genericClass = TypeUtils.getGenericClass(orderPayWxTemplateMessageHandler.getClass());
        assertEquals(genericClass, OrderPayMessage.class);

        OrderPayMessage orderPayMessage = new OrderPayMessage();
//        orderPayMessage.setNotes("notes");
//        orderPayMessage.setTradeAmount(BigDecimal.ZERO);
        orderPayMessage.setTradeNo("tradeNo");
        orderPayMessage.setTradePayAmount(BigDecimal.ONE);
        orderPayMessage.setBrandIdenty(1L);
        orderPayMessage.setShopIdenty(2L);
        orderPayMessage.setMsgType(1);
        orderPayMessage.setCustomerId(1L);
        String jsonBody = JSON.toJSONString(orderPayMessage);
        WxTempMsg wxTempMsg = (WxTempMsg) JSON.parseObject(jsonBody, genericClass);
        wxTempMsg = JSON.parseObject(jsonBody, (Type) genericClass);


        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -6);
        String format = DateFormatUtil.format(calendar.getTime(), DateFormatUtil.FORMAT_DATE) + " 00:00:00";


    }
}