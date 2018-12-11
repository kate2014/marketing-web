package com.zhongmei.yunfu.controller.weixinPay;


import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "xml")
public class RefundqueryReponseModel {

    private String return_code;

    private String return_msg;

    private String result_code;

    private String err_code;

    private String err_code_des;

    private String appid;

    private String mch_id;

    private String nonce_str;

    private String sign;

    private String transaction_id;

    private String out_trade_no;

    private String total_fee;

    private String cash_fee;

    private String refund_count;

    private String out_refund_no;

    private String refund_id;

    private String refund_fee;

    private String refund_status_0;
    private String refund_status_1;
    private String refund_status_2;
    private String refund_status_3;

    private String refund_recv_accout;

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getErr_code() {
        return err_code;
    }

    public void setErr_code(String err_code) {
        this.err_code = err_code;
    }

    public String getErr_code_des() {
        return err_code_des;
    }

    public void setErr_code_des(String err_code_des) {
        this.err_code_des = err_code_des;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getCash_fee() {
        return cash_fee;
    }

    public void setCash_fee(String cash_fee) {
        this.cash_fee = cash_fee;
    }

    public String getRefund_count() {
        return refund_count;
    }

    public void setRefund_count(String refund_count) {
        this.refund_count = refund_count;
    }

    public String getOut_refund_no() {
        return out_refund_no;
    }

    public void setOut_refund_no(String out_refund_no) {
        this.out_refund_no = out_refund_no;
    }

    public String getRefund_id() {
        return refund_id;
    }

    public void setRefund_id(String refund_id) {
        this.refund_id = refund_id;
    }

    public String getRefund_fee() {
        return refund_fee;
    }

    public void setRefund_fee(String refund_fee) {
        this.refund_fee = refund_fee;
    }

    public String getRefund_status_0() {
        return refund_status_0;
    }

    public void setRefund_status_0(String refund_status_0) {
        this.refund_status_0 = refund_status_0;
    }

    public String getRefund_status_1() {
        return refund_status_1;
    }

    public void setRefund_status_1(String refund_status_1) {
        this.refund_status_1 = refund_status_1;
    }

    public String getRefund_status_2() {
        return refund_status_2;
    }

    public void setRefund_status_2(String refund_status_2) {
        this.refund_status_2 = refund_status_2;
    }

    public String getRefund_status_3() {
        return refund_status_3;
    }

    public void setRefund_status_3(String refund_status_3) {
        this.refund_status_3 = refund_status_3;
    }

    public String getRefund_recv_accout() {
        return refund_recv_accout;
    }

    public void setRefund_recv_accout(String refund_recv_accout) {
        this.refund_recv_accout = refund_recv_accout;
    }
}
