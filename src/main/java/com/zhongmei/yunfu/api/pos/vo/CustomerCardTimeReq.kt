package com.zhongmei.yunfu.api.pos.vo

import com.zhongmei.yunfu.api.PosReq

class CustomerCardTimeReq : PosReq() {
    /*var shopId: Long? = null
    var brandId: Long? = null*/
    var pageNo: Int = 0
    var pageSize: Int = 10
    var customerId: Long? = null
}