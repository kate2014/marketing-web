package com.zhongmei.yunfu.controller.api.model;

import com.zhongmei.yunfu.domain.entity.CustomerEntity;

public class CustomerResp {

    private CustomerEntity mCustomer;

    private String sessionKey;

    private String accesstoken;

    public CustomerEntity getmCustomer() {
        return mCustomer;
    }

    public void setmCustomer(CustomerEntity mCustomer) {
        this.mCustomer = mCustomer;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }
}
