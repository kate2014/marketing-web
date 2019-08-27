package com.zhongmei.yunfu.controller.api.model;

import java.nio.Buffer;

public class WxQRCodeReq {

    public Long activityId;
    public Long  recommendCustomerId;
    public String recommendOpenId;
    public Long brandIdenty;
    public Long shopIdenty;

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Long getRecommendCustomerId() {
        return recommendCustomerId;
    }

    public void setRecommendCustomerId(Long recommendCustomerId) {
        this.recommendCustomerId = recommendCustomerId;
    }

    public String getRecommendOpenId() {
        return recommendOpenId;
    }

    public void setRecommendOpenId(String recommendOpenId) {
        this.recommendOpenId = recommendOpenId;
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

}
