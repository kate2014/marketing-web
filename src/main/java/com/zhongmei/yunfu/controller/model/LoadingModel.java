package com.zhongmei.yunfu.controller.model;

public class LoadingModel {

    Long brandIdenty;

    Long shopIdenty;

    Integer requestUrlType;

    private String requestUrl;

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
}
