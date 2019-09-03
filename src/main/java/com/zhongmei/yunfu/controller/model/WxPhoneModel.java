package com.zhongmei.yunfu.controller.model;

public class WxPhoneModel {

    private String phoneNumber;

    private String purePhoneNumber;

    private String countryCode;

    private String watermark;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPurePhoneNumber() {
        return purePhoneNumber;
    }

    public void setPurePhoneNumber(String purePhoneNumber) {
        this.purePhoneNumber = purePhoneNumber;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getWatermark() {
        return watermark;
    }

    public void setWatermark(String watermark) {
        this.watermark = watermark;
    }
}
