package com.zhongmei.yunfu.api.pos;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.api.ApiResult;
import com.zhongmei.yunfu.api.PosApiController;
import com.zhongmei.yunfu.api.pos.vo.CustomerCardTimeReq;
import com.zhongmei.yunfu.api.pos.vo.CustomerCardTimeResp;
import com.zhongmei.yunfu.domain.entity.CustomerCardTimeEntity;
import com.zhongmei.yunfu.service.CustomerCardTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建订单的次卡接口
 */
@RestController
@RequestMapping("/pos/customer/cardtime")
public class CustomerCardTimeTradeApi extends PosApiController {

    @Autowired
    CustomerCardTimeService customerCardTimeService;

    @RequestMapping
    public ApiResult list(@RequestBody CustomerCardTimeReq req) throws Exception {
        Page<CustomerCardTimeEntity> listPageByCustomerId = customerCardTimeService.getListPageByCustomerId(req.getCustomerId(), req.getPageNo(), req.getPageSize());
        List<CustomerCardTimeResp> result = new ArrayList<>();
        for (CustomerCardTimeEntity record : listPageByCustomerId.getRecords()) {
            CustomerCardTimeResp customerCardTimeResp = new CustomerCardTimeResp();
            customerCardTimeResp.setCardType(record.getCardType());
            customerCardTimeResp.setCardExpireDate(record.getCardExpireDate() != null ? record.getCardExpireDate().getTime() : null);
            customerCardTimeResp.setBrandIdenty(req.getHeader().getBrandId());
            customerCardTimeResp.setCardInstanceId(record.getId());
            customerCardTimeResp.setServiceId(record.getDishId());
            customerCardTimeResp.setServiceName(record.getDishName());
            customerCardTimeResp.setServiceTotalTime(record.getTradeCount());
            customerCardTimeResp.setServiceRemainderTime(record.getResidueCount());
            result.add(customerCardTimeResp);
        }

        return ApiResult.newSuccess(result, listPageByCustomerId);
    }

}
