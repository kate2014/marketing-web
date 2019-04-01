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
class PurchaseSaleReport : Serializable {

    var id: Long? = null

    var dishId: Long? = null

    var name: String? = null

    var residueTotal: BigDecimal? = null

    var dishQty: BigDecimal? = null

    var marketPrice: BigDecimal? = null

    var sourceName: String? = null

    var number: BigDecimal? = null

    var purchasePrice: BigDecimal? = null

    var totalPurchasePrice: BigDecimal? = null

    var type: Int? = null

    var saleTotal: BigDecimal? = null

    var salePrice: BigDecimal? = null

    var serverCreateTime: Date? =null

    override fun toString(): String {
        return "PurchaseSaleReport{" +
                ", id=" + id +
                ", dishId=" + dishId +
                ", name=" + name +
                ", residueTotal=" + residueTotal +
                ", dishQty=" + dishQty +
                ", marketPrice=" + marketPrice +
                ", sourceName=" + sourceName +
                ", number=" + number +
                ", purchasePrice=" + purchasePrice +
                ", totalPurchasePrice=" + totalPurchasePrice +
                ", type=" + type +
                ", saleTotal=" + saleTotal +
                ", salePrice=" + salePrice +
                ", serverCreateTime=" + serverCreateTime +
                "}"
    }
}
