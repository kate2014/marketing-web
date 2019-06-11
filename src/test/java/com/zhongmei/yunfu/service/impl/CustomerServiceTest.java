package com.zhongmei.yunfu.service.impl;

import com.zhongmei.yunfu.controller.model.CustomerDrainSearchModel;
import com.zhongmei.yunfu.domain.entity.AuthUserEntity;
import com.zhongmei.yunfu.service.CustomerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class CustomerServiceTest {

    @Autowired
    CustomerService customerService;

    @Test
    public void findCustomerByDrain() throws Exception {
        CustomerDrainSearchModel customerDrainSearchModel = new CustomerDrainSearchModel();
        AuthUserEntity authUserEntity = new AuthUserEntity();
        authUserEntity.setBrandIdenty(1L);
        authUserEntity.setShopIdenty(1L);
        customerDrainSearchModel.setUser(authUserEntity);
        customerService.findCustomerByDrain(customerDrainSearchModel);
    }
}
