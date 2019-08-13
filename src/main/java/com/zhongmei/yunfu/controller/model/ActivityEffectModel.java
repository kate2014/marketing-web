package com.zhongmei.yunfu.controller.model;

import com.zhongmei.yunfu.controller.model.base.WebBaseModel;

import java.math.BigDecimal;

public class ActivityEffectModel extends WebBaseModel {

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
    /**
     * 操作顾客人数，同一人操作多次，记为一人
     */
    private Integer persionCount;

    private Integer operationalCount;

    /**
     * 类型：1：查看  2：分享  3：购买
     */
    private Integer type;

    private BigDecimal amount;

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

    public Integer getPersionCount() {
        return persionCount;
    }

    public void setPersionCount(Integer persionCount) {
        this.persionCount = persionCount;
    }

    public Integer getOperationalCount() {
        return operationalCount;
    }

    public void setOperationalCount(Integer operationalCount) {
        this.operationalCount = operationalCount;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
