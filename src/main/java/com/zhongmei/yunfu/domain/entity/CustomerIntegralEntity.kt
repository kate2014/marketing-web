package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.annotations.TableId
import com.baomidou.mybatisplus.annotations.TableName
import com.baomidou.mybatisplus.enums.IdType
import com.zhongmei.yunfu.domain.entity.base.BaseEntity

/**
 * <p>
 * 会员积分表
 * </p>
 *
 * @author yangyp
 * @since 2018-10-01
 */
@TableName("customer_integral")
class CustomerIntegralEntity : BaseEntity() {

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
     * 记录类型1储值、2消费
     */
    var recordType: Int? = null
    /**
     * 当前可用积分
     */
    var currentIntegral: Int? = null
    /**
     * 交易积分(加积分/减积分)
     */
    var tradeIntegral: Int? = null
    /**
     * 交易理由
     */
    var tradeReason: String? = null
    /**
     * 当前剩余积分
     */
    var residueIntegral: Int? = null
    /**
     * 订单id
     */
    var tradeId: Long? = null
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
        return "CustomerIntegralEntity{" +
                ", id=" + id +
                ", customerId=" + customerId +
                ", recordType=" + recordType +
                ", currentIntegral=" + currentIntegral +
                ", tradeIntegral=" + tradeIntegral +
                ", residueIntegral=" + residueIntegral +
                ", tradeId=" + tradeId +
                ", shopIdenty=" + shopIdenty +
                ", brandIdenty=" + brandIdenty +
                ", enabledFlag=" + enabledFlag +
                "}"
    }
}
