package com.zhongmei.yunfu.controller.model;

import com.zhongmei.yunfu.controller.model.base.WebBaseModel;

public class CouponSearchModel extends WebBaseModel {
    private int couponType = 0; //券类型(0：全部 1：折扣、2：代金、3：礼品、)
    private int couponState = 0;//优惠卷状态（0:全部 1，使用中，2，停用）
    private String keyWord;//查询关键字

    public int getCouponType() {
        return couponType;
    }

    public void setCouponType(int couponType) {
        this.couponType = couponType;
    }

    public int getCouponState() {
        return couponState;
    }

    public void setCouponState(int couponState) {
        this.couponState = couponState;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }


}
