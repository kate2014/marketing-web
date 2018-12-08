package com.zhongmei.yunfu.domain.entity.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SalaryCommiosResponse implements Serializable {
    private String message;
    private String messageType;
    private Integer statusCode;
    private String dataType;
    private SalaryCommions data;

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

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

    public SalaryCommions getData() {
        return data;
    }

    public void setData(SalaryCommions data) {
        this.data = data;
    }
}
