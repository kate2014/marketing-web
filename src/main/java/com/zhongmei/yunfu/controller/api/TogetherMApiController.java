package com.zhongmei.yunfu.controller.api;


import com.zhongmei.yunfu.controller.model.BaseDataModel;
import com.zhongmei.yunfu.controller.model.CustomerMTModel;
import com.zhongmei.yunfu.controller.model.TogetherMarketingModel;
import com.zhongmei.yunfu.domain.entity.CustomerCouponEntity;
import com.zhongmei.yunfu.domain.entity.CustomerMarketingTogetherEntity;
import com.zhongmei.yunfu.domain.entity.MarketingTogetherEntity;
import com.zhongmei.yunfu.service.CustomerCouponService;
import com.zhongmei.yunfu.service.CustomerMarketingTogetherService;
import com.zhongmei.yunfu.service.MarketingTogetherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * 同行特惠
 */
@RestController
@RequestMapping("/wxapp/togetherMarketing")
public class TogetherMApiController {

    @Autowired
    MarketingTogetherService marketingTogetherService;

    @Autowired
    CustomerMarketingTogetherService customerMarketingTogetherService;
    @Autowired
    CustomerCouponService mCustomerCouponService;

    @GetMapping("/findTogetherM")
    public BaseDataModel findTogetherMarketing(Model model, TogetherMarketingModel togetherMarketingModel) {

        MarketingTogetherEntity mMarketingTogether = marketingTogetherService.findMarketingTogether(togetherMarketingModel.getShopIdenty(), 1,1);
        BaseDataModel mBaseDataModel = new BaseDataModel();

        if (mMarketingTogether != null) {
            //添加浏览次数
            marketingTogetherService.updateNumber(mMarketingTogether.getId(), mMarketingTogether.getScanNumber() + 1, null);

            mBaseDataModel.setState("1000");
            mBaseDataModel.setMsg("数据获取成功");
            mBaseDataModel.setData(mMarketingTogether);

        } else {
            mBaseDataModel.setState("1001");
            mBaseDataModel.setMsg("商户未开通该活动");
            mBaseDataModel.setData(false);
        }


        return mBaseDataModel;
    }

    /**
     * 添加同行特惠分享次数
     *
     * @param model
     * @param togetherMarketingModel
     * @return
     */
    @GetMapping("/shareCount")
    public BaseDataModel shareCount(ModelMap model, TogetherMarketingModel togetherMarketingModel) {
        Boolean isSuccess = marketingTogetherService.updateNumber(togetherMarketingModel.getId(), null, 1);
        BaseDataModel mBaseDataModel = new BaseDataModel();
        if (isSuccess) {
            mBaseDataModel.setState("1000");
            mBaseDataModel.setMsg("分享成功");
            mBaseDataModel.setData(null);
        } else {
            mBaseDataModel.setState("1001");
            mBaseDataModel.setMsg("分享失败");
            mBaseDataModel.setData(null);
        }

        return mBaseDataModel;
    }

    /**
     * 发起同行特惠
     *
     * @param model
     * @param mCustomerMTModel
     * @return
     */
    @GetMapping("/togetherAction")
    public BaseDataModel customerMTModel(ModelMap model, CustomerMTModel mCustomerMTModel) {
        BaseDataModel mBaseDataModel = new BaseDataModel();
        try {
            //获取活动优惠券
            MarketingTogetherEntity mMarketingTogether = marketingTogetherService.findMarketingTogether(mCustomerMTModel.getShopIdenty(), 1,1);
            if(mMarketingTogether != null){
                mCustomerMTModel.setCouponId(mMarketingTogether.getCouponId());
                mCustomerMTModel.setCouponName(mMarketingTogether.getCouponName());
                mCustomerMTModel.setValidData(mMarketingTogether.getEndDate());
                mCustomerMTModel.setStatus(2); //1：结束 2：未接受 3:接受 4：拒绝
                mCustomerMTModel = customerMarketingTogetherService.installCMT(mCustomerMTModel);
            }

            mBaseDataModel.setState("1000");
            mBaseDataModel.setMsg("操作成功");
            mBaseDataModel.setData(mCustomerMTModel);
        } catch (Exception e) {
            e.printStackTrace();
            mBaseDataModel.setState("1001");
            mBaseDataModel.setMsg("操作失败");
            mBaseDataModel.setData(null);
        }

        return mBaseDataModel;
    }

    /**
     * 处理同行特惠邀请信息
     *
     * @param model
     * @param mCustomerMTModel
     * @return
     */
    @GetMapping("/modfiyAction")
    public BaseDataModel modfiyCTM(ModelMap model, CustomerMTModel mCustomerMTModel) {
        BaseDataModel mBaseDataModel = new BaseDataModel();
        try {
            Boolean isSuccess = true;


            isSuccess = customerMarketingTogetherService.modfiyCMT(mCustomerMTModel);

            if (isSuccess) {
                //如果接受同行，则添加优惠券
                CustomerMarketingTogetherEntity mCustomerMarketingTogether = new CustomerMarketingTogetherEntity();
                mCustomerMarketingTogether.setStatus(mCustomerMTModel.getStatus());

                if (mCustomerMTModel.getStatus() == 3) {
                    //获取活动优惠券
                    MarketingTogetherEntity mMarketingTogether = marketingTogetherService.findMarketingTogether(mCustomerMTModel.getShopIdenty(), 1,1);
                    mCustomerMarketingTogether = customerMarketingTogetherService.queryCMTByBatchCode(mCustomerMTModel.getBatchCode());
                    if (mMarketingTogether != null && mCustomerMarketingTogether != null) {
                        //给受邀用户发券
                        isSuccess = sendCouponForCustomer(mCustomerMarketingTogether.getFCustomerId(), mCustomerMarketingTogether.getFOpenId(), mMarketingTogether.getCouponId(), mMarketingTogether.getCouponName(), mMarketingTogether.getBrandIdenty(), mMarketingTogether.getShopIdenty());
                        //给发起受邀用户发券
                        isSuccess = sendCouponForCustomer(mCustomerMarketingTogether.getCustomerId(), mCustomerMarketingTogether.getOpenId(), mMarketingTogether.getCouponId(), mMarketingTogether.getCouponName(), mMarketingTogether.getBrandIdenty(), mMarketingTogether.getShopIdenty());

                    }

                }

                mBaseDataModel.setState("1000");
                mBaseDataModel.setMsg("接受邀请操作成功");
                mBaseDataModel.setData(mCustomerMarketingTogether);
            } else {
                mBaseDataModel.setState("1001");
                mBaseDataModel.setMsg("接受邀请操作失败");
                mBaseDataModel.setData(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mBaseDataModel.setState("1001");
            mBaseDataModel.setMsg("接受邀请操作失败");
            mBaseDataModel.setData(false);
        }

        return mBaseDataModel;
    }

    /**
     * 给会员发放优惠券
     *
     * @param customerId
     * @param wxOpenid
     * @param brandIdenty
     * @param shopIdenty
     * @return
     */
    public Boolean sendCouponForCustomer(Long customerId, String wxOpenid, Long couponId, String couponName, Long brandIdenty, Long shopIdenty) {
        Boolean isSuccess = true;
        try {
            CustomerCouponEntity mCustomerCoupon = new CustomerCouponEntity();
            mCustomerCoupon.setBrandIdenty(brandIdenty);
            mCustomerCoupon.setShopIdenty(shopIdenty);
            mCustomerCoupon.setSourceId(11);

            mCustomerCoupon.setCouponId(couponId);
            mCustomerCoupon.setCouponName(couponName);
            mCustomerCoupon.setCustomerId(customerId);
            mCustomerCoupon.setWxCustomerOpenid(wxOpenid);
            mCustomerCoupon.setStatus(1);
            mCustomerCoupon.setEnabledFlag(1);
            mCustomerCoupon.setServerCreateTime(new Date());
            mCustomerCoupon.setServerUpdateTime(new Date());

            isSuccess = mCustomerCouponService.addCustomerCoupon(mCustomerCoupon);
            return isSuccess;
        } catch (Exception e) {
            e.printStackTrace();
            return isSuccess;
        }
    }

    /**
     * 获取会员发起的同行特惠邀请列表/会员受邀列表
     *
     * @param model
     * @param mCustomerMTModel
     * @return
     */
    @GetMapping("/findCMTList")
    public BaseDataModel findCMTList(ModelMap model, CustomerMTModel mCustomerMTModel) {
        BaseDataModel mBaseDataModel = new BaseDataModel();
        try {
            List<CustomerMarketingTogetherEntity> listData = new ArrayList<>();
            if (mCustomerMTModel.getType() == 1) {
                listData = customerMarketingTogetherService.findCMTList(mCustomerMTModel);
            } else if (mCustomerMTModel.getType() == 2) {
                listData = customerMarketingTogetherService.findCMTInvited(mCustomerMTModel);
            }

            mBaseDataModel.setState("1000");
            mBaseDataModel.setMsg("获取数据成功");
            mBaseDataModel.setData(listData);
        } catch (Exception e) {
            e.printStackTrace();
            mBaseDataModel.setState("1001");
            mBaseDataModel.setMsg("获取数据成功");
            mBaseDataModel.setData(false);
        }

        return mBaseDataModel;
    }
}
