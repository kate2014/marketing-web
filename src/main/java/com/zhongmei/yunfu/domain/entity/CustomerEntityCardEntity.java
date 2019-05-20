package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 
 * </p>
 *
 * @author pigeon88
 * @since 2019-04-29
 */
@TableName("customer_entity_card")
public class CustomerEntityCardEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long customerId;
    /**
     * 卡号
     */
    private String cardNo;

    /**
     * 如果归属门店，则为门店id；如果归属品牌，则为品牌id.
     新的权限体系下，全部为品牌id
     就是登录标示!!仅登录使用
     */
    private Long shopIdenty;
    /**
     * 品牌标识 : 品牌标识
     */
    private Long brandIdenty;


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

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
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
        return "CustomerEntityCardEntity{" +
        ", id=" + id +
        ", customerId=" + customerId +
        ", cardNo=" + cardNo +
        "}";
    }
}
