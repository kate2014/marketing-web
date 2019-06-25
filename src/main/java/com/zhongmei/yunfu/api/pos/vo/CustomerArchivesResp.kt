package com.zhongmei.yunfu.api.pos.vo

import com.zhongmei.yunfu.api.PosReq
import com.zhongmei.yunfu.domain.entity.TaskRemindEntity

class CustomerArchivesResp {

    var id: Long? = null
    var type: Int? = null
    var title: String? = null
    var content: String? = null
    var customerId: Long? = null
    var listTask: List<TaskRemindEntity>? = null
}