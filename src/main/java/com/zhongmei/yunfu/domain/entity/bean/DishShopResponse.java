package com.zhongmei.yunfu.domain.entity.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DishShopResponse implements Serializable {
    private String message;
    private String messageType;
    private Integer statusCode;
    private DishShopResponseContent data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public DishShopResponseContent getData() {
        return data;
    }

    public void setData(DishShopResponseContent data) {
        this.data = data;
    }
}
