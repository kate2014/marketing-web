package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 会员储值、储值消费记录表
 * </p>
 *
 * @author pigeon88
 * @since 2018-10-01
 */
@TableName("customer_stored")
class CustomerStoredEntity : BaseEntity() {

    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 会员编号
     */
    var customerId: Long? = null
    /**
     * 记录类型1储值、2消费、3退款
     */
    var recordType: Int? = null
    /**
     * 交易金额(充值/消费) 
     */
    var tradeAmount: BigDecimal? = null
    /**
     * 当前剩余余额
     */
    var residueBalance: BigDecimal? = null
    /**
     * 订单id
     */
    var tradeId: Long? = null
    /**
     * 支付itemId
     */
    var paymentItemId: Long? = null
    /**
     * 门店id
     */
    var shopIdenty: Long? = null
    /**
     * 品牌标识
     */
    var brandIdenty: Long? = null
    /**
     * 启用停用标识 : 1:启用;2:停用
     */
    var enabledFlag: Int? = null


    override fun toString(): String {
        return "CustomerStoredEntity{" +
        ", id=" + id +
        ", customerId=" + customerId +
        ", recordType=" + recordType +
        ", tradeAmount=" + tradeAmount +
        ", residueBalance=" + residueBalance +
        ", tradeId=" + tradeId +
        ", paymentItemId=" + paymentItemId +
        ", shopIdenty=" + shopIdenty +
        ", brandIdenty=" + brandIdenty +
        ", enabledFlag=" + enabledFlag +
        "}"
    }

    enum class RecordType(val value: Int) {
        RECHARGE(1),
        EXPENSE(2),
        REFUND(3);
    }
}
