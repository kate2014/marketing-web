package com.zhongmei.yunfu.api.pos.vo

import com.zhongmei.yunfu.api.PosReq

class CustomerArchivesReq : PosReq() {

    var pageNo: Int = 0
    var pageSize: Int = 20
    var customerId: Long? = null
    var type: Int? = null
    var title: String? = null
    var content: String? = null
    var creatorId: Long? = null
    var creatorName: String? = null
    var archivesId: Long? = null

}