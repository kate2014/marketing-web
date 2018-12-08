package com.zhongmei.yunfu.api.internal;

import com.zhongmei.yunfu.api.internal.vo.CustomerBalanceReq;
import com.zhongmei.yunfu.controller.model.BaseDataModel;
import com.zhongmei.yunfu.domain.entity.CustomerStoredEntity;
import com.zhongmei.yunfu.service.CustomerStoredService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/customer/balance")
public class CustomerStoredBalanceInternalApi extends InternalApi {

    @Autowired
    CustomerStoredService customerStoredService;

    /**
     * 充值
     *
     * @return
     */
    @RequestMapping("/recharge")
    public BaseDataModel recharge(@RequestBody CustomerBalanceReq req) throws Exception {
        CustomerStoredEntity customerStored = toCustomerStoredEntity(req);
        customerStoredService.recharge(customerStored);
        return BaseDataModel.newSuccess(customerStored);
    }

    /**
     * 消费
     *
     * @return
     */
    @RequestMapping("/expense")
    public BaseDataModel expense(@RequestBody CustomerBalanceReq req) throws Exception {
        CustomerStoredEntity customerStored = toCustomerStoredEntity(req);
        customerStoredService.expense(customerStored);
        return BaseDataModel.newSuccess(customerStored);
    }

    /**
     * 退款
     *
     * @param req
     * @return
     */
    @RequestMapping("/refund")
    public BaseDataModel refund(@RequestBody CustomerBalanceReq req) throws Exception {
        CustomerStoredEntity customerStored = toCustomerStoredEntity(req);
        customerStoredService.refund(customerStored);
        return BaseDataModel.newSuccess(customerStored);
    }

    private CustomerStoredEntity toCustomerStoredEntity(CustomerBalanceReq req) {
        CustomerStoredEntity customerStored = new CustomerStoredEntity();
        customerStored.setCustomerId(req.getCustomerId());
        customerStored.setTradeAmount(req.getUsefulAmount());
        customerStored.setTradeId(req.getTradeId());
        customerStored.setPaymentItemId(req.getPaymentItemId());
        customerStored.setShopIdenty(req.getShopId());
        customerStored.setBrandIdenty(req.getBrandId());
        customerStored.setCreatorId(req.getCreatorId());
        customerStored.setCreatorName(req.getCreatorName());
        return customerStored;
    }
}
