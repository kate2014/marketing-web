package com.zhongmei.yunfu.api.internal.vo

import java.math.BigDecimal

class CustomerBalanceReq {
    var customerId: Long? = null
    var tradeId: Long? = null
    var paymentItemId: Long? = null
    var usefulAmount: BigDecimal? = null //交易金额
    var giveAmout: BigDecimal? = null //赠送金额
    var shopId: Long? = null
    var brandId: Long? = null
    var creatorId: Long? = null
    var creatorName: String? = null
}