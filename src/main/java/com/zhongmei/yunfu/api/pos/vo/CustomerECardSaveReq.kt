package com.zhongmei.yunfu.api.pos.vo

import com.zhongmei.yunfu.api.PosReq

class CustomerECardSaveReq : PosReq() {

    var customerId: Long? = null
    var userId: Long? = null
    var userName: String? = null
    var cardNo: String? = null
}
