package com.zhongmei.yunfu.controller.model;

import com.zhongmei.yunfu.domain.entity.TradeEntity;

import java.util.List;

public class TradePageDataModel {


    /**
     * 是否是最后一页，用于分页
     */
    private Boolean isEndPage;

    List<TradeEntity> listTradeData;

    public Boolean getEndPage() {
        return isEndPage;
    }

    public void setEndPage(Boolean endPage) {
        isEndPage = endPage;
    }

    public List<TradeEntity> getListTradeData() {
        return listTradeData;
    }

    public void setListTradeData(List<TradeEntity> listTradeData) {
        this.listTradeData = listTradeData;
    }
}
