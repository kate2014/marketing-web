package com.zhongmei.yunfu.controller.model;

import java.math.BigDecimal;
import java.util.Date;

public class ExpandedCommissionModel {

    private Long id;
    private Long customerId;

    private String wxOpenId;
    /**
     * 累计提成金额
     */
    private BigDecimal commissionAmount;
    /**
     * 可兑换提成金额
     */
    private BigDecimal canExchange;
    /**
     * 变动提成金额
     */
    private BigDecimal changeAmount;
    /**
     * 兑换金额
     */
    private BigDecimal exchangeAmount;
    /**
     * 兑换时间
     */
    private Date exchangeDate;
    /**
     * 业务类型 1：提成累加  2：兑换
     */
    private Integer type;
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
    /**
     * 状态标识1:启用 2:禁用
     */
    private Integer statusFlag;
    /**
     * 服务器创建时间
     */
    private Date serverCreateTime;
    /**
     * 服务器更新时间
     */
    private Date serverUpdateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getWxOpenId() {
        return wxOpenId;
    }

    public void setWxOpenId(String wxOpenId) {
        this.wxOpenId = wxOpenId;
    }

    public BigDecimal getCommissionAmount() {
        return commissionAmount;
    }

    public void setCommissionAmount(BigDecimal commissionAmount) {
        this.commissionAmount = commissionAmount;
    }

    public BigDecimal getCanExchange() {
        return canExchange;
    }

    public void setCanExchange(BigDecimal canExchange) {
        this.canExchange = canExchange;
    }

    public BigDecimal getChangeAmount() {
        return changeAmount;
    }

    public void setChangeAmount(BigDecimal changeAmount) {
        this.changeAmount = changeAmount;
    }

    public Date getExchangeDate() {
        return exchangeDate;
    }

    public void setExchangeDate(Date exchangeDate) {
        this.exchangeDate = exchangeDate;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public Integer getStatusFlag() {
        return statusFlag;
    }

    public void setStatusFlag(Integer statusFlag) {
        this.statusFlag = statusFlag;
    }

    public Date getServerCreateTime() {
        return serverCreateTime;
    }

    public void setServerCreateTime(Date serverCreateTime) {
        this.serverCreateTime = serverCreateTime;
    }

    public Date getServerUpdateTime() {
        return serverUpdateTime;
    }

    public void setServerUpdateTime(Date serverUpdateTime) {
        this.serverUpdateTime = serverUpdateTime;
    }

    public Long getTradeId() {
        return tradeId;
    }

    public void setTradeId(Long tradeId) {
        this.tradeId = tradeId;
    }

    public BigDecimal getExchangeAmount() {
        return exchangeAmount;
    }

    public void setExchangeAmount(BigDecimal exchangeAmount) {
        this.exchangeAmount = exchangeAmount;
    }
}
