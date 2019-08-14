package com.zhongmei.yunfu.controller.model;

import com.zhongmei.yunfu.controller.model.base.IShopIdenty;
import com.zhongmei.yunfu.controller.model.base.WebBaseModel;

/**
 * 活动查询列表
 */
public class ActivitySearchModel extends WebBaseModel implements IShopIdenty {
    private Long id;
    private Integer planState;
    private String name;
    private Long brandIdentity;
    private Long shopIdentity;
    /**
     * 浏览次数
     */
    private Integer scanNumber;
    /**
     * 分享次数
     */
    private Integer shareNumber;

    private String CustomerId;

    private String WXOpenId;

    private Long activityId;
    private Integer type;//1：浏览   2：分享
    private String customerName;
    private String customerPhone;
    private Integer operationalCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPlanState() {
        return planState;
    }

    public void setPlanState(Integer planState) {
        this.planState = planState;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getBrandIdenty() {
        return brandIdentity;
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

    public void setBrandIdenty(Long brandIdentity) {
        this.brandIdentity = brandIdentity;
    }

    public Long getShopIdenty() {
        return shopIdentity;
    }

    public void setShopIdenty(Long shopIdentity) {
        this.shopIdentity = shopIdentity;
    }

    public Integer getScanNumber() {
        return scanNumber;
    }

    public void setScanNumber(Integer scanNumber) {
        this.scanNumber = scanNumber;
    }

    public Integer getShareNumber() {
        return shareNumber;
    }

    public void setShareNumber(Integer shareNumber) {
        this.shareNumber = shareNumber;
    }

    public String getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(String customerId) {
        CustomerId = customerId;
    }

    public String getWXOpenId() {
        return WXOpenId;
    }

    public void setWXOpenId(String WXOpenId) {
        this.WXOpenId = WXOpenId;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public Integer getOperationalCount() {
        return operationalCount;
    }

    public void setOperationalCount(Integer operationalCount) {
        this.operationalCount = operationalCount;
    }
}
