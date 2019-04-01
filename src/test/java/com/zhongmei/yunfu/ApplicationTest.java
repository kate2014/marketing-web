package com.zhongmei.yunfu;


import com.alibaba.fastjson.JSON;
import com.zhongmei.yunfu.api.internal.vo.CustomerCardTimeBuyReq;
import com.zhongmei.yunfu.service.CustomerCardTimeService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class ApplicationTest {

    @Before
    public void init() {
        System.out.println("开始测试-----------------");
    }

    @After
    public void after() {
        System.out.println("测试结束-----------------");
    }

    @Autowired
    private CustomerCardTimeService customerCardTimeService;

    @Test
    public void testGetEntFileById() throws Exception {
        CustomerCardTimeBuyReq customerCardTimeBuyReq = JSON.parseObject("", CustomerCardTimeBuyReq.class);
        customerCardTimeService.buy(customerCardTimeBuyReq);
    }
}
