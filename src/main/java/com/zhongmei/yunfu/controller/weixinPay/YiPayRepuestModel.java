package com.zhongmei.yunfu.controller.weixinPay;

public class YiPayRepuestModel {

    private String appid;

    private String sign;

    private int total_amount;

    private String out_trade_no;

    private String return_url;

    private String sub_appid;

    private String sub_openid;

    private String spbill_create_ip;

    private String nonce_str;

    private String version;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public int getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(int total_amount) {
        this.total_amount = total_amount;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getReturn_url() {
        return return_url;
    }

    public void setReturn_url(String return_url) {
        this.return_url = return_url;
    }

    public String getSub_appid() {
        return sub_appid;
    }

    public void setSub_appid(String sub_appid) {
        this.sub_appid = sub_appid;
    }

    public String getSub_openid() {
        return sub_openid;
    }

    public void setSub_openid(String sub_openid) {
        this.sub_openid = sub_openid;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
