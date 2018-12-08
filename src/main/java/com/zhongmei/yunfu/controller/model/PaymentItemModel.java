package com.zhongmei.yunfu.controller.model;

import java.math.BigDecimal;

public class PaymentItemModel {
    /**
     * 支付总额
     */
    BigDecimal totalAmount;
    /**
     * 支付笔数
     */
    Long count;
    /**
     * 支付方式名称
     */
    String payModeName;
    /**
     * 支付方式Id
     */
    Integer payModeId;
    /**
     * 品牌标识
     */
    private Long brandIdenty;
    /**
     * 门店标识
     */
    private Long shopIdenty;

    private String startDate;

    private String endDate;

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getPayModeName() {
        return payModeName;
    }

    public void setPayModeName(String payModeName) {
        this.payModeName = payModeName;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getPayModeId() {
        return payModeId;
    }

    public void setPayModeId(Integer payModeId) {
        this.payModeId = payModeId;
    }
}
