package com.zhongmei.yunfu.controller.model;

import com.zhongmei.yunfu.controller.model.base.IShopIdenty;
import com.zhongmei.yunfu.controller.model.base.WebBaseModel;

public class NewDishPushSearchModel extends WebBaseModel implements IShopIdenty {
    private Integer planState = 0; //0:全部 1:进行中; 2:停止;
    private String keyWord;//查询关键字
    private Integer sourceType;
    private Long shopIdenty;
    private Long brandIdenty;
    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public int getPlanState() {
        return planState;
    }

    public void setPlanState(int planState) {
        this.planState = planState;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public void setPlanState(Integer planState) {
        this.planState = planState;
    }

    public Long getShopIdenty() {
        return shopIdenty;
    }

    public void setShopIdenty(Long shopIdenty) {
        this.shopIdenty = shopIdenty;
    }

    public Long getBrandIdenty() {
        return brandIdenty;
    }

    public void setBrandIdenty(Long brandIdenty) {
        this.brandIdenty = brandIdenty;
    }
}
