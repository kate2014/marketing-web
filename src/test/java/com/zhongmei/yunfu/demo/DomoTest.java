package com.zhongmei.yunfu.demo;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhongmei.yunfu.controller.weixinPay.WeiXinPayRsqEntity;
import com.zhongmei.yunfu.domain.entity.CustomerCardTimeEntity;
import com.zhongmei.yunfu.util.DateFormatUtil;
import com.zhongmei.yunfu.util.HttpMessageConverterUtils;
import org.junit.Test;

import java.io.*;
import java.util.Calendar;
import java.util.Date;

public class DomoTest {

    @Test
    public void contextLoads() throws IOException {
        EntityWrapper<CustomerCardTimeEntity> entityWrapper = new EntityWrapper<>();
        entityWrapper.andNew("card_expire_date >= {0} OR card_expire_date IS NULL", DateFormatUtil.formatDate(new Date()));
        entityWrapper.getSqlSegment();
        System.out.println(new File("out/gen").getAbsolutePath());

        String s = "{\"a\":\"1\",\"b\":2}";
        ByteArrayInputStream in = new ByteArrayInputStream(s.getBytes());
        PushbackInputStream pushbackInputStream = new PushbackInputStream(in, 8 * 1024);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int buffCount = 8 * 1024;
        byte[] bytes = new byte[buffCount];
        int readCount = 0;
        System.out.println("push before");
        System.out.println("ByteArrayInputStream: " + in.toString());
        while ((readCount = pushbackInputStream.read(bytes)) != -1) {
            byteArrayOutputStream.write(bytes, 0, readCount);
        }
        System.out.println("push after");
        System.out.println("ByteArrayInputStream: " + in.toString());
        System.out.println("ByteArrayOutputStream: " + byteArrayOutputStream.toString());

        pushbackInputStream.unread(bytes, 0, buffCount);

        System.out.println("push after222");
        System.out.println("ByteArrayInputStream: " + in.toString());
        /*while (readCount < buffCount) {
            readCount += pushbackInputStream.read(bytes, readCount, buffCount - readCount);
        }*/


        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date date = calendar.getTime();

        Calendar calendar2 = Calendar.getInstance();
        calendar2.add(Calendar.WEEK_OF_MONTH, 1);
        Date date2 = calendar2.getTime();

        Calendar calendar3 = Calendar.getInstance();
        calendar3.add(Calendar.MONTH, 1);
        Date date3 = calendar3.getTime();

    }

    @Test
    public void xml() throws Exception {
        WeiXinPayRsqEntity xmlToObject = HttpMessageConverterUtils.xmlToObject("<xml><return_code><![CDATA[FAIL]]></return_code>\n" +
                "<return_msg><![CDATA[1111111111111111]]></return_msg>\n" +
                "</xml>", WeiXinPayRsqEntity.class);
        System.out.println(xmlToObject);

        String type = null;
        switch (type) {
            default:
                System.out.println("00000");
                break;
        }
    }
}
