package com.zhongmei.yunfu.api.pos.vo

import com.zhongmei.yunfu.api.PosReq

class CustomerStoredBalanceReq : PosReq() {

    var pageNo: Int = 0
    var pageSize: Int = 10
    var customerId: Long? = null

}