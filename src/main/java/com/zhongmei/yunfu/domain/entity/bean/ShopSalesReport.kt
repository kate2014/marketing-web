package com.zhongmei.yunfu.domain.entity

import java.io.Serializable
import java.math.BigDecimal
import java.util.*

/**
 *
 *
 * 门店销售业绩
 *
 *
 * @author zhaos
 * @since 2018-09-21
 */
class ShopSalesReport{

    var shopIdenty: Long? = null

    var shopName: String? = null
    /**
     * 消费额度
     */
    val salesAmount: BigDecimal? = null

    val salesCount: BigDecimal? = null

    override fun toString(): String {
        return "CustomerReport{" +
                ", shopIdenty=" + shopIdenty +
                ", shopName=" + shopName +
                ", salesAmount=" + salesAmount +
                ", salesCount=" + salesCount +
                "}"
    }
}
