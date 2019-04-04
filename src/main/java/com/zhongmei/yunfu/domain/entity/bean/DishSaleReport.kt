package com.zhongmei.yunfu.domain.entity

import java.io.Serializable
import java.math.BigDecimal
import java.util.*

/**
 *
 *
 * 商品销售报表
 *
 *
 * @author zhaos
 * @since 2018-09-21
 */
class DishSaleReport : Serializable {

    var id: Long? = null

    var dishId: Long? = null

    var name: String? = null

    var number: BigDecimal? = null

    var actualAmount: BigDecimal? = null

    var type: String? = null

    var status: BigDecimal? = null

    var serverCreateTime: String? =null

    var customerName: String? = null

    var tradeUser: String? = null

    var tradeId: Long? = null

    var tradeItemId: Long? = null

    var tradeNo: String? = null

    override fun toString(): String {
        return "PurchaseSaleReport{" +
                ", id=" + id +
                ", dishId=" + dishId +
                ", name=" + name +
                ", number=" + number +
                ", actualAmount=" + actualAmount +
                ", type=" + type +
                ", status=" + status +
                ", serverCreateTime=" + serverCreateTime +
                ", customerName=" + customerName +
                ", tradeUser=" + tradeUser +
                ", tradeId=" + tradeId +
                ", tradeItemId=" + tradeItemId +
                ", tradeNo=" + tradeNo +
                "}"
    }
}
