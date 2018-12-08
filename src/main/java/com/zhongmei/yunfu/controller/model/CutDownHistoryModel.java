package com.zhongmei.yunfu.controller.model;

import com.zhongmei.yunfu.domain.entity.CutDownCustomerEntity;
import com.zhongmei.yunfu.domain.entity.CutDownHistoryEntity;
import com.zhongmei.yunfu.domain.entity.CutDownMarketingEntity;

import java.util.List;

public class CutDownHistoryModel {

    private CutDownCustomerEntity mCutDownCustomer;

    private List<CutDownHistoryEntity> listData;

    private CutDownMarketingEntity mCutDownMarketing;

    public CutDownCustomerEntity getmCutDownCustomer() {
        return mCutDownCustomer;
    }

    public void setmCutDownCustomer(CutDownCustomerEntity mCutDownCustomer) {
        this.mCutDownCustomer = mCutDownCustomer;
    }

    public List<CutDownHistoryEntity> getListData() {
        return listData;
    }

    public void setListData(List<CutDownHistoryEntity> listData) {
        this.listData = listData;
    }

    public CutDownMarketingEntity getmCutDownMarketing() {
        return mCutDownMarketing;
    }

    public void setmCutDownMarketing(CutDownMarketingEntity mCutDownMarketing) {
        this.mCutDownMarketing = mCutDownMarketing;
    }
}
