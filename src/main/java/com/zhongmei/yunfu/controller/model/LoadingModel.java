package com.zhongmei.yunfu.controller.model;

public class LoadingModel {

    private Long brandIdenty;

    private Long shopIdenty;

    private Integer requestUrlType;

    private String requestUrl;

    private Integer rquestSource;//来源：1 web商家后台 2：POS端

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

    public Integer getRequestUrlType() {
        return requestUrlType;
    }

    public void setRequestUrlType(Integer requestUrlType) {
        this.requestUrlType = requestUrlType;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public Integer getRquestSource() {
        return rquestSource;
    }

    public void setRquestSource(Integer rquestSource) {
        this.rquestSource = rquestSource;
    }
}
