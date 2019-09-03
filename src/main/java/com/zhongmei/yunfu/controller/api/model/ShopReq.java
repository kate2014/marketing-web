package com.zhongmei.yunfu.controller.api.model;

public class ShopReq {

    /**
     * 品牌id
     */
    private Long brandIdenty;
    /**
     * 门店id
     */
    private Long shopIdenty;


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
