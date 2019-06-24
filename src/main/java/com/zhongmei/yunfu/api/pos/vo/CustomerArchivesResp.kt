package com.zhongmei.yunfu.api.pos.vo

import com.zhongmei.yunfu.api.PosReq

class CustomerArchivesResp : PosReq() {

    var id: Long? = null
    var type: Int? = null
    var title: String? = null
    var content: String? = null
    var customerId: Long? = null

}