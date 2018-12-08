package com.zhongmei.yunfu.api.pos.vo

class CustomerIntegralTradeReq {
    var shopId: Long? = null
    var brandId: Long? = null
    var creatorId: Long? = null
    var creatorName: String? = null
    var customerId: Long? = null
    var tradeId: Long? = null
    var tradeUuid: String? = null
    var tradeIntegral: Int? = null //(反核销的时候这个值可以不传)
}