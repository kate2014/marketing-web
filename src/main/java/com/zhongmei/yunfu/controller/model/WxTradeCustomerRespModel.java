package com.zhongmei.yunfu.controller.model;

import com.zhongmei.yunfu.domain.entity.WxTradeCustomerEntity;

import java.util.List;

public class WxTradeCustomerRespModel {

    private String status;

    private String stateCode;

    private String message;

    private String messageId;

    private String errors;

    private WxTradeCustomerEntity content;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }

    public WxTradeCustomerEntity getContent() {
        return content;
    }

    public void setContent(WxTradeCustomerEntity content) {
        this.content = content;
    }
}
