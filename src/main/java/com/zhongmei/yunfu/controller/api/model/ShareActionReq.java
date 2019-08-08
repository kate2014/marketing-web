package com.zhongmei.yunfu.controller.api.model;

public class ShareActionReq {

    /**
     * 分享活动类型1：门店分享 2：新品分享 3：活动分享 4：拼团 5：秒杀 6：砍价  7：特价活动
     */
    private Integer shareType;

    private Long shopIdenty;

    private Long brandIdenty;

    private Long customerId;

    private String customerPhone;

    private String customerName;

    private String wxOpenId;

    private String wxPhoto;

    private String wxName;

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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getWxOpenId() {
        return wxOpenId;
    }

    public void setWxOpenId(String wxOpenId) {
        this.wxOpenId = wxOpenId;
    }

    public String getWxPhoto() {
        return wxPhoto;
    }

    public void setWxPhoto(String wxPhoto) {
        this.wxPhoto = wxPhoto;
    }

    public String getWxName() {
        return wxName;
    }

    public void setWxName(String wxName) {
        this.wxName = wxName;
    }

    public Long getLinksId() {
        return linksId;
    }

    public void setLinksId(Long linksId) {
        this.linksId = linksId;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }
}
