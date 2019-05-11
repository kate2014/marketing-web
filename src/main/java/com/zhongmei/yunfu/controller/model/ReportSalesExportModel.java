package com.zhongmei.yunfu.controller.model;

import com.zhongmei.yunfu.controller.model.base.WebBaseModel;

import java.math.BigDecimal;

/**
 * 下单参：该服务只支持小程程序下单，而小程程序的拼团、秒杀和砍价只支持单商品，单服务的购买。
 */
public class ReportSalesExportModel extends WebBaseModel {

    /**
     * 交易类型
     */
    private String businessType;
    /**
     * 交易时间
     */
    private String tradeDate;
    /**
     * 交易类别
     */
    private String tradeType;
    /**
     * 交易金额
     */
    private String tradeAmount;
    /**
     * 交易通道
     */
    private String tradeSource;
    /**
     * 交易方式
     */
    private String tradeMode;
    /**
     * 交易状态
     */
    private String tradeState;

    private Long shopIdenty;

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(String tradeDate) {
        this.tradeDate = tradeDate;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(String tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public String getTradeSource() {
        return tradeSource;
    }

    public void setTradeSource(String tradeSource) {
        this.tradeSource = tradeSource;
    }

    public String getTradeMode() {
        return tradeMode;
    }

    public void setTradeMode(String tradeMode) {
        this.tradeMode = tradeMode;
    }

    public String getTradeState() {
        return tradeState;
    }

    public void setTradeState(String tradeState) {
        this.tradeState = tradeState;
    }

    public Long getShopIdenty() {
        return shopIdenty;
    }

    public void setShopIdenty(Long shopIdenty) {
        this.shopIdenty = shopIdenty;
    }
}
