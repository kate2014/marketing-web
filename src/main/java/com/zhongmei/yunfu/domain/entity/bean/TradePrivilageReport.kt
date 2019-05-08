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
class TradePrivilageReport : CustomerEntity(), Serializable {

    /**
     * 储值单数
     */
    var tradeCount: BigDecimal? = null
    /**
     * 消费额度
     */
    val tradeAmount: BigDecimal? = null
    /**
     * 创建时间
     */
    var privilageName: String? = null
    /**
     * 优惠券Id
     */
    var promoId: Long? = null

    override fun toString(): String {
        return "CustomerReport{" +
                ", tradeCount=" + tradeCount +
                ", tradeAmount=" + tradeAmount +
                ", privilageName=" + privilageName +
                ", promoId=" + promoId +
                "}"
    }
}
