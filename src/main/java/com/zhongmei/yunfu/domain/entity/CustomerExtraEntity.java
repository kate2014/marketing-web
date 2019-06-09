package com.zhongmei.yunfu.domain.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 会员扩展表
 * </p>
 *
 * @author pigeon88
 * @since 2019-06-05
 */
@TableName("customer_extra")
public class CustomerExtraEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "customer_id", type = IdType.INPUT)
    private Long customerId;
    /**
     * 储值总余额(包括赠送)
     */
    private BigDecimal storedAmount;
    /**
     * 储值赠送总金额
     */
    private BigDecimal storedGive;
    /**
     * 已使用余额
     */
    private BigDecimal storedUsed;
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

    @Override
    public String toString() {
        return "CustomerExtraEntity{" +
        ", customerId=" + customerId +
        ", storedAmount=" + storedAmount +
        ", storedGive=" + storedGive +
        ", storedUsed=" + storedUsed +
        ", storedPrivilegeType=" + storedPrivilegeType +
        ", storedPrivilegeValue=" + storedPrivilegeValue +
        "}";
    }
}
