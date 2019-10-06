package com.zhongmei.yunfu.service.impl;

import com.zhongmei.yunfu.domain.entity.CustomerStoredEntity;
import com.zhongmei.yunfu.service.CustomerStoredService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerStoredServiceImplTest {

    @Autowired
    CustomerStoredService customerStoredService;

    @Test
    public void recharge() throws Exception {
        CustomerStoredEntity customerStored = new CustomerStoredEntity();
        //customerStored.baseCreate(req.getCreatorId(), req.getCreatorName());
        customerStored.setCustomerId(832L);
        customerStored.setTradeAmount(BigDecimal.valueOf(100));
        customerStored.setTradeId(-100L);
        customerStored.setPaymentItemId(-100L);
        customerStored.setShopIdenty(1L);
        customerStored.setBrandIdenty(1L);
        customerStoredService.recharge(customerStored);
    }

}