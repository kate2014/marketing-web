package com.zhongmei.yunfu.api.pos.vo

import com.zhongmei.yunfu.api.PosReq
import java.util.*

class TaskRemindReq : PosReq() {

    var pageNo: Int = 0
    var pageSize: Int = 20
    var creatorId: Long? = null
    var creatorName: String? = null
    var title: String? = null
    var content: String? = null
    var remindTime: Date? = null
    var type: Int? = null
    var status: Int? = null
    var customerDocId: Long? = null
    var taskResult: String? = null
    var customerId: Long? = null
    var customerName: String? = null
    var customerMobile: String? = null
    var userId: Long? = null
    var userName: String? = null
    var shopIdenty: Long? = null
    var brandIdenty: Long? = null

}