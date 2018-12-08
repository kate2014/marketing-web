package com.zhongmei.yunfu.controller.model;

/**
 * 微信小程序支付参数
 */
public class WxPayMsgModel {

    /**
     * 状态码
     */
    private String code;

    /**
     * 小程序ID
     */
    private String appid;

    /**
     * 时间戳
     */
    private String time_stamp;

    /**
     * 随机字符串
     */
    private String nonce_str;

    /**
     * 数据包
     */
    private String prepay_id;

    /**
     * 签名算法
     */
    private String sign_type;

    private String sign;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(String time_stamp) {
        this.time_stamp = time_stamp;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getPrepay_id() {
        return prepay_id;
    }

    public void setPrepay_id(String prepay_id) {
        this.prepay_id = prepay_id;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
