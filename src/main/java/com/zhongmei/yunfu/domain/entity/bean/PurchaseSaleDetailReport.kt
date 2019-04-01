package com.zhongmei.yunfu.domain.entity

import java.io.Serializable
import java.math.BigDecimal
import java.util.*

/**
 *
 *
 * 进销存
 *
 *
 * @author zhaos
 * @since 2018-09-21
 */
class PurchaseSaleDetailReport : Serializable {

    var server_create_time: Date? = null

    var qty: BigDecimal? = null

    var dish_name: String? = null

    var total_price: BigDecimal? = null

    var source_name: String? = null

    var type: Int? = null

    var price: BigDecimal? = null

    var trade_type: Int? = null

    override fun toString(): String {
        return "PurchaseSaleDetailReport{" +
                ", server_create_time=" + server_create_time +
                ", qty=" + qty +
                ", dish_name=" + dish_name +
                ", total_price=" + total_price +
                ", source_name=" + source_name +
                ", type=" + type +
                ", price=" + price +
                ", trade_type=" + trade_type +
                "}"
    }
}
