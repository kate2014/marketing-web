package com.zhongmei.yunfu.controller.model;

public class SalaryModel {

    private String startDate;

    private String endDate;

    private String brandIdenty;

    private String shopIdenty;

    private String creatorId;

    private String creatorName;

    private Long userId;

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

    public String getBrandIdenty() {
        return brandIdenty;
    }

    public void setBrandIdenty(String brandIdenty) {
        this.brandIdenty = brandIdenty;
    }

    public String getShopIdenty() {
        return shopIdenty;
    }

    public void setShopIdenty(String shopIdenty) {
        this.shopIdenty = shopIdenty;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
