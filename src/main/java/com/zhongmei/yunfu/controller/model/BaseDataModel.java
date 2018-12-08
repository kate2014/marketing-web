package com.zhongmei.yunfu.controller.model;

import com.zhongmei.yunfu.api.ApiResponseStatus;

public class BaseDataModel {

    private String state;

    private String msg;

    public Object data;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static BaseDataModel newSuccess(Object content) {
        BaseDataModel baseDataModel = new BaseDataModel();
        baseDataModel.setState(ApiResponseStatus.SUCCESS.getValue() + "");
        baseDataModel.setMsg(ApiResponseStatus.SUCCESS.getReason());
        baseDataModel.setData(content);
        return baseDataModel;
    }

    public static BaseDataModel newCreate(int status, String message) {
        BaseDataModel baseDataModel = new BaseDataModel();
        baseDataModel.setState(status + "");
        baseDataModel.setMsg(message);
        //baseDataModel.setData(content);
        return baseDataModel;
    }
}
