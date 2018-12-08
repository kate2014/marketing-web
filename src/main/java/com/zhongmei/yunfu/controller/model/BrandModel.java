package com.zhongmei.yunfu.controller.model;

import com.zhongmei.yunfu.domain.entity.BrandEntity;
import com.zhongmei.yunfu.domain.entity.CommercialEntity;

import java.util.List;

public class BrandModel {

    private BrandEntity brand;

    private List<CommercialEntity> listCommercial;

    public BrandEntity getBrand() {
        return brand;
    }

    public void setBrand(BrandEntity brand) {
        this.brand = brand;
    }

    public List<CommercialEntity> getListCommercial() {
        return listCommercial;
    }

    public void setListCommercial(List<CommercialEntity> listCommercial) {
        this.listCommercial = listCommercial;
    }
}
