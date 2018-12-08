package com.zhongmei.yunfu.domain.entity

import java.io.Serializable
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
class CustomerReport : CustomerEntity(), Serializable {

    /**
     * 会员数量
     */
    var count: Int? = null
    /**
     * 创建时间
     */
    var createDate: Date? = null

    override fun toString(): String {
        return "CustomerReport{" +
                ", count=" + count +
                ", createDate=" + createDate +
                "}"
    }
}
