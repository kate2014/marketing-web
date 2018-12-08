package com.zhongmei.yunfu.controller.model;

public class WxPayRequestModel {

    private String appType;

    private String deviceID;

    private String systemType;

    private String versionCode;

    private String versionName;

    private PaySignRequestModel content;

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getSystemType() {
        return systemType;
    }

    public void setSystemType(String systemType) {
        this.systemType = systemType;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public PaySignRequestModel getContent() {
        return content;
    }

    public void setContent(PaySignRequestModel content) {
        this.content = content;
    }
}
