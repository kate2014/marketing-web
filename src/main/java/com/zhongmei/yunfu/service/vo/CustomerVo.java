package com.zhongmei.yunfu.service.vo;

import com.zhongmei.yunfu.domain.entity.CustomerEntity;

public class CustomerVo extends CustomerEntity {

    private String wxOpenId;

    public String getWxOpenId() {
        return wxOpenId;
    }

    public void setWxOpenId(String wxOpenId) {
        this.wxOpenId = wxOpenId;
    }
}
