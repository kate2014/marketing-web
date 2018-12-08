package com.zhongmei.yunfu.controller.model;

import com.zhongmei.yunfu.domain.entity.PaymentEntity;
import com.zhongmei.yunfu.domain.entity.PaymentItemEntity;

public class CreatePayModel {

    private PaymentEntity mPaymentEntity;

    private PaymentItemEntity mPaymentItemEntity;

    private WxPayMsgModel mWxPayMsgModel;

    public PaymentEntity getmPaymentEntity() {
        return mPaymentEntity;
    }

    public void setmPaymentEntity(PaymentEntity mPaymentEntity) {
        this.mPaymentEntity = mPaymentEntity;
    }

    public PaymentItemEntity getmPaymentItemEntity() {
        return mPaymentItemEntity;
    }

    public void setmPaymentItemEntity(PaymentItemEntity mPaymentItemEntity) {
        this.mPaymentItemEntity = mPaymentItemEntity;
    }

    public WxPayMsgModel getmWxPayMsgModel() {
        return mWxPayMsgModel;
    }

    public void setmWxPayMsgModel(WxPayMsgModel mWxPayMsgModel) {
        this.mWxPayMsgModel = mWxPayMsgModel;
    }
}
