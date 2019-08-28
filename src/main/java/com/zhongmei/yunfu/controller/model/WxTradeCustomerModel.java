package com.zhongmei.yunfu.controller.model;

import com.zhongmei.yunfu.domain.entity.WxTradeCustomerEntity;

import java.util.List;
import java.util.Map;

public class WxTradeCustomerModel {

    private String status;

    private String stateCode;

    private String message;

    private String messageId;

    private String errors;

    private WxTradeCustomerEntity wxTradeCustomer;

    private List<WxTradeCustomerEntity> content;

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

    public List<WxTradeCustomerEntity> getContent() {
        return content;
    }

    public void setContent(List<WxTradeCustomerEntity> content) {
        this.content = content;
    }

    public WxTradeCustomerEntity getWxTradeCustomer() {
        return wxTradeCustomer;
    }

    public void setWxTradeCustomer(WxTradeCustomerEntity wxTradeCustomer) {
        this.wxTradeCustomer = wxTradeCustomer;
    }
}
