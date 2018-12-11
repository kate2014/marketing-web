package com.zhongmei.yunfu.api.internal;


import com.zhongmei.yunfu.api.internal.vo.CustomerQueryOrAddReq;
import com.zhongmei.yunfu.controller.model.BaseDataModel;
import com.zhongmei.yunfu.core.security.Password;
import com.zhongmei.yunfu.domain.entity.CustomerEntity;
import com.zhongmei.yunfu.domain.entity.CustomerLevelRuleEntity;
import com.zhongmei.yunfu.service.CustomerLevelRuleService;
import com.zhongmei.yunfu.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author yangyp
 * @since 2018-08-29
 */
@RestController
@RequestMapping("/internal/customer")
public class CustomerInternalApi extends InternalApi {

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerLevelRuleService customerLevelRuleService;

    @RequestMapping("/queryOrAdd")
    public BaseDataModel save(@RequestBody CustomerQueryOrAddReq req) throws Exception {
        /*List<CustomerQueryOrAddReq.QueryCustomer> data = req.getData();
        List<String> mobiles = new ArrayList<>();
        data.forEach(it -> mobiles.add(it.getMobile()));*/

        CustomerEntity customer = customerService.getCustomerByMobile(req.getShopIdenty(), req.getMobile());
        //for (CustomerQueryOrAddReq.QueryCustomer item : data) {
        //CustomerEntity customerEntity = getCustomerByMobile(customerByMobile, req.getMobile());
        if (customer == null) {
            customer = new CustomerEntity();
            String password = Password.create().generate(req.getMobile(), req.getMobile().substring(req.getMobile().length() - 6));
            CustomerLevelRuleEntity levelRuleEntity = customerLevelRuleService.queryLevelByScore(req.getShopIdenty(), req.getBrandIdenty(), 0);
            customer.baseCreate(req.getCreatorId(), req.getCreatorName());
            customer.setShopIdenty(req.getShopIdenty());
            customer.setBrandIdenty(req.getBrandIdenty());
            customer.setName(req.getName());
            customer.setGender(req.getGender());
            customer.setMobile(req.getMobile());
            customer.setPassword(password);
            customer.setGroupLevelId(levelRuleEntity.getId());
            customer.setGroupLevel(levelRuleEntity.getLevelName());
            customer.setSourceId(1);
            customerService.insert(customer);
            //customerByMobile.add(customer);
        }
        //}

        return BaseDataModel.newSuccess(customer);
    }

    private CustomerEntity getCustomerByMobile(List<CustomerEntity> customerByMobile, String mobile) {
        for (CustomerEntity customerEntity : customerByMobile) {
            if (customerEntity.getMobile().equals(mobile)) {
                return customerEntity;
            }
        }
        return null;
    }

}

