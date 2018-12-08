package com.zhongmei.yunfu.controller.model;

import com.zhongmei.yunfu.domain.entity.CustomerMarketingExpandedEntity;
import com.zhongmei.yunfu.domain.entity.ExpandedCommissionEntity;

import java.util.List;

public class CustomerCommissionModel {

    private List<CustomerMarketingExpandedEntity> listExpanded;

    private ExpandedCommissionEntity expandedCommission;

    public List<CustomerMarketingExpandedEntity> getListExpanded() {
        return listExpanded;
    }

    public void setListExpanded(List<CustomerMarketingExpandedEntity> listExpanded) {
        this.listExpanded = listExpanded;
    }

    public ExpandedCommissionEntity getExpandedCommission() {
        return expandedCommission;
    }

    public void setExpandedCommission(ExpandedCommissionEntity expandedCommission) {
        this.expandedCommission = expandedCommission;
    }
}
