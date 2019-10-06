package com.zhongmei.yunfu.service.impl;

import com.zhongmei.yunfu.api.ApiRespStatus;
import com.zhongmei.yunfu.api.ApiRespStatusException;
import com.zhongmei.yunfu.api.pos.vo.CustomerLoginReq;
import com.zhongmei.yunfu.controller.model.CustomerDrainSearchModel;
import com.zhongmei.yunfu.core.security.Password;
import com.zhongmei.yunfu.domain.entity.AuthUserEntity;
import com.zhongmei.yunfu.domain.entity.CustomerEntity;
import com.zhongmei.yunfu.domain.entity.CustomerLevelRuleEntity;
import com.zhongmei.yunfu.domain.enums.CustomerSourceId;
import com.zhongmei.yunfu.domain.enums.EnabledFlag;
import com.zhongmei.yunfu.service.CustomerLevelRuleService;
import com.zhongmei.yunfu.service.CustomerPrivilegeRuleService;
import com.zhongmei.yunfu.service.CustomerService;
import com.zhongmei.yunfu.util.DateFormatUtil;
import org.apache.commons.lang.StringUtils;
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

    @Autowired
    CustomerLevelRuleService customerLevelRuleService;

    @Test
    public void save() throws Exception {
        CustomerEntity mCustomer = new CustomerEntity();
        //mCustomer.setUuid(req);
        mCustomer.setName("5500");
        mCustomer.setGender(1);
        mCustomer.setMobile("18702885500");
        mCustomer.setBirthday(DateFormatUtil.parseDate("2016-01-01", DateFormatUtil.FORMAT_DATE));
        //mCustomer.setHobby(req.getEnvironmentHobby());
        //mCustomer.setAddress(req.getAddress());
        //mCustomer.setProfile(req.getMemo());

            String consumePassword = mCustomer.getPassword();
            if (StringUtils.isBlank(consumePassword)) {
                int mobileLength = mCustomer.getMobile().length();
                consumePassword = mCustomer.getMobile().substring(mobileLength - 6, mobileLength);
            }
            consumePassword = Password.create().generate(mCustomer.getMobile(), consumePassword);
            mCustomer.setPassword(consumePassword);

            CustomerLevelRuleEntity levelRuleEntity = customerLevelRuleService.getCustomerLevelRuleEntity(1L, 1L, 0);
            mCustomer.baseCreate(1L, "1");
            mCustomer.setIntegralTotal(0);
            mCustomer.setGroupLevelId(levelRuleEntity.getId());
            mCustomer.setGroupLevel(levelRuleEntity.getLevelName());
            mCustomer.setSourceId(CustomerSourceId.POS.value());
            //mCustomer.setStoredBalance(BigDecimal.ZERO);
            mCustomer.setShopIdenty(1L);
            mCustomer.setBrandIdenty(1L);
            mCustomer.setEnabledFlag(EnabledFlag.ENABLED.value());
            customerService.save(mCustomer);
    }

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
