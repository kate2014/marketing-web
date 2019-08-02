package com.zhongmei.yunfu.domain.entity

import java.io.Serializable
import java.math.BigDecimal
import java.util.*

/**
 *
 *
 * 会员储值报表
 *
 *
 * @author zhaos
 * @since 2018-09-21
 */
class CustomerSaveReport : CustomerEntity(), Serializable {

    /**
     * 储值单数
     */
    var tradeCount: Int? = null
    /**
     * 交易总额
     */
    var tradeAmount: BigDecimal? = null
    /**
     * 消费额度
     */
    val salesAmount: BigDecimal? = null
    /**
     * 储值赠送金额
     */
    var giveAmount: BigDecimal? = null
    /**
     * 当前可用余额
     */
    var residueBalance: BigDecimal? = null
    /**
     * 交易类型
     */
    var recordype: Int? = null
    /**
     * 创建时间
     */
    var createDate: String? = null

    var customerName: String? = null

    override fun toString(): String {
        return "CustomerReport{" +
                ", tradeCount=" + tradeCount +
                ", salesAmount=" + salesAmount +
                ", giveAmount=" + giveAmount +
                ", residueBalance=" + residueBalance +
                ", createDate=" + createDate +
                ", customerName=" + customerName +
                "}"
    }
}
