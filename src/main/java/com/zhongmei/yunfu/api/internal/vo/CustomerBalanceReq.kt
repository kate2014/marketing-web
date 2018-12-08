package com.zhongmei.yunfu.api.internal.vo

import java.math.BigDecimal

class CustomerBalanceReq {
    var customerId: Long? = null
    var tradeId: Long? = null
    var paymentItemId: Long? = null
    var usefulAmount: BigDecimal? = null
    var shopId: Long? = null
    var brandId: Long? = null
    var creatorId: Long? = null
    var creatorName: String? = null
}