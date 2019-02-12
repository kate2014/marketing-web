package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhongmei.yunfu.domain.entity.*;
import com.zhongmei.yunfu.domain.mapper.PushMessageCustomerMapper;
import com.zhongmei.yunfu.service.CustomerCouponService;
import com.zhongmei.yunfu.service.PushMessageCustomerService;
import com.zhongmei.yunfu.service.PushPlanActivityService;
import com.zhongmei.yunfu.service.PushPlanNewDishService;
import com.zhongmei.yunfu.thirdlib.wxapp.WxTemplateMessageHandler;
import com.zhongmei.yunfu.thirdlib.wxapp.msg.CouponPushMessage;
import com.zhongmei.yunfu.thirdlib.wxapp.msg.WxTempMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 推送信息给顾客 服务实现类
 * </p>
 *
 * @author yangyp
 * @since 2018-09-13
 */
@Service
public class PushMessageCustomerServiceImpl extends ServiceImpl<PushMessageCustomerMapper, PushMessageCustomerEntity> implements PushMessageCustomerService {

    @Autowired
    CustomerCouponService mCustomerCouponService;
    @Autowired
    CouponServiceImpl mCouponServiceImpl;
    @Autowired
    PushPlanNewDishService mPushPlanNewDishService;
    @Autowired
    PushPlanActivityService mPushPlanActivityService;

    @Override
    public Boolean installPush(PushMessageCustomerEntity mPushMessageCustomer) throws Exception {
        Boolean isSuccess = insert(mPushMessageCustomer);
        return isSuccess;
    }

    @Override
    public Boolean installPushBatch(Integer type, List<Long> listCustomerID, Long relationId, Long brandIdentity, Long shopIdentity, Long creatorId, String creatorName) throws Exception {

        String heading = "";
        String imageUrl = "";
        String message = "";
        Date startTime = Calendar.getInstance().getTime(), endTime = null;
        CouponEntity mCouponEntity = new CouponEntity();
        //type 推送类型：1：新品活动推送  2：门店活动推送  3：优惠券推送
        if (type == 1) {
            PushPlanNewDishEntity mPushPlanNewDishEntity = mPushPlanNewDishService.queryDetailById(relationId);
            heading = mPushPlanNewDishEntity.getName();
            imageUrl = mPushPlanNewDishEntity.getImgUrl();
            message = mPushPlanNewDishEntity.getPlanDesc();
            startTime = mPushPlanNewDishEntity.getBeginTime();
            endTime = mPushPlanNewDishEntity.getEndTime();

        } else if (type == 2) {
            PushPlanActivityEntity mPushPlanActivityEntity = mPushPlanActivityService.findActivityDetailById(relationId);
            heading = mPushPlanActivityEntity.getName();
            imageUrl = mPushPlanActivityEntity.getImgUrl();
            message = mPushPlanActivityEntity.getPlanDesc();
            startTime = mPushPlanActivityEntity.getBeginTime();
            endTime = mPushPlanActivityEntity.getEndTime();
        } else if (type == 3) {
            mCouponEntity = mCouponServiceImpl.queryByid(relationId);
            heading = mCouponEntity.getName();
            message = mCouponEntity.getContent();
            startTime = mCouponEntity.getServerCreateTime();
            endTime = mCouponEntity.getEndTime();
        }

        List<CustomerCouponEntity> listCustomerCoupon = new ArrayList<>();
        List<PushMessageCustomerEntity> listData = new ArrayList<>();
        for (Long customerId : listCustomerID) {
            PushMessageCustomerEntity mPushMessageCustomerEntity = new PushMessageCustomerEntity();
            mPushMessageCustomerEntity.baseCreate(creatorId, creatorName);
            mPushMessageCustomerEntity.setType(type);
            mPushMessageCustomerEntity.setCustomerId(customerId);
            mPushMessageCustomerEntity.setRelationId(relationId);
            mPushMessageCustomerEntity.setHeading(heading);
            mPushMessageCustomerEntity.setImgUrl(imageUrl);
            mPushMessageCustomerEntity.setMessage(message);
            mPushMessageCustomerEntity.setState(1);
            mPushMessageCustomerEntity.setStatusFlag(1);
            mPushMessageCustomerEntity.setBrandIdenty(brandIdentity);
            mPushMessageCustomerEntity.setShopIdenty(shopIdentity);
            listData.add(mPushMessageCustomerEntity);

            if (type == 3) {
                CustomerCouponEntity mCustomerCoupon = new CustomerCouponEntity();
                mCustomerCoupon.setCouponId(mCouponEntity.getId());
                mCustomerCoupon.setSourceId(1);
                mCustomerCoupon.setCouponType(mCouponEntity.getCouponType());
                mCustomerCoupon.setCouponName(mCouponEntity.getName());
                mCustomerCoupon.setCustomerId(customerId);
                mCustomerCoupon.setWxCustomerOpenid("");
                mCustomerCoupon.setStatus(1);
                mCustomerCoupon.setEnabledFlag(1);
                mCustomerCoupon.setStatusFlag(1);
                mCustomerCoupon.setBrandIdenty(brandIdentity);
                mCustomerCoupon.setShopIdenty(shopIdentity);

                listCustomerCoupon.add(mCustomerCoupon);
            }
        }

        if (listCustomerCoupon.size() != 0) {
            mCustomerCouponService.addCustomerCouponBatch(listCustomerCoupon);
        }

        Boolean isSuccess = insertBatch(listData);
        sendCouponPushMessage(startTime, endTime, listData);
        return isSuccess;
    }

    private void sendCouponPushMessage(Date startTime, Date endTime, List<PushMessageCustomerEntity> listData) {
        try {
            //发送消息
            for (PushMessageCustomerEntity customerCouponEntity : listData) {
                CouponPushMessage couponPushMessage = new CouponPushMessage();
                couponPushMessage.setMsgType(WxTempMsg.msgType_CouponPush);
                couponPushMessage.setBrandIdenty(customerCouponEntity.getBrandIdenty());
                couponPushMessage.setShopIdenty(customerCouponEntity.getShopIdenty());
                couponPushMessage.setCustomerId(customerCouponEntity.getCustomerId());
                couponPushMessage.setSendDate(startTime != null ? startTime.getTime() : null);
                couponPushMessage.setEndDate(endTime != null ? endTime.getTime() : null);
                couponPushMessage.setProductName(customerCouponEntity.getHeading());
                couponPushMessage.setNotes(customerCouponEntity.getMessage());
                WxTemplateMessageHandler.sendWxTemplateMessage(couponPushMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Boolean modifyPushState(Long pushId, int state) throws Exception {
        PushMessageCustomerEntity mPC = new PushMessageCustomerEntity();
        mPC.setId(pushId);
        mPC.setState(state);
        EntityWrapper<PushMessageCustomerEntity> eWrapper = new EntityWrapper<>(new PushMessageCustomerEntity());
        Boolean isSuccess = update(mPC, eWrapper);
        return isSuccess;
    }

    @Override
    public List<PushMessageCustomerEntity> queryPushByCustomer(Long brandIdentity, Long shopIdentity, String customerId) throws Exception {
        EntityWrapper<PushMessageCustomerEntity> eWrapper = new EntityWrapper<>(new PushMessageCustomerEntity());
        eWrapper.eq("brand_identy", brandIdentity);
        eWrapper.eq("shop_identy", shopIdentity);
        eWrapper.eq("state", 1);

        if (customerId != null) {
            eWrapper.in("customer_id", customerId);
        }


        eWrapper.setSqlSelect("id,type,relation_id,heading,img_url,message");
        List<PushMessageCustomerEntity> listData = selectList(eWrapper);
        return listData;
    }
}
