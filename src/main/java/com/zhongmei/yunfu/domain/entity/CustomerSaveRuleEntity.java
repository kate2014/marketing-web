package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 会员储值规则表
 * </p>
 *
 * @author pigeon88
 * @since 2019-06-04
 */
@TableName("customer_save_rule")
public class CustomerSaveRuleEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 储值金额
     */
    private BigDecimal storedValue;
    /**
     * 赠送金额
     */
    private BigDecimal giveValue;
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

    public BigDecimal getStoredValue() {
        return storedValue;
    }

    public void setStoredValue(BigDecimal storedValue) {
        this.storedValue = storedValue;
    }

    public BigDecimal getGiveValue() {
        return giveValue;
    }

    public void setGiveValue(BigDecimal giveValue) {
        this.giveValue = giveValue;
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

    @Override
    public String toString() {
        return "CustomerSaveRuleEntity{" +
        ", id=" + id +
        ", storedValue=" + storedValue +
        ", giveValue=" + giveValue +
        ", shopIdenty=" + shopIdenty +
        ", brandIdenty=" + brandIdenty +
        "}";
    }
}
