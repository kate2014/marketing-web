package com.zhongmei.yunfu.api.pos.vo

import com.zhongmei.yunfu.api.PosReq

class CustomerSearchReq : PosReq() {

    var pageNo: Int = 0
    var pageSize: Int = 10
    var nameOrMobile: String? = null

}