package com.zhongmei.yunfu;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
//@SpringBootTest
public class WebPageTest {

    @Test
    public void test() throws Exception {
        WebPage webPage = new WebPage("",100, 10, 6, 5);
        assert webPage.getLowNum() == 2;
    }
}
