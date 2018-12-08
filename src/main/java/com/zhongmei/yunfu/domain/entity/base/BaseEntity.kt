package com.zhongmei.yunfu.domain.entity.base

import java.util.*

abstract class BaseEntity {

    /**
     * 状态标识1:启用 2:禁用
     */
    var statusFlag: Int? = null
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
    /**
     * 服务器创建时间
     */
    var serverCreateTime: Date? = null
    /**
     * 服务器更新时间
     */
    var serverUpdateTime: Date? = null

    fun baseCreate(creatorId: Long?, creatorName: String?) {
        this.creatorId = creatorId
        this.creatorName = creatorName
        this.serverCreateTime = Calendar.getInstance().time
        baseUpdate(creatorId, creatorName)
    }

    fun baseUpdate(updatorId: Long?, updatorName: String?) {
        this.updatorId = updatorId
        this.updatorName = updatorName
        this.serverUpdateTime = Calendar.getInstance().time
    }
}