package com.zhongmei.yunfu.controller.model;

import com.zhongmei.yunfu.controller.model.base.WebBaseModel;

import java.math.BigDecimal;
import java.util.Date;

public class CommissionSearchModel extends WebBaseModel {

    private String startDate;

    private String endDate;

    private String mobile;

    private String password;

    private Long id;

    private Long customerId;

    private String name;
    /**
     * 累计消费金额
     */
    private BigDecimal totalAmount;
    /**
     * 单次消费金额
     */
    private BigDecimal changeSalesAmount;
    /**
     * 提成比例
     */
    private BigDecimal commissionRatio;
    /**
     * 单次提成金额
     */
    private BigDecimal commissionAmount;
    /**
     * 合计提成金额
     */
    private BigDecimal totalCommission;
    /**
     * 可兑换提成金额
     */
    private BigDecimal canExchange;
    /**
     * 单次兑换提成金额
     */
    private BigDecimal exchangeAmount;
    /**
     * 业务类型 1：提成累加  2：兑换 3:扣减（退货时需扣减）
     */
    private Integer type;
    /**
     * 门店id
     */
    private Long shopIdenty;
    /**
     * 品牌标识
     */
    private Long brandIdenty;

    private Date serverCreateTime;

    /**
     * 提示信息
     */
    private String sendMsg;

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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getChangeSalesAmount() {
        return changeSalesAmount;
    }

    public void setChangeSalesAmount(BigDecimal changeSalesAmount) {
        this.changeSalesAmount = changeSalesAmount;
    }

    public BigDecimal getCommissionRatio() {
        return commissionRatio;
    }

    public void setCommissionRatio(BigDecimal commissionRatio) {
        this.commissionRatio = commissionRatio;
    }

    public BigDecimal getCommissionAmount() {
        return commissionAmount;
    }

    public void setCommissionAmount(BigDecimal commissionAmount) {
        this.commissionAmount = commissionAmount;
    }

    public BigDecimal getTotalCommission() {
        return totalCommission;
    }

    public void setTotalCommission(BigDecimal totalCommission) {
        this.totalCommission = totalCommission;
    }

    public BigDecimal getCanExchange() {
        return canExchange;
    }

    public void setCanExchange(BigDecimal canExchange) {
        this.canExchange = canExchange;
    }

    public BigDecimal getExchangeAmount() {
        return exchangeAmount;
    }

    public void setExchangeAmount(BigDecimal exchangeAmount) {
        this.exchangeAmount = exchangeAmount;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getServerCreateTime() {
        return serverCreateTime;
    }

    public void setServerCreateTime(Date serverCreateTime) {
        this.serverCreateTime = serverCreateTime;
    }

    public String getSendMsg() {
        return sendMsg;
    }

    public void setSendMsg(String sendMsg) {
        this.sendMsg = sendMsg;
    }
}
