package com.zhongmei.yunfu.controller.model;

public class PaySignRequestModel {

    private String payment_id;

    private String payment_item_id;
    /**
     * 支付金额
     */
    private String total_amount;
    /**
     * 订单号tradeNo
     */
    private String out_trade_no;
    /**
     * 微信小程序ID
     */
    private String sub_appid;
    /**
     * 微信用户OpenId
     */
    private String sub_openid;
    /**
     * 终端IP
     */
    private String spbill_create_ip;

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    public String getPayment_item_id() {
        return payment_item_id;
    }

    public void setPayment_item_id(String payment_item_id) {
        this.payment_item_id = payment_item_id;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
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
}
