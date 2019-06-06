package com.zhongmei.yunfu.controller.model;

public class ReportMarketingModel {

    /**
     * 品牌标识
     */
    private Long brandIdenty;
    /**
     * 门店标识
     */
    private Long shopIdenty;

    private String startDate;

    private String endDate;

    private Integer rquestSource;

    private Integer privilegeType;

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
}
