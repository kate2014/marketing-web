package com.zhongmei.yunfu.domain.entity.base

import java.util.*

abstract class SupperEntity : Cloneable {

    companion object {
        /**
         * 有效的
         */
        const val VALiD = 1

        /**
         * 无效的
         */
        const val INVALID = 2
    }


    /**
     * 状态标识1:启用 2:禁用
     */
    var statusFlag: Int? = null
    /**
     * 服务器创建时间
     */
    var serverCreateTime: Date? = null
    /**
     * 服务器更新时间
     */
    var serverUpdateTime: Date? = null

    fun baseCreate() {
        this.statusFlag = 1
        this.serverCreateTime = Calendar.getInstance().time
        this.serverUpdateTime = Calendar.getInstance().time
    }

    fun baseUpdate() {
        this.serverUpdateTime = Calendar.getInstance().time
    }

    fun isValid(): Boolean {
        return statusFlag == 1
    }

    override fun clone(): Any {
        return super.clone()
    }
}

