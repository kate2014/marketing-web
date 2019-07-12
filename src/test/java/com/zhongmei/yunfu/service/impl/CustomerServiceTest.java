package com.zhongmei.yunfu.service.impl;

import com.zhongmei.yunfu.api.pos.vo.CustomerLoginReq;
import com.zhongmei.yunfu.controller.model.CustomerDrainSearchModel;
import com.zhongmei.yunfu.domain.entity.AuthUserEntity;
import com.zhongmei.yunfu.domain.entity.CustomerEntity;
import com.zhongmei.yunfu.service.CustomerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class CustomerServiceTest {

    @Autowired
    CustomerService customerService;

    @Test
    public void loginCardNoEntity() throws Exception {
        CustomerEntity login = customerService.login(1L, CustomerLoginReq.LoginType.CARD_NO_ENTITY, "123456789", false, null);
    }

    @Test
    public void findCustomerByDrain() throws Exception {
        CustomerDrainSearchModel customerDrainSearchModel = new CustomerDrainSearchModel();
        AuthUserEntity authUserEntity = new AuthUserEntity();
        authUserEntity.setBrandIdenty(1L);
        authUserEntity.setShopIdenty(1L);
        customerDrainSearchModel.setUser(authUserEntity);
        customerDrainSearchModel.setConsumptionLastTime("2019-01-01");
        customerDrainSearchModel.setWillExpireDay(4);
        customerDrainSearchModel.setOpType(1);
        customerDrainSearchModel.setCardResidueCount(100);
        customerDrainSearchModel.setStoredBalance(BigDecimal.valueOf(200));
        customerService.findCustomerByDrain(customerDrainSearchModel);
    }
}
