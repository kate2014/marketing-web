package com.zhongmei.yunfu.domain.entity

import java.io.Serializable
import java.math.BigDecimal
import java.util.*

/**
 *
 *
 * 员工绩效报表
 *
 *
 * @author zhaos
 * @since 2018-09-21
 */
class UserSalaryReport : Serializable {

    /**
     * 会员名称
     */
    var userName: String? = null
    /**
     * 金额
     */
    var amount: BigDecimal? = null

    var count: BigDecimal? = null

    var dishName: String? = null

    var tradeDate: String? = null

    var businessType: Int? = null

    override fun toString(): String {
        return "CustomerReport{" +
                ", userName=" + userName +
                ", amount=" + amount +
                ", count=" + count +
                ", dishName=" + dishName +
                ", tradeDate=" + tradeDate +
                ", businessType=" + businessType +
                "}"
    }
}
