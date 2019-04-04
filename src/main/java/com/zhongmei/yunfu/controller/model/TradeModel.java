package com.zhongmei.yunfu.controller.model;

import com.zhongmei.yunfu.controller.model.base.WebBaseModel;

import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 下单参：该服务只支持小程程序下单，而小程程序的拼团、秒杀和砍价只支持单商品，单服务的购买。
 */
public class TradeModel extends WebBaseModel {

    /**
     * 用户微信openId
     */
    private String wxOpenId;

    /**
     * 关联会员发起的拼团活动id
     */
    private Long relationId;

    private Long tradeId;

    private Long relationTradeId;

    private String tradeNo;

    private Long paymentItemId;
    /**
     * 购买的活动id
     */
    private Long marketingId;
    /**
     * 够买的活动名称
     */
    private String marketingName;
    /**
     * 类型 1 拼团、2 砍价、 3 秒杀
     */
    private Integer type;

    private Long customerId;
    /**
     * 顾客电话
     */
    private String customerPhone;
    /**
     * 顾客姓名
     */
    private String customerName;

    private Integer customerType;
    /**
     * 顾客性别:
     * 1男,0女,-1未知
     */
    private Integer customerSex;
    /**
     * 商品ID
     */
    private String dishId;
    /**
     * 商品名称
     */
    private String dishName;
    /**
     * 购买的服务价格
     */
    private BigDecimal price;
    /**
     * 购买的服务数量
     */
    private BigDecimal quantity;

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

    private Integer businessType;

    private Integer tradeType;

    private Integer source;

    private Integer tradeStatus;

    private Integer tradePayStatus;
    /**
     * 设备标识
     */
    private String deviceIdenty;

    private Long creatorId;

    private String creatorName;

    private Long dishShopId;

    private String tradeUser;

    public String getWxOpenId() {
        return wxOpenId;
    }

    public void setWxOpenId(String wxOpenId) {
        this.wxOpenId = wxOpenId;
    }

    public Long getTradeId() {
        return tradeId;
    }

    public void setTradeId(Long tradeId) {
        this.tradeId = tradeId;
    }

    public Long getMarketingId() {
        return marketingId;
    }

    public void setMarketingId(Long marketingId) {
        this.marketingId = marketingId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getDishId() {
        return dishId;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getCustomerSex() {
        return customerSex;
    }

    public void setCustomerSex(Integer customerSex) {
        this.customerSex = customerSex;
    }

    public void setDishId(String dishId) {
        this.dishId = dishId;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
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

    public Integer getType() {
        return type;
    }

    public Long getRelationId() {
        return relationId;
    }

    public void setRelationId(Long relationId) {
        this.relationId = relationId;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public Integer getTradeType() {
        return tradeType;
    }

    public void setTradeType(Integer tradeType) {
        this.tradeType = tradeType;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Integer getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(Integer tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public String getDeviceIdenty() {
        return deviceIdenty;
    }

    public void setDeviceIdenty(String deviceIdenty) {
        this.deviceIdenty = deviceIdenty;
    }

    public Integer getTradePayStatus() {
        return tradePayStatus;
    }

    public void setTradePayStatus(Integer tradePayStatus) {
        this.tradePayStatus = tradePayStatus;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public Long getPaymentItemId() {
        return paymentItemId;
    }

    public void setPaymentItemId(Long paymentItemId) {
        this.paymentItemId = paymentItemId;
    }

    public Long getRelationTradeId() {
        return relationTradeId;
    }

    public void setRelationTradeId(Long relationTradeId) {
        this.relationTradeId = relationTradeId;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getMarketingName() {
        return marketingName;
    }

    public void setMarketingName(String marketingName) {
        this.marketingName = marketingName;
    }

    public Long getDishShopId() {
        return dishShopId;
    }

    public void setDishShopId(Long dishShopId) {
        this.dishShopId = dishShopId;
    }

    public String getTradeUser() {
        return tradeUser;
    }

    public void setTradeUser(String tradeUser) {
        this.tradeUser = tradeUser;
    }

    public Integer getCustomerType() {
        return customerType;
    }

    public void setCustomerType(Integer customerType) {
        this.customerType = customerType;
    }
}
