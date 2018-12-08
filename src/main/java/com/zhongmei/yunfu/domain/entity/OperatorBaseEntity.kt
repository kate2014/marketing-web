package com.zhongmei.yunfu.domain.entity

import com.zhongmei.yunfu.domain.enums.StatusFlag
import com.zhongmei.yunfu.domain.enums.ValueEnums
import java.util.*

abstract class OperatorBaseEntity/*: BaseEntity()*/ {

    /**
     * 品牌Id
     */
    var brandIdenty: Long? = null
    /**
     * 门店Id
     */
    var shopIdenty: Long? = null
    /**
     * 创建者id
     */
    var creatorId: Long? = null
    /**
     * 创建者名称
     */
    var creatorName: String? = null
    /**
     * 更新者id
     */
    var updatorId: Long? = null
    /**
     * 最后修改者姓名
     */
    var updatorName: String? = null

    fun baseCreate(creatorId: Long?, creatorName: String?) {
        //this.serverCreateTime = Calendar.getInstance().time
        this.creatorId = creatorId
        this.creatorName = creatorName
        baseUpdate(creatorId, creatorName)
    }

    fun baseUpdate(updatorId: Long?, updatorName: String?) {
        //this.serverUpdateTime = Calendar.getInstance().time
        this.updatorId = updatorId
        this.updatorName = updatorName
    }
}