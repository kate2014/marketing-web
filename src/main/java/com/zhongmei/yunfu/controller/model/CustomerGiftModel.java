package com.zhongmei.yunfu.controller.model;

import com.zhongmei.yunfu.controller.model.base.WebBaseModel;

import java.util.Date;

public class CustomerGiftModel extends WebBaseModel {

    private Long id;
    /**
     * 品牌Id
     */
    private Long brandIdenty;
    /**
     * 门店Id
     */
    private Long shopIdenty;

    private Long customerId;

    private String customerPhone;

    private String customerName;

    private String wxOpenId;

    private String wxPhoto;

    private String wxName;

    private Long activityId;

    private Long giftId;

    private String giftName;

    private Date serverCreateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getWxOpenId() {
        return wxOpenId;
    }

    public void setWxOpenId(String wxOpenId) {
        this.wxOpenId = wxOpenId;
    }

    public String getWxPhoto() {
        return wxPhoto;
    }

    public void setWxPhoto(String wxPhoto) {
        this.wxPhoto = wxPhoto;
    }

    public String getWxName() {
        return wxName;
    }

    public void setWxName(String wxName) {
        this.wxName = wxName;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Long getGiftId() {
        return giftId;
    }

    public void setGiftId(Long giftId) {
        this.giftId = giftId;
    }

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public Date getServerCreateTime() {
        return serverCreateTime;
    }

    public void setServerCreateTime(Date serverCreateTime) {
        this.serverCreateTime = serverCreateTime;
    }
}
