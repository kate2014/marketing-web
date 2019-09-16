package com.zhongmei.yunfu.controller.api.model;

public class ShopResp {

    /**
     * 品牌id
     */
    private Long brandIdenty;
    /**
     * 门店id
     */
    private Long shopIdenty;
    /**
     * 启动图片
     */
    private String startPicture;
    /**
     * 是否打开授权
     */
    private String openAuthorization;

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

    public String getStartPicture() {
        return startPicture;
    }

    public void setStartPicture(String startPicture) {
        this.startPicture = startPicture;
    }

    public String getOpenAuthorization() {
        return openAuthorization;
    }

    public void setOpenAuthorization(String openAuthorization) {
        this.openAuthorization = openAuthorization;
    }
}
