package com.zhongmei.yunfu.controller.api.model;

import com.zhongmei.yunfu.domain.entity.ActivitySalesEntity;
import com.zhongmei.yunfu.domain.entity.ActivitySalesGiftEntity;
import com.zhongmei.yunfu.domain.entity.OperationalRecordsEntity;

import java.util.List;

public class ActivitySalesReq {

    /**
     * 品牌id
     */
    private Long brandIdenty;
    /**
     * 门店id
     */
    private Long shopIdenty;

    private Long activityId;

    private String wxOpenId;

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

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public String getWxOpenId() {
        return wxOpenId;
    }

    public void setWxOpenId(String wxOpenId) {
        this.wxOpenId = wxOpenId;
    }
}
