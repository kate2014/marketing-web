package com.zhongmei.yunfu.domain.entity

import java.io.Serializable
import java.math.BigDecimal
import java.util.*

/**
 *
 *
 * 会员报表
 *
 *
 * @author zhaos
 * @since 2018-09-21
 */
class SalesReport : CustomerEntity(), Serializable {

    /**
     * 会员数量
     */
    var peopleCount: Int? = null
    /**
     * 消费额度
     */
    val salesAmount: BigDecimal? = null
    /**
     * 创建时间
     */
    var createDate: Date? = null

    override fun toString(): String {
        return "CustomerReport{" +
                ", peopleCount=" + peopleCount +
                ", salesAmount=" + salesAmount +
                ", createDate=" + createDate +
                "}"
    }
}
