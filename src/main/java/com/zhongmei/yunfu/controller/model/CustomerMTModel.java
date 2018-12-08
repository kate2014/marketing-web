package com.zhongmei.yunfu.controller.model;

import java.util.Date;

/**
 * 同行特惠邀请数据对象
 */
public class CustomerMTModel {

    private Long id;
    /**
     * 会员ID
     */
    private Long customerId;
    /**
     * 会员名称
     */
    private String customerName;
    /**
     * 微信openId
     */
    private String openId;
    /**
     * 被邀请人姓名
     */
    private String fCustomerName;
    /**
     * 优惠券id
     */
    private Long couponId;
    /**
     * 优惠券名称
     */
    private String couponName;
    /**
     * 同行特惠方案id
     */
    private Long togetherId;
    /**
     * 同行验证码
     */
    private String togetherCode;
    /**
     * 活动截止时间
     */
    private Date validData;
    /**
     * 邀请类型 1：主动邀请 2：被邀请
     */
    private Integer type;
    /**
     * 同行邀请状态  1：结束 2：未接受 3:接受 4：拒绝
     */
    private Integer status;
    /**
     * 邀请批次号，邀请人和被邀请人批次号相同，在到店使用时可根据该批次号找的对应的两个同行码进行验证
     */
    private String batchCode;
    /**
     * 门店id
     */
    private Long shopIdenty;
    /**
     * 品牌标识
     */
    private Long brandIdenty;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getTogetherId() {
        return togetherId;
    }

    public void setTogetherId(Long togetherId) {
        this.togetherId = togetherId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTogetherCode() {
        return togetherCode;
    }

    public void setTogetherCode(String togetherCode) {
        this.togetherCode = togetherCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public Long getShopIdenty() {
        return shopIdenty;
    }

    public void setShopIdenty(Long shopIdenty) {
        this.shopIdenty = shopIdenty;
    }

    public Long getBrandIdenty() {
        return brandIdenty;
    }

    public void setBrandIdenty(Long brandIdenty) {
        this.brandIdenty = brandIdenty;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getfCustomerName() {
        return fCustomerName;
    }

    public void setfCustomerName(String fCustomerName) {
        this.fCustomerName = fCustomerName;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public Date getValidData() {
        return validData;
    }

    public void setValidData(Date validData) {
        this.validData = validData;
    }
}
