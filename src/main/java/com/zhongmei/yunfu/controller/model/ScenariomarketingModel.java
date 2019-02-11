package com.zhongmei.yunfu.controller.model;

import java.util.List;

/**
 * 我参与的拼团、秒杀和砍价活动
 */
public class ScenariomarketingModel {

    private Long brandIdenty;

    private Long shopIdenty;

    private Long customerId;

    private List<CutDownCustomerModel> listCutDown;

    private List<CollageCustomerModel> listCollage;

    private List<FlashSalesCustomerModel> listFlashSales;

    public Long getBrandIdenty() {
        return brandIdenty;
    }

    public void setBrandIdenty(Long brandIdenty) {
        this.brandIdenty = brandIdenty;
    }

    public Long getShopIdenty() {
        return shopIdenty;
    }

    public void setShopIdenty(Long shopIdenty) {
        this.shopIdenty = shopIdenty;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<CutDownCustomerModel> getListCutDown() {
        return listCutDown;
    }

    public void setListCutDown(List<CutDownCustomerModel> listCutDown) {
        this.listCutDown = listCutDown;
    }

    public List<CollageCustomerModel> getListCollage() {
        return listCollage;
    }

    public void setListCollage(List<CollageCustomerModel> listCollage) {
        this.listCollage = listCollage;
    }

    public List<FlashSalesCustomerModel> getListFlashSales() {
        return listFlashSales;
    }

    public void setListFlashSales(List<FlashSalesCustomerModel> listFlashSales) {
        this.listFlashSales = listFlashSales;
    }
}
