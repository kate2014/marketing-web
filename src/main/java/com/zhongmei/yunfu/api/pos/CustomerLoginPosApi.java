package com.zhongmei.yunfu.api.pos;

import com.zhongmei.yunfu.api.ApiResult;
import com.zhongmei.yunfu.api.PosApiController;
import com.zhongmei.yunfu.api.pos.vo.CustomerLoginReq;
import com.zhongmei.yunfu.api.pos.vo.CustomerLoginResp;
import com.zhongmei.yunfu.domain.entity.CustomerEntity;
import com.zhongmei.yunfu.domain.entity.CustomerExtraEntity;
import com.zhongmei.yunfu.domain.enums.EnabledFlag;
import com.zhongmei.yunfu.service.CustomerCardTimeService;
import com.zhongmei.yunfu.service.CustomerCouponService;
import com.zhongmei.yunfu.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pos")
public class CustomerLoginPosApi extends PosApiController {

    @Autowired
    CustomerService customerService;
    @Autowired
    CustomerCouponService customerCouponService;
    @Autowired
    CustomerCardTimeService customerCardTimeService;

    /**
     * 会员登录
     *
     * @return
     */
    @RequestMapping("/customer/login")
    public ApiResult login(@RequestBody CustomerLoginReq req) throws Exception {
        CustomerEntity customerEntity = customerService.login(req.getHeader().getShopId(), req.getLoginType(), req.getLoginId(), req.getIsNeedPwd(), req.getPassword());
        customerService.checkState(customerEntity);

        int couponCount = customerCouponService.selectCouponEntityCount(customerEntity.getId(), customerEntity.getShopIdenty());
        //int cardTimeCount = customerCardTimeService.selectCount(customerEntity.getId(), customerEntity.getShopIdenty());
        CustomerLoginResp customerLoginResp = new CustomerLoginResp();
        customerLoginResp.setCustomerId(customerEntity.getId());//顾客id
        customerLoginResp.setCustomerName(customerEntity.getName());// 顾客名字 （新的登录接口使用）
        customerLoginResp.setSex(customerEntity.getGender()); //性别
        customerLoginResp.setMobile(customerEntity.getMobile());//联系电话 （新接口使用）
        customerLoginResp.setPhoneNumber(customerEntity.getTelephone());//手机号码(老接口使用)
        customerLoginResp.setAddress(customerEntity.getAddress());//顾客地址
        customerLoginResp.setLevelId(customerEntity.getGroupLevelId());
        customerLoginResp.setLevel(customerEntity.getGroupLevel()); //会员等级
        customerLoginResp.setOpenId(customerEntity.getThirdId());//微信openID
        customerLoginResp.setIntegral(customerEntity.getIntegralTotal() - customerEntity.getIntegralUsed());//当前积分
        customerLoginResp.setCoupCount(couponCount);//优惠券（有/无）
        customerLoginResp.setCardCount(customerEntity.getCardResidueCount());//次卡（有/无）
        customerLoginResp.setIsDisable(customerEntity.getEnabledFlag() == EnabledFlag.DISABLED.value() ? 1 : 2);//是否停用 1.是停用; 2.否
        customerLoginResp.setBrandId(customerEntity.getBrandIdenty());//品牌id
        customerLoginResp.setCommercialId(customerEntity.getShopIdenty());//顾客所属门店id

        CustomerExtraEntity customerExtra = customerService.getCustomerExtra(customerEntity.getId());
        if (customerExtra != null) {
            customerLoginResp.setValueCardBalance(customerExtra.getStoredBalance()); //储值余额
            customerLoginResp.setRemainValue(customerExtra.getStoredBalance());
            customerLoginResp.setStoredPaymentCheck(customerExtra.getStoredPaymentCheck() != null && customerExtra.getStoredPaymentCheck() > 0);
            customerLoginResp.setStoredPrivilegeType(customerExtra.getStoredPrivilegeType());
            customerLoginResp.setStoredPrivilegeValue(customerExtra.getStoredPrivilegeValue());
            customerLoginResp.setStoredFullAmount(customerExtra.getStoredFullAmount());
        }
        return ApiResult.newSuccess(customerLoginResp);
    }
}
