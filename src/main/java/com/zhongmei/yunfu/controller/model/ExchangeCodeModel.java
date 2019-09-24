package com.zhongmei.yunfu.controller.model;

import java.math.BigDecimal;
import java.util.Date;

public class ExchangeCodeModel {

    /**
     * 自增主键
     */
    private Long id;
    /**
     * 兑换编码
     */
    private String exchangeCode;
    /**
     * 生成时间
     */
    private Date createTime;
    /**
     * 会员手机
     */
    private String mobile;
    /**
     * 会员Id
     */
    private Long customerId;
    /**
     * 门店id
     */
    private Long shopIdenty;
    /**
     * 品牌标识
     */
    private Long brandIdenty;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExchangeCode() {
        return exchangeCode;
    }

    public void setExchangeCode(String exchangeCode) {
        this.exchangeCode = exchangeCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
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
