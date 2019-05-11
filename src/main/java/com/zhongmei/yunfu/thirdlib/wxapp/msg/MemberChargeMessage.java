package com.zhongmei.yunfu.thirdlib.wxapp.msg;

public class MemberChargeMessage extends WxTempMsg {

    public MemberChargeMessage() {
        super(member_charge);
    }

    private String chargeType; //充值类型
    private String mobileAccount; //帐号
    private String chargeTime; //充值时间
    private String chargeAmount; //充值金额
    private String tradeNo; //订单号
    private String givenAmount; //赠送金额
    private String accountBalance; //帐户余额
    private String memo; //备注
    private String shopName; //商家名称

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }

    public String getMobileAccount() {
        return mobileAccount;
    }

    public void setMobileAccount(String mobileAccount) {
        this.mobileAccount = mobileAccount;
    }

    public String getChargeTime() {
        return chargeTime;
    }

    public void setChargeTime(String chargeTime) {
        this.chargeTime = chargeTime;
    }

    public String getChargeAmount() {
        return chargeAmount;
    }

    public void setChargeAmount(String chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getGivenAmount() {
        return givenAmount;
    }

    public void setGivenAmount(String givenAmount) {
        this.givenAmount = givenAmount;
    }

    public String getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(String accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
