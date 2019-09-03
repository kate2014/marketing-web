package com.zhongmei.yunfu.controller.model;

import com.zhongmei.yunfu.controller.model.base.WebBaseModel;

import java.util.Date;

public class ReportMarketingModel extends WebBaseModel {

    /**
     * 品牌标识
     */
    private Long brandIdenty;
    /**
     * 门店标识
     */
    private Long shopIdenty;

    private Integer type;

    private Integer status;

    private Integer enabledFlag;

    private String startDate;

    private String endDate;

    private Integer rquestSource;

    private Integer privilegeType;

    private String validityPeriod;

    public Long getShopIdenty() {
        return shopIdenty;
    }

    public void setShopIdenty(Long shopIdenty) {
        this.shopIdenty = shopIdenty;
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

    public Long getBrandIdenty() {
        return brandIdenty;
    }

    public void setBrandIdenty(Long brandIdenty) {
        this.brandIdenty = brandIdenty;
    }

    public Integer getRquestSource() {
        return rquestSource;
    }

    public void setRquestSource(Integer rquestSource) {
        this.rquestSource = rquestSource;
    }

    public Integer getPrivilegeType() {
        return privilegeType;
    }

    public void setPrivilegeType(Integer privilegeType) {
        this.privilegeType = privilegeType;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(Integer enabledFlag) {
        this.enabledFlag = enabledFlag;
    }

    public String getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(String validityPeriod) {
        this.validityPeriod = validityPeriod;
    }
}
