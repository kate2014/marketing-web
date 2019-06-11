package com.zhongmei.yunfu.service.impl;

import com.zhongmei.yunfu.api.ApiRespStatus;
import com.zhongmei.yunfu.api.ApiRespStatusException;
import com.zhongmei.yunfu.domain.entity.CustomerStoredEntity;
import com.zhongmei.yunfu.service.CustomerStoredService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class CustomerStoredServiceTest {

    @Autowired
    CustomerStoredService customerStoredService;

    @Test
    public void refund() throws Exception {
        CustomerStoredEntity byPaymentItemId = customerStoredService.getByPaymentItemId(1L, 2099L, 1715L);
        if (byPaymentItemId == null) {
            throw new ApiRespStatusException(ApiRespStatus.FOUND);
        }
        //CustomerStoredEntity customerStored = toCustomerStoredEntity(req);
        CustomerStoredEntity customerStored = (CustomerStoredEntity) byPaymentItemId.clone();
        customerStored.setId(null);
        //customerStored.baseCreate(req.getCreatorId(), req.getCreatorName());

        customerStoredService.refund(customerStored);
    }
}
