package com.zhongmei.yunfu.api.pos.vo

import com.zhongmei.yunfu.domain.entity.SystemVersionEntity

class SystemVersionResp {

    private var status: String? = null

    private var stateCode: String? = null

    private var message: String? = null

    private var messageId: String? = null

    private var errors: String? = null

    private var content: SystemVersionEntity? = null

    fun getStatus(): String? {
        return status
    }

    fun setStatus(status: String) {
        this.status = status
    }

    fun getStateCode(): String? {
        return stateCode
    }

    fun setStateCode(stateCode: String) {
        this.stateCode = stateCode
    }

    fun getMessage(): String? {
        return message
    }

    fun setMessage(message: String) {
        this.message = message
    }

    fun getMessageId(): String? {
        return messageId
    }

    fun setMessageId(messageId: String) {
        this.messageId = messageId
    }

    fun getErrors(): String? {
        return errors
    }

    fun setErrors(errors: String) {
        this.errors = errors
    }

    fun getContent(): SystemVersionEntity? {
        return content
    }

    fun setContent(content: SystemVersionEntity) {
        this.content = content
    }
}