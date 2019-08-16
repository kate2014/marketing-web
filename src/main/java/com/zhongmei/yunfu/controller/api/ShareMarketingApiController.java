package com.zhongmei.yunfu.controller.api;

import com.zhongmei.yunfu.controller.api.model.ShareActionReq;
import com.zhongmei.yunfu.controller.model.BaseDataModel;
import com.zhongmei.yunfu.controller.model.ShareActionModel;
import com.zhongmei.yunfu.controller.model.ShareMarketingModel;
import com.zhongmei.yunfu.domain.entity.*;
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

    @Autowired
    OperationalRecordsService mOperationalRecordsService;
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
     * 分享有礼接口  支持门店分享、活动分析、新品分享、拼团、秒杀、砍价、特价活动
     * ShareType 分享活动类型1：门店分享 2：新品分享 3：活动分享 4：拼团 5：秒杀 6：砍价  7：特价活动
     * @param model
     * @param mShareActionReq
     * @return
     */
    @GetMapping("/shareAction")
    public BaseDataModel shareCount(ModelMap model, ShareActionReq mShareActionReq) {

        BaseDataModel mBaseDataModel = new BaseDataModel();
        try {
            Boolean isSuccess = true;
            if (mShareActionReq.getShareType() == 1) {
                //触发优惠券
                sendCouponForCustomer(1, mShareActionReq);
            }  else if (mShareActionReq.getShareType() == 3) {
                //活动分享次数+1
                isSuccess = pushPlanActivityService.updateActivityNumber(mShareActionReq.getLinksId(), null, 1);
                isSuccess = sendCouponForCustomer(3, mShareActionReq);
                //触发优惠券
            } else if (mShareActionReq.getShareType() == 2) {
                //新品分享次数+1
                isSuccess = mPushPlanNewDishService.refreshShareNumber(mShareActionReq.getLinksId());
                //触发优惠券
                isSuccess = sendCouponForCustomer(2, mShareActionReq);
            }

            //因目前只有分享门店、分享新品、分享活动才赠送优惠券，所以不对其他分享做优惠券下发
//            else if(mShareActionReq.getShareType() == 4){
//
//            } else if(mShareActionReq.getShareType() == 5){
//
//            } else if(mShareActionReq.getShareType() == 6){
//
//            } else if(mShareActionReq.getShareType() == 7){
//
//            }

            //添加顾客查看记录,如该顾客对该条信息已有同样的操作是，只需在原有操作次数的基础上+1
            if(mShareActionReq.getWxOpenId() != null){
                OperationalRecordsEntity orEntity = new OperationalRecordsEntity();
                orEntity.setBrandIdenty(mShareActionReq.getBrandIdenty());
                orEntity.setShopIdenty(mShareActionReq.getShopIdenty());
                orEntity.setWxOpenId(mShareActionReq.getWxOpenId());
                orEntity.setCustomerId(mShareActionReq.getCustomerId());
                orEntity.setActivityId(mShareActionReq.getLinksId());
                orEntity.setType(2);
                orEntity.setSource(mShareActionReq.getShareType());
                OperationalRecordsEntity recordEntity = mOperationalRecordsService.queryByCustomer(orEntity);
                if(recordEntity == null){
                    orEntity = new OperationalRecordsEntity();
                    orEntity.setBrandIdenty(mShareActionReq.getBrandIdenty());
                    orEntity.setShopIdenty(mShareActionReq.getShopIdenty());
                    orEntity.setCustomerId(mShareActionReq.getCustomerId());
                    orEntity.setCustomerPhone(mShareActionReq.getCustomerPhone());
                    orEntity.setCustomerName(mShareActionReq.getCustomerName());
                    orEntity.setWxOpenId(mShareActionReq.getWxOpenId());
                    orEntity.setWxPhoto(mShareActionReq.getWxPhoto());
                    orEntity.setWxName(mShareActionReq.getWxName());
                    orEntity.setActivityId(mShareActionReq.getLinksId());
                    orEntity.setOperationalCount(1);
                    orEntity.setType(2);
                    orEntity.setSource(mShareActionReq.getShareType());
                    orEntity.setServerCreateTime(new Date());
                    orEntity.setServerUpdateTime(new Date());
                    mOperationalRecordsService.addOperational(orEntity);
                }else{
                    orEntity.setBrandIdenty(mShareActionReq.getBrandIdenty());
                    orEntity.setShopIdenty(mShareActionReq.getShopIdenty());
                    orEntity.setWxOpenId(mShareActionReq.getWxOpenId());
                    orEntity.setWxPhoto(mShareActionReq.getWxPhoto());
                    orEntity.setActivityId(mShareActionReq.getLinksId());
                    orEntity.setOperationalCount(recordEntity.getOperationalCount()+1);
                    orEntity.setServerUpdateTime(new Date());
                    mOperationalRecordsService.modiftyOperational(orEntity);
                }
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

    public Boolean sendCouponForCustomer(int shareType, ShareActionReq mShareActionReq) {


        Boolean isSuccess = true;
        try {

            List<MarketingShareEntity> listMarketingShare = mMarketingShareService.findSharePlanByShopId(mShareActionReq.getShopIdenty());
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

                    mCustomerCouponService.sendCustomerCoupon(mShareActionReq.getBrandIdenty(), mShareActionReq.getShopIdenty(),
                            mShareActionReq.getCustomerId(), mShareActionReq.getWxOpenId(),ms.getCouponId(),ms.getCouponName(),
                            mShareActionReq.getLinksId(), source);

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
