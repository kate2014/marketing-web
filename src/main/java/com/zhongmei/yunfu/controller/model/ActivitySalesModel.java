package com.zhongmei.yunfu.controller.model;

import com.zhongmei.yunfu.controller.model.base.WebBaseModel;

import java.math.BigDecimal;

public class ActivitySalesModel extends WebBaseModel {

    private Long id;

    /**
     * 名称
     */
    private String name;

    private String imageUrl;

    private BigDecimal saleAmount;

    private Long productId;

    private String productName;

    private String profile;
    /**
     * 服务原价
     */
    private BigDecimal originalPrice;

    private Integer customerBuyCount;

    private String describe;

    private String endTime;

    private String validityPeriod;

    private Integer joinCount;

    private Integer statusFlag;

    private Integer enabledFlag;

    private Long activityId;

    private Long giftId;

    private String giftName;

    private Integer orderCount;


    private BigDecimal firstMinAmount;
    /**
     * 直接推荐随机最大红包
     */
    private BigDecimal firstMaxAmount;
    /**
     * 间接推荐随机最小红包
     */
    private BigDecimal secondMinAmount;
    /**
     * 间接推荐随机最大红包
     */
    private BigDecimal secondMaxAmount;
    /**
     * 品牌id
     */
    private Long brandIdenty;
    /**
     * 门店id
     */
    private Long shopIdenty;

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public BigDecimal getSaleAmount() {
        return saleAmount;
    }

    public void setSaleAmount(BigDecimal saleAmount) {
        this.saleAmount = saleAmount;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
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

    public Integer getJoinCount() {
        return joinCount;
    }

    public void setJoinCount(Integer joinCount) {
        this.joinCount = joinCount;
    }

    public Integer getStatusFlag() {
        return statusFlag;
    }

    public void setStatusFlag(Integer statusFlag) {
        this.statusFlag = statusFlag;
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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Integer getCustomerBuyCount() {
        return customerBuyCount;
    }

    public void setCustomerBuyCount(Integer customerBuyCount) {
        this.customerBuyCount = customerBuyCount;
    }

    public Integer getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(Integer enabledFlag) {
        this.enabledFlag = enabledFlag;
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

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    public BigDecimal getFirstMinAmount() {
        return firstMinAmount;
    }

    public void setFirstMinAmount(BigDecimal firstMinAmount) {
        this.firstMinAmount = firstMinAmount;
    }

    public BigDecimal getFirstMaxAmount() {
        return firstMaxAmount;
    }

    public void setFirstMaxAmount(BigDecimal firstMaxAmount) {
        this.firstMaxAmount = firstMaxAmount;
    }

    public BigDecimal getSecondMinAmount() {
        return secondMinAmount;
    }

    public void setSecondMinAmount(BigDecimal secondMinAmount) {
        this.secondMinAmount = secondMinAmount;
    }

    public BigDecimal getSecondMaxAmount() {
        return secondMaxAmount;
    }

    public void setSecondMaxAmount(BigDecimal secondMaxAmount) {
        this.secondMaxAmount = secondMaxAmount;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
