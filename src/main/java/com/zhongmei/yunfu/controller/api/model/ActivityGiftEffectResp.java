package com.zhongmei.yunfu.controller.api.model;

import java.math.BigDecimal;

public class ActivityGiftEffectResp {
    /**
     * 礼品图片
     */
    public String imageUrl;
    /**
     * 礼品价格
     */
    public BigDecimal giftPrice;
    /**
     * 获取礼品需推荐成单数
     */
    public int orderCount;
    /**
     * 礼品Id
     */
    public String giftId;
    /**
     * 礼品名称
     */
    public String giftName;
    /**
     * 活动名称
     */
    public String activityName;
    /**
     * 礼品兑换情况
     */
    public String status;
    /**
     * 完成情况
     */
    public String finishStatus;
    /**
     * 完成推荐订单数
     */
    public int finishCount;
    /**
     * 活动Id
     */
    public Long activityId;
    /**
     * 门店编号
     */
    public Long shopIdenty;
    /**
     * 品牌编号
     */
    public Long brandIdenty;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getFinishCount() {
        return finishCount;
    }

    public void setFinishCount(int finishCount) {
        this.finishCount = finishCount;
    }

    public BigDecimal getGiftPrice() {
        return giftPrice;
    }

    public void setGiftPrice(BigDecimal giftPrice) {
        this.giftPrice = giftPrice;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
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

    public String getFinishStatus() {
        return finishStatus;
    }

    public void setFinishStatus(String finishStatus) {
        this.finishStatus = finishStatus;
    }
}
