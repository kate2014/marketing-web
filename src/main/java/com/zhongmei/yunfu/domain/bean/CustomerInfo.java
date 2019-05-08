package com.zhongmei.yunfu.domain.bean;

import com.zhongmei.yunfu.domain.entity.CustomerEntity;

public class CustomerInfo extends CustomerEntity {

    private String cardId;
    private String cardNo;

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
}
