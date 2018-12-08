package com.zhongmei.yunfu.controller.model;

public class ShareActionModel {

    /**
     * 分享类型：1：门店分享 2：新品分享 3：活动分享
     */
    private Integer shareType;

    private Long shopIdenty;

    private Long brandIdenty;

    private Long customerId;

    private String openId;

    /**
     * 关联ID, 如分享的是活动这该字段保存活动id,如分享的是新品，则该字段保存新品id
     */
    private Long linksId;

    public Integer getShareType() {
        return shareType;
    }

    public void setShareType(Integer shareType) {
        this.shareType = shareType;
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

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Long getLinksId() {
        return linksId;
    }

    public void setLinksId(Long linksId) {
        this.linksId = linksId;
    }
}
