package com.zhongmei.yunfu.controller.model;

import com.zhongmei.yunfu.controller.model.base.WebBaseModel;

import java.math.BigDecimal;
import java.util.Date;

public class FlashSalesCustomerModel{

    private Long id;
    /**
     * 秒杀活动名称
     */
    private String name;
    /**
     * 秒杀活动简介
     */
    private String profile;
    /**
     * 活动开始时间
     */
    private String beginTime;
    /**
     * 活动结束时间
     */
    private String endTime;
    /**
     * 活动使用有效期
     */
    private String validityPeriod;
    /**
     * 原价格
     */
    private BigDecimal originalPrice;
    /**
     * 秒杀价格
     */
    private BigDecimal flashPrice;

    /**
     * 活动图片
     */
    private String imgUrl;
    /**
     * 启用停用标识 : 1:启用;2:停用
     */
    private Integer enabledFlag;
    /**
     * 品牌id
     */
    private Long brandIdentity;
    /**
     * 门店id
     */
    private Long shopIdentity;
    /**
     * 状态标识1:有效 2:无效
     */
    private Integer statusFlag;
    /**
     * 状态 1未使用、2已使用
     */
    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(String validityPeriod) {
        this.validityPeriod = validityPeriod;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public BigDecimal getFlashPrice() {
        return flashPrice;
    }

    public void setFlashPrice(BigDecimal flashPrice) {
        this.flashPrice = flashPrice;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(Integer enabledFlag) {
        this.enabledFlag = enabledFlag;
    }

    public Long getBrandIdentity() {
        return brandIdentity;
    }

    public void setBrandIdentity(Long brandIdentity) {
        this.brandIdentity = brandIdentity;
    }

    public Long getShopIdentity() {
        return shopIdentity;
    }

    public void setShopIdentity(Long shopIdentity) {
        this.shopIdentity = shopIdentity;
    }

    public Integer getStatusFlag() {
        return statusFlag;
    }

    public void setStatusFlag(Integer statusFlag) {
        this.statusFlag = statusFlag;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
