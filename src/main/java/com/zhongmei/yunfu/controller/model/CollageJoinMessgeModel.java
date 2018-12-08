package com.zhongmei.yunfu.controller.model;

import com.zhongmei.yunfu.domain.entity.CollageCustomerEntity;
import com.zhongmei.yunfu.domain.entity.CollageMarketingEntity;

public class CollageJoinMessgeModel {

    CollageCustomerEntity mCollageCustomerEntity;

    CollageMarketingEntity mCollageMarketing;

    public CollageCustomerEntity getmCollageCustomerEntity() {
        return mCollageCustomerEntity;
    }

    public void setmCollageCustomerEntity(CollageCustomerEntity mCollageCustomerEntity) {
        this.mCollageCustomerEntity = mCollageCustomerEntity;
    }

    public CollageMarketingEntity getmCollageMarketing() {
        return mCollageMarketing;
    }

    public void setmCollageMarketing(CollageMarketingEntity mCollageMarketing) {
        this.mCollageMarketing = mCollageMarketing;
    }
}
