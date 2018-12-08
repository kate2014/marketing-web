package com.zhongmei.yunfu.controller.model;

import com.zhongmei.yunfu.domain.entity.CollageMarketingEntity;
import com.zhongmei.yunfu.domain.entity.CutDownMarketingEntity;
import com.zhongmei.yunfu.domain.entity.FlashSalesMarketingEntity;

import java.util.List;

/**
 * 我参与的拼团、秒杀和砍价活动
 */
public class ShopScenariomarketingModel {

    private CollageMarketingEntity collage;

    private CutDownMarketingEntity cutDown;

    private FlashSalesMarketingEntity flashSales;

    public CollageMarketingEntity getCollage() {
        return collage;
    }

    public void setCollage(CollageMarketingEntity collage) {
        this.collage = collage;
    }

    public CutDownMarketingEntity getCutDown() {
        return cutDown;
    }

    public void setCutDown(CutDownMarketingEntity cutDown) {
        this.cutDown = cutDown;
    }

    public FlashSalesMarketingEntity getFlashSales() {
        return flashSales;
    }

    public void setFlashSales(FlashSalesMarketingEntity flashSales) {
        this.flashSales = flashSales;
    }
}
