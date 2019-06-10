package com.zhongmei.yunfu.controller.model;

import com.zhongmei.yunfu.domain.entity.BookingEntity;
import com.zhongmei.yunfu.domain.entity.CustomerCardTimeEntity;
import com.zhongmei.yunfu.domain.entity.CustomerEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class LoginCustomerModel {

    private Long brandIdenty;

    private Long shopIdenty;

    private Integer gender;

    private String groupLevel;

    private Long groupLevelId;

    private String mobile;

    private String name;

    private Long relateId;

    private Integer statusFlag;

    private BigDecimal storedBalance = BigDecimal.ZERO;

    private String thirdId;

    private Date consumptionLastTime;

    private int cardTimeCount;

    private int couponCount;

    private BookingEntity mBookingEntity;

    public Long getBrandIdenty() {
        return brandIdenty;
    }

    public void setBrandIdenty(Long brandIdenty) {
        this.brandIdenty = brandIdenty;
    }

    public Long getShopIdenty() {
        return shopIdenty;
    }

    public void setShopIdenty(Long shopIdenty) {
        this.shopIdenty = shopIdenty;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getGroupLevel() {
        return groupLevel;
    }

    public void setGroupLevel(String groupLevel) {
        this.groupLevel = groupLevel;
    }

    public Long getGroupLevelId() {
        return groupLevelId;
    }

    public void setGroupLevelId(Long groupLevelId) {
        this.groupLevelId = groupLevelId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getRelateId() {
        return relateId;
    }

    public void setRelateId(Long relateId) {
        this.relateId = relateId;
    }

    public Integer getStatusFlag() {
        return statusFlag;
    }

    public void setStatusFlag(Integer statusFlag) {
        this.statusFlag = statusFlag;
    }

    public BigDecimal getStoredBalance() {
        return storedBalance;
    }

    public void setStoredBalance(BigDecimal storedBalance) {
        this.storedBalance = storedBalance;
    }

    public String getThirdId() {
        return thirdId;
    }

    public void setThirdId(String thirdId) {
        this.thirdId = thirdId;
    }

    public Date getConsumptionLastTime() {
        return consumptionLastTime;
    }

    public void setConsumptionLastTime(Date consumptionLastTime) {
        this.consumptionLastTime = consumptionLastTime;
    }

    public int getCardTimeCount() {
        return cardTimeCount;
    }

    public void setCardTimeCount(int cardTimeCount) {
        this.cardTimeCount = cardTimeCount;
    }

    public int getCouponCount() {
        return couponCount;
    }

    public void setCouponCount(int couponCount) {
        this.couponCount = couponCount;
    }

    public BookingEntity getmBookingEntity() {
        return mBookingEntity;
    }

    public void setmBookingEntity(BookingEntity mBookingEntity) {
        this.mBookingEntity = mBookingEntity;
    }
}
