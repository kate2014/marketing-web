package com.zhongmei.yunfu.controller.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

import java.math.BigDecimal;

/**
 * <p>
 * 会员扩展表
 * </p>
 *
 */
public class CustomerExtraModel {

    private Long customerId;
    /**
     * 储值总余额(包括赠送)
     */
    private BigDecimal storedAmount;
    /**
     * 储值本金
     */
    private BigDecimal saveBaseAmount;
    /**
     * 储值赠送总金额
     */
    private BigDecimal storedGive;
    /**
     * 已使用余额
     */
    private BigDecimal storedUsed;
    /**
     * 储值余额
     */
    private BigDecimal storedBalance;
    /**
     * 是否做储值支付验证
     */
    private Integer storedPaymentCheck;
    /**
     * 储值支付优惠类型
     */
    private Integer storedPrivilegeType;
    /**
     * 储值支付优惠值
     */
    private BigDecimal storedPrivilegeValue;
    /**
     * 储值消费满额限制
     */
    private BigDecimal storedFullAmount;

    private Long shopIdenty;

    private String shopName;
    /**
     * 品牌标识 : 品牌标识
     */
    private Long brandIdenty;


    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getStoredAmount() {
        return storedAmount;
    }

    public void setStoredAmount(BigDecimal storedAmount) {
        this.storedAmount = storedAmount;
    }

    public BigDecimal getStoredGive() {
        return storedGive;
    }

    public void setStoredGive(BigDecimal storedGive) {
        this.storedGive = storedGive;
    }

    public BigDecimal getStoredUsed() {
        return storedUsed;
    }

    public void setStoredUsed(BigDecimal storedUsed) {
        this.storedUsed = storedUsed;
    }

    public BigDecimal getStoredBalance() {
        return storedBalance;
    }

    public void setStoredBalance(BigDecimal storedBalance) {
        this.storedBalance = storedBalance;
    }

    public Integer getStoredPaymentCheck() {
        return storedPaymentCheck;
    }

    public void setStoredPaymentCheck(Integer storedPaymentCheck) {
        this.storedPaymentCheck = storedPaymentCheck;
    }

    public Integer getStoredPrivilegeType() {
        return storedPrivilegeType;
    }

    public void setStoredPrivilegeType(Integer storedPrivilegeType) {
        this.storedPrivilegeType = storedPrivilegeType;
    }

    public BigDecimal getStoredPrivilegeValue() {
        return storedPrivilegeValue;
    }

    public void setStoredPrivilegeValue(BigDecimal storedPrivilegeValue) {
        this.storedPrivilegeValue = storedPrivilegeValue;
    }

    public BigDecimal getStoredFullAmount() {
        return storedFullAmount;
    }

    public void setStoredFullAmount(BigDecimal storedFullAmount) {
        this.storedFullAmount = storedFullAmount;
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

    public BigDecimal getSaveBaseAmount() {
        return saveBaseAmount;
    }

    public void setSaveBaseAmount(BigDecimal saveBaseAmount) {
        this.saveBaseAmount = saveBaseAmount;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    @Override
    public String toString() {
        return "CustomerExtraEntity{" +
                ", customerId=" + customerId +
                ", storedAmount=" + storedAmount +
                ", saveBaseAmount=" + saveBaseAmount +
                ", shopName=" + shopName +
                ", storedGive=" + storedGive +
                ", storedUsed=" + storedUsed +
                ", storedPrivilegeType=" + storedPrivilegeType +
                ", storedPrivilegeValue=" + storedPrivilegeValue +
                ", storedFullAmount=" + storedFullAmount +
                "}";
    }
}
