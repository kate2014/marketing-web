package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;
import java.util.*

/**
 * <p>
 * 用户推广提成兑换验证码
 * </p>
 *
 */
@TableName("exchange_code")
class ExchangeCodeEntity {

    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 兑换编码
     */
    var exchangeCode: String? = null
    /**
     * 生成时间
     */
    var createTime: Date? = null
    /**
     * 会员手机
     */
    var mobile: String? = null
    /**
     * 会员Id
     */
    var customerId: Long? = null
    /**
     * 门店id
     */
    var shopIdenty: Long? = null
    /**
     * 品牌标识
     */
    var brandIdenty: Long? = null
    /**
     * 是否已使用 1:未使用，2:已使用，用于避免一个码多次兑现
     */
    var statusFlag: Int? = null


    override fun toString(): String {
        return "ExchangeCodeEntity{" +
                ", id=" + id +
                ", exchangeCode=" + exchangeCode +
                ", createTime=" + createTime +
                ", mobile=" + mobile +
                ", customerId=" + customerId +
                ", shopIdenty=" + shopIdenty +
                ", brandIdenty=" + brandIdenty +
                ", statusFlag=" + statusFlag +
                "}"
    }
}
