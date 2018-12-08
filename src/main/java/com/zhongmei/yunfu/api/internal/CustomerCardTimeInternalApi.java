package com.zhongmei.yunfu.api.internal;

import com.zhongmei.yunfu.api.internal.vo.CustomerCardTimeBuyReq;
import com.zhongmei.yunfu.api.internal.vo.CustomerCardTimeRefundReq;
import com.zhongmei.yunfu.controller.model.BaseDataModel;
import com.zhongmei.yunfu.service.CustomerCardTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/customer/cardtime")
public class CustomerCardTimeInternalApi extends InternalApi {

    @Autowired
    CustomerCardTimeService customerCardTimeService;

    /**
     * 购买
     *
     * @return
     */
    @RequestMapping("/buy")
    public Object buy(@RequestBody CustomerCardTimeBuyReq req) throws Exception {
        customerCardTimeService.buy(req);
        return BaseDataModel.newSuccess(null);
    }

    /**
     * 退货
     *
     * @return
     */
    @RequestMapping("/return")
    public Object refund(@RequestBody CustomerCardTimeRefundReq req) throws Exception {
        customerCardTimeService.refund(req);
        return BaseDataModel.newSuccess(null);
    }
}
