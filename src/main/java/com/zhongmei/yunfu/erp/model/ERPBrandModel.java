package com.zhongmei.yunfu.erp.model;

import com.zhongmei.yunfu.controller.model.base.WebBaseModel;
import com.zhongmei.yunfu.domain.entity.BrandEntity;
import com.zhongmei.yunfu.domain.entity.CommercialEntity;

import java.util.List;

public class ERPBrandModel extends WebBaseModel {

    /**
     * 状态：0有效 -1无效
     */
    private Integer status;
    /**
     * 类型 0代表集团 1代表公司
     */
    private Integer type;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
