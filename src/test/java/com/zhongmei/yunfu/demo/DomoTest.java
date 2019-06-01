package com.zhongmei.yunfu.demo;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhongmei.yunfu.controller.weixinPay.WeiXinPayRsqEntity;
import com.zhongmei.yunfu.domain.entity.CustomerCardTimeEntity;
import com.zhongmei.yunfu.util.DateFormatUtil;
import com.zhongmei.yunfu.util.HttpMessageConverterUtils;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import org.junit.Test;

import java.io.*;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

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

    @Test
    public void rxJava() throws Exception {
        Integer[] list = {1, 2, 3, 4, 5, 6, 7};
        /*int pageCount = list.length / 3 + Math.min(list.length % 3, 1);
        CountDownLatch countDownLatch = new CountDownLatch(pageCount);
        Flowable.fromArray(list)
                .buffer(3)
                .parallel()
                .runOn(Schedulers.io(), 1)
                .map(ints ->
                {
                    Thread.sleep(300);
                    System.out.println(Thread.currentThread().getName() + " " + ints);
                    return ints;
                })
                .sequential()
                //.observeOn(Schedulers.single())
                .subscribe(ints ->
                {
                    countDownLatch.countDown();
                    System.out.println(Thread.currentThread() + " subscribe: -> " + ints);
                });

        //Thread.sleep(5000);
        countDownLatch.await();
        System.out.println("-----------");

        Integer[] list1 = {11, 12, 13, 14, 15, 16, 17};
        Flowable.fromArray(list1)
                .buffer(3)
                .parallel()
                .runOn(Schedulers.io())
                .map(ints ->
                {
                    System.out.println(Thread.currentThread().getName() + " " + ints);
                    return ints;
                })
                .sequential()
                //.observeOn(Schedulers.single())
                .subscribe(ints ->
                {
                    System.out.println(Thread.currentThread() + " subscribe: -> " + ints);
                });
        Thread.sleep(5000);*/


        Flowable.fromArray(list)
                .map(integer -> integer + " ->")
                .buffer(Integer.MAX_VALUE)
                .map(ints ->
                {
                    System.out.println(Thread.currentThread().getName() + " " + ints);
                    return ints;
                })
                .subscribe(ints ->
                {
                    System.out.println(Thread.currentThread() + " subscribe: -> " + ints);
                });
    }
}
