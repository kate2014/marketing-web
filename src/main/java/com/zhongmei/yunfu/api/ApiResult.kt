package com.zhongmei.yunfu.api

import com.baomidou.mybatisplus.plugins.Page

class ApiResult(status: Int, message: String? = null) {

    var status: Int = SC_OK
    var message: String? = null
    val messageId: String? = null
    var content: Any? = null
    var page: ApiPage? = null

    init {
        this.status = status
        this.message = message
    }

    constructor(page: Page<*>? = null, content: Any?) : this(SC_OK) {
        this.page = if (page != null) ApiPage(page) else null
        this.content = content
    }


    companion object {
        const val SC_OK = 1000

        @JvmStatic
        fun newSuccess(content: Any?): ApiResult {
            if (content is Page<*>) {
                return newSuccess(content.records, content)
            }
            return newSuccess(content, null)
        }

        @JvmStatic
        fun newSuccess(content: Any?, page: Page<*>?): ApiResult {
            return ApiResult(page, content)
        }

        @JvmStatic
        fun newResult(status: ApiRespStatus): ApiResult {
            return ApiResult(status.value, status.reason)
        }

        @JvmStatic
        fun newResult(status: Int, message: String?): ApiResult {
            return ApiResult(status, message)
        }
    }
}