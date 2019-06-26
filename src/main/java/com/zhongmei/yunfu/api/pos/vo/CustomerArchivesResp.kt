package com.zhongmei.yunfu.api.pos.vo

import com.zhongmei.yunfu.api.PosReq
import com.zhongmei.yunfu.domain.entity.CustomerArchivesEntity
import com.zhongmei.yunfu.domain.entity.TaskRemindEntity
import java.util.*

class CustomerArchivesResp {

    var id: Long? = null
    var type: Int? = null
    var title: String? = null
    var content: String? = null
    var customerId: Long? = null
    var listTask: List<TaskRemindEntity>? = null
    var serverCreateTime: Date? = null
    var serverUpdateTime: Date? = null
    var archives: CustomerArchivesEntity? = null
    var taskRemind: TaskRemindEntity ? = null
}