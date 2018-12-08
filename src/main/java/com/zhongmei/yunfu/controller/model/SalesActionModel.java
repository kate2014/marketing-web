package com.zhongmei.yunfu.controller.model;

public class SalesActionModel {

    /**
     * 交易订单Id
     */
    private Long tradeId;
    /**
     * 门店id
     */
    private Long shopIdenty;
    /**
     * 品牌标识
     */
    private Long brandIdenty;

    public Long getTradeId() {
        return tradeId;
    }

    public void setTradeId(Long tradeId) {
        this.tradeId = tradeId;
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
}
