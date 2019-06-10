package com.zhongmei.yunfu.domain.bean;

import com.zhongmei.yunfu.domain.entity.CustomerEntity;

import java.math.BigDecimal;

public class CustomerDrain extends CustomerEntity {

    /**
     * 储值总余额(包括赠送)
     */
    private BigDecimal storedAmount;
    /**
     * 已使用余额
     */
    private BigDecimal storedUsed;
    //private BigDecimal storedBalance;

    public BigDecimal getStoredAmount() {
        return storedAmount;
    }

    public void setStoredAmount(BigDecimal storedAmount) {
        this.storedAmount = storedAmount;
    }

    public BigDecimal getStoredUsed() {
        return storedUsed;
    }

    public void setStoredUsed(BigDecimal storedUsed) {
        this.storedUsed = storedUsed;
    }

    /*public BigDecimal getStoredBalance() {
        return storedBalance;
    }

    public void setStoredBalance(BigDecimal storedBalance) {
        this.storedBalance = storedBalance;
    }*/
}
