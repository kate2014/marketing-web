package com.zhongmei.yunfu.controller.model;

import com.zhongmei.yunfu.controller.model.base.WebBaseModel;

public class AuthUserModel extends WebBaseModel {

    /**
     * 品牌标识
     */
    private Long brandIdenty;
    /**
     * 门店标识
     */
    private Long shopIdenty;

    private String name;

    private Integer jobEmployeeType;

    private String jobNumber;

    private Long creatorId;

    private String creatorName;

    private String startDate;

    private String endDate;

    private Integer rquestSource;
    /**
     * 0: 品牌员工  1：门店员工
     */
    private Integer opType;
    /**
     * 账号归属：1：品牌，2：门店
     */
    private Integer assignedGroup;

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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getRquestSource() {
        return rquestSource;
    }

    public void setRquestSource(Integer rquestSource) {
        this.rquestSource = rquestSource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getJobEmployeeType() {
        return jobEmployeeType;
    }

    public void setJobEmployeeType(Integer jobEmployeeType) {
        this.jobEmployeeType = jobEmployeeType;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public Integer getOpType() {
        return opType;
    }

    public void setOpType(Integer opType) {
        this.opType = opType;
    }

    public Integer getAssignedGroup() {
        return assignedGroup;
    }

    public void setAssignedGroup(Integer assignedGroup) {
        this.assignedGroup = assignedGroup;
    }
}
