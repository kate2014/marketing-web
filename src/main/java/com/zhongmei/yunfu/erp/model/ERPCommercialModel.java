package com.zhongmei.yunfu.erp.model;

import com.zhongmei.yunfu.controller.model.base.WebBaseModel;

public class ERPCommercialModel extends WebBaseModel {

    /**
     * 商户状态 0-可用 -1-不可用,1-预装
     */
    private Integer status;
    /**
     * 作废状态:1 正常 2 已作废
     */
    private Integer invalidStatus;
    /**
     * 所属品牌id
     */
    private Long brandId;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getInvalidStatus() {
        return invalidStatus;
    }

    public void setInvalidStatus(Integer invalidStatus) {
        this.invalidStatus = invalidStatus;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }
}
