package com.zhongmei.yunfu.controller.api;

import com.zhongmei.yunfu.controller.model.BaseDataModel;
import com.zhongmei.yunfu.controller.model.ShareActionModel;
import com.zhongmei.yunfu.controller.model.ShareMarketingModel;
import com.zhongmei.yunfu.domain.entity.CouponEntity;
import com.zhongmei.yunfu.domain.entity.CustomerCouponEntity;
import com.zhongmei.yunfu.domain.entity.CustomerEntity;
import com.zhongmei.yunfu.domain.entity.MarketingShareEntity;
import com.zhongmei.yunfu.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * 分享有礼接口
 */
@RestController
@RequestMapping("/wxapp/marketingShare")
public class ShareMarketingApiController {

    @Autowired
    MarketingShareService mMarketingShareService;
    @Autowired
    PushPlanActivityService pushPlanActivityService;
    @Autowired
    PushPlanNewDishService mPushPlanNewDishService;
    @Autowired
    CustomerCouponService mCustomerCouponService;
    @Autowired
    CustomerService mCustomerService;
    /**
     * 获取门分享设置
     *
     * @param model
     * @param shareMarketingModel
     * @return
     */
    @GetMapping("/queryShareMaketing")
    public BaseDataModel queryShareMarketing(Model model, ShareMarketingModel shareMarketingModel) {

        List<MarketingShareEntity> listMarketingShare = mMarketingShareService.findSharePlanByShopId(shareMarketingModel.getShopIdenty());

        BaseDataModel mBaseDataModel = new BaseDataModel();

        if (listMarketingShare != null) {
            mBaseDataModel.setState("1000");
            mBaseDataModel.setMsg("获取数据成功");
            mBaseDataModel.setData(listMarketingShare);
        } else {
            mBaseDataModel.setState("1001");
            mBaseDataModel.setMsg("获取数据失败");
            mBaseDataModel.setData(false);
        }
        return mBaseDataModel;
    }


    /**
     * 分享有礼接口  支持门店分享、活动分析、新品分享
     * ShareType 分享类型：1：门店分享 2：新品分享 3：活动分享
     *
     * @param model
     * @param shareActionModel
     * @return
     */
    @GetMapping("/shareAction")
    public BaseDataModel shareCount(ModelMap model, ShareActionModel shareActionModel) {

        BaseDataModel mBaseDataModel = new BaseDataModel();
        try {
            Boolean isSuccess = true;
            if (shareActionModel.getShareType() == 1) {
                //触发优惠券
                sendCouponForCustomer(1, shareActionModel.getCustomerId(), shareActionModel.getOpenId(), shareActionModel.getBrandIdenty(), shareActionModel.getShopIdenty());
            } else if (shareActionModel.getShareType() == 2) {
                isSuccess = mPushPlanNewDishService.refreshShareNumber(shareActionModel.getLinksId());
                //触发优惠券
                isSuccess = sendCouponForCustomer(2, shareActionModel.getCustomerId(), shareActionModel.getOpenId(), shareActionModel.getBrandIdenty(), shareActionModel.getShopIdenty());
            } else if (shareActionModel.getShareType() == 3) {
                //活动分享次数+1
                isSuccess = pushPlanActivityService.updateActivityNumber(shareActionModel.getLinksId(), null, 1);
                isSuccess = sendCouponForCustomer(3, shareActionModel.getCustomerId(), shareActionModel.getOpenId(), shareActionModel.getBrandIdenty(), shareActionModel.getShopIdenty());
                //触发优惠券
            }

            if (isSuccess) {
                mBaseDataModel.setState("1000");
                mBaseDataModel.setMsg("分享成功");
                mBaseDataModel.setData(true);
            } else {
                mBaseDataModel.setState("1001");
                mBaseDataModel.setMsg("分享失败");
                mBaseDataModel.setData(false);
            }

        } catch (Exception e) {
            e.printStackTrace();
            mBaseDataModel.setState("1001");
            mBaseDataModel.setMsg("分享失败");
            mBaseDataModel.setData(false);
        }

        return mBaseDataModel;
    }

    public Boolean sendCouponForCustomer(int shareType, Long customerId, String wxOpenid, Long brandIdenty, Long shopIdenty) {


        Boolean isSuccess = true;
        try {

            List<MarketingShareEntity> listMarketingShare = mMarketingShareService.findSharePlanByShopId(shopIdenty);
            for (MarketingShareEntity ms : listMarketingShare) {
                if (ms.getShareType() == shareType && ms.getShareState() == 1) {
                    Integer source = 0;
                    if (shareType == 1) {
                        source = 7;
                    } else if (shareType == 2) {
                        source = 9;
                    } else if (shareType == 3) {
                        source = 8;
                    }

                    CustomerCouponEntity mCustomerCoupon = new CustomerCouponEntity();
                    mCustomerCoupon.setBrandIdenty(brandIdenty);
                    mCustomerCoupon.setShopIdenty(shopIdenty);
                    mCustomerCoupon.setSourceId(source);
                    mCustomerCoupon.setCouponId(ms.getCouponId());
                    mCustomerCoupon.setCouponName(ms.getCouponName());
//                    mCustomerCoupon.setCouponType();
                    mCustomerCoupon.setCustomerId(customerId);
                    mCustomerCoupon.setWxCustomerOpenid(wxOpenid);
                    mCustomerCoupon.setStatus(1);
                    mCustomerCoupon.setEnabledFlag(1);
                    mCustomerCoupon.setServerCreateTime(new Date());
                    mCustomerCoupon.setServerUpdateTime(new Date());

                    mCustomerCouponService.addCustomerCoupon(mCustomerCoupon);

                    break;
                }
            }
            return isSuccess;
        } catch (Exception e) {
            e.printStackTrace();
            return isSuccess;
        }


    }
}
