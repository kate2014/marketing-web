package com.zhongmei.yunfu.controller.model;

import com.zhongmei.yunfu.domain.entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TradeDataModel {

    private TradeEntity trade;

    List<Map<String,Object>> listTradeItem;

    private List<PaymentItemEntity> mPaymentItemEntity;

    private List<TradePrivilegeEntity> listPrivilege;

    private List<FeedbackModel> listFeedback;

    private FeedbackEntity returnFeedback;

    private List<StarRatingEntity> listStarRating;

    private WxTradeCustomerEntity wxTradeCustomerEntity;

    public TradeEntity getTrade() {
        return trade;
    }

    public void setTrade(TradeEntity trade) {
        this.trade = trade;
    }

    public List<Map<String, Object>> getListTradeItem() {
        return listTradeItem;
    }

    public void setListTradeItem(List<Map<String, Object>> listTradeItem) {
        this.listTradeItem = listTradeItem;
    }

    public List<PaymentItemEntity> getmPaymentItemEntity() {
        return mPaymentItemEntity;
    }

    public void setmPaymentItemEntity(List<PaymentItemEntity> mPaymentItemEntity) {
        this.mPaymentItemEntity = mPaymentItemEntity;
    }

    public List<TradePrivilegeEntity> getListPrivilege() {
        return listPrivilege;
    }

    public void setListPrivilege(List<TradePrivilegeEntity> listPrivilege) {
        this.listPrivilege = listPrivilege;
    }

    public List<FeedbackModel> getListFeedback() {
        return listFeedback;
    }

    public void setListFeedback(List<FeedbackModel> listFeedback) {
        this.listFeedback = listFeedback;
    }

    public List<StarRatingEntity> getListStarRating() {
        return listStarRating;
    }

    public void setListStarRating(List<StarRatingEntity> listStarRating) {
        this.listStarRating = listStarRating;
    }

    public FeedbackEntity getReturnFeedback() {
        return returnFeedback;
    }

    public void setReturnFeedback(FeedbackEntity returnFeedback) {
        this.returnFeedback = returnFeedback;
    }

    public WxTradeCustomerEntity getWxTradeCustomerEntity() {
        return wxTradeCustomerEntity;
    }

    public void setWxTradeCustomerEntity(WxTradeCustomerEntity wxTradeCustomerEntity) {
        this.wxTradeCustomerEntity = wxTradeCustomerEntity;
    }
}
