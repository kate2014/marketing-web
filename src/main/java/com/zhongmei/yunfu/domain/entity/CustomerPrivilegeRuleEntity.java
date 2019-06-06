package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 会员权益设置表
 * </p>
 *
 * @author pigeon88
 * @since 2019-06-04
 */
@TableName("customer_privilege_rule")
public class CustomerPrivilegeRuleEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 会员等级
     */
    private Integer customerLevel;
    /**
     * 权益类型： 1：会员等级折扣，2：会员等级让价，3：储值支付折扣，4：储值支付让价
     */
    private Integer privilegeType;
    /**
     * 储值金额，用于记录储值满多少给予对应的优惠
     */
    private BigDecimal saveAmount;
    /**
     * 权益额度值
     */
    private BigDecimal privilageValue;
    /**
     * 满额限制
     */
    private BigDecimal fullAmount;
    /**
     * 是否需要储值支付 0：不需要  1：需要
     */
    private Integer isNeedSavePayment;
    /**
     * 商户id
     */
    private Long shopIdenty;
    /**
     * 品牌id
     */
    private Long brandIdenty;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCustomerLevel() {
        return customerLevel;
    }

    public void setCustomerLevel(Integer customerLevel) {
        this.customerLevel = customerLevel;
    }

    public Integer getPrivilegeType() {
        return privilegeType;
    }

    public void setPrivilegeType(Integer privilegeType) {
        this.privilegeType = privilegeType;
    }

    public BigDecimal getPrivilageValue() {
        return privilageValue;
    }

    public void setPrivilageValue(BigDecimal privilageValue) {
        this.privilageValue = privilageValue;
    }

    public BigDecimal getFullAmount() {
        return fullAmount;
    }

    public void setFullAmount(BigDecimal fullAmount) {
        this.fullAmount = fullAmount;
    }

    public Integer getIsNeedSavePayment() {
        return isNeedSavePayment;
    }

    public void setIsNeedSavePayment(Integer isNeedSavePayment) {
        this.isNeedSavePayment = isNeedSavePayment;
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

    public BigDecimal getSaveAmount() {
        return saveAmount;
    }

    public void setSaveAmount(BigDecimal saveAmount) {
        this.saveAmount = saveAmount;
    }

    @Override
    public String toString() {
        return "CustomerPrivilegeRuleEntity{" +
        ", id=" + id +
        ", customerLevel=" + customerLevel +
        ", privilegeType=" + privilegeType +
        ", saveAmount=" + saveAmount +
        ", privilageValue=" + privilageValue +
        ", fullAmount=" + fullAmount +
        ", isNeedSavePayment=" + isNeedSavePayment +
        ", shopIdenty=" + shopIdenty +
        ", brandIdenty=" + brandIdenty +
        "}";
    }
}
