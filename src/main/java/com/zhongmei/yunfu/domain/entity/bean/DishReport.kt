package com.zhongmei.yunfu.domain.entity

import java.io.Serializable
import java.math.BigDecimal

/**
 *
 *
 * 商品销售情况
 *
 *
 * @author zhaos
 * @since 2018-09-21
 */
class DishReport : CustomerEntity(), Serializable {

    /**
     * 销售数量
     */
    var salseCount: Long? = null
    /**
     * 销售额度
     */
    val salesAmount: BigDecimal? = null
    /**
     * 商品名称
     */
    val dishName: String? = null

    val price: BigDecimal? = null

    override fun toString(): String {
        return "DishReport{" +
                ", salseCount=" + salseCount +
                ", salesAmount=" + salesAmount +
                ", dishName=" + dishName +
                ", price=" + price +
                "}"
    }
}
