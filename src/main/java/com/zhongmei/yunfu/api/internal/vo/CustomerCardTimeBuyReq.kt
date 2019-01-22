package com.zhongmei.yunfu.api.internal.vo

class CustomerCardTimeBuyReq {
    var customerId: Long? = null
    var tradeId: Long? = null
    var tradeUuid: String? = null
    var shopId: Long? = null
    var brandId: Long? = null
    var creatorId: Long? = null
    var creatorName: String? = null
    var dishs: List<Dish>? = null

    class Dish {
        var dishId: Long? = null
        var dishName: String? = null
        /*var groupId: Long? = null
       var groupName: String? = null*/
        var tradeCount: Int? = null
        var creatorId: Long? = null
        var creatorName: String? = null
        var type: Int? = null
        var batchNo: String? = null //这个数据是json
    }
}