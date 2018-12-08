package com.zhongmei.yunfu.controller.model;

import java.math.BigDecimal;
import java.util.Date;

public class ExpandedMarketingModel {

    private Long id;
    /**
     * 方案名称
     */
    private String planName;
    /**
     * 全员推广营销活动状态： 1：投放中  2：停止
     */
    private Integer expandedState;
    /**
     * 开始时间
     */
    private Date startDate;
    /**
     * 结束时间
     */
    private String endDate;
    /**
     * 简介
     */
    private String profile;
    /**
     * 一级推广提成比例
     */
    private BigDecimal firstLevelDiscount;
    /**
     * 二季推广提成比例
     */
    private BigDecimal secondClassDicount;
    /**
     * 提成方式，目前写上为 1：按比例提成
     */
    private Integer dicountType;
    /**
     * 推广活动结束，富文本信息
     */
    private String description;
    /**
     * 门店id
     */
    private Long shopIdenty;
    /**
     * 品牌标识
     */
    private Long brandIdenty;
    /**
     * 状态标识1:启用 2:禁用
     */
    private Integer statusFlag;
    /**
     * 创建者id
     */
    private Long creatorId;
    /**
     * 创建者名称
     */
    private String creatorName;
    /**
     * 更新者id
     */
    private Long updatorId;
    /**
     * 最后修改者姓名
     */
    private String updatorName;
    /**
     * 服务器创建时间
     */
    private Date serverCreateTime;
    /**
     * 服务器更新时间
     */
    private Date serverUpdateTime;

    private String successOrfail;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getExpandedState() {
        return expandedState;
    }

    public void setExpandedState(Integer expandedState) {
        this.expandedState = expandedState;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public BigDecimal getFirstLevelDiscount() {
        return firstLevelDiscount;
    }

    public void setFirstLevelDiscount(BigDecimal firstLevelDiscount) {
        this.firstLevelDiscount = firstLevelDiscount;
    }

    public BigDecimal getSecondClassDicount() {
        return secondClassDicount;
    }

    public void setSecondClassDicount(BigDecimal secondClassDicount) {
        this.secondClassDicount = secondClassDicount;
    }

    public Integer getDicountType() {
        return dicountType;
    }

    public void setDicountType(Integer dicountType) {
        this.dicountType = dicountType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Integer getStatusFlag() {
        return statusFlag;
    }

    public void setStatusFlag(Integer statusFlag) {
        this.statusFlag = statusFlag;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Long getUpdatorId() {
        return updatorId;
    }

    public void setUpdatorId(Long updatorId) {
        this.updatorId = updatorId;
    }

    public String getUpdatorName() {
        return updatorName;
    }

    public void setUpdatorName(String updatorName) {
        this.updatorName = updatorName;
    }

    public Date getServerCreateTime() {
        return serverCreateTime;
    }

    public void setServerCreateTime(Date serverCreateTime) {
        this.serverCreateTime = serverCreateTime;
    }

    public Date getServerUpdateTime() {
        return serverUpdateTime;
    }

    public void setServerUpdateTime(Date serverUpdateTime) {
        this.serverUpdateTime = serverUpdateTime;
    }

    public String getSuccessOrfail() {
        return successOrfail;
    }

    public void setSuccessOrfail(String successOrfail) {
        this.successOrfail = successOrfail;
    }
}
