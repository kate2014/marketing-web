package com.zhongmei.yunfu.api.pos;

import com.zhongmei.yunfu.api.ApiResult;
import com.zhongmei.yunfu.api.PosApiController;
import com.zhongmei.yunfu.api.pos.vo.CustomerECardSaveReq;
import com.zhongmei.yunfu.domain.entity.CustomerEntityCardEntity;
import com.zhongmei.yunfu.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pos/customer/ecard")
public class CustomerEntityCardPosApi extends PosApiController {

    @Autowired
    CustomerService customerService;

    @RequestMapping("/bind")
    public ApiResult save(@RequestBody CustomerECardSaveReq req) throws Exception {
        /*CustomerEntity login = customerService.login(req.getHeader().getShopId(), CustomerLoginReq.LoginType.CARD_NO_ENTITY, req.getCardNo(), false, null);
        if (login != null) {
            throw new ApiResponseStatusException(ApiResponseStatus.CUSTOMER_ENTITY_CARD_BINDED);
        }*/

        CustomerEntityCardEntity customerEntityCardEntity = customerService.saveEntityCard(req);
        return ApiResult.newSuccess(customerEntityCardEntity);
    }
}
