package com.zhongmei.yunfu.domain.entity.base

abstract class BaseEntity : SupperEntity() {

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
        super.baseCreate()
        this.creatorId = creatorId
        this.creatorName = creatorName
        baseUpdate(creatorId, creatorName)
    }

    fun baseUpdate(updatorId: Long?, updatorName: String?) {
        super.baseUpdate()
        this.updatorId = updatorId
        this.updatorName = updatorName
    }
}