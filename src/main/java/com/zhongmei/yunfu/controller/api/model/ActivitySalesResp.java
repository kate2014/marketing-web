package com.zhongmei.yunfu.controller.api.model;

import com.zhongmei.yunfu.domain.entity.*;

import java.util.List;

public class ActivitySalesResp {


    /**
     * 活动基本信息
     */
    ActivitySalesEntity mActivitySalesEntity;
    /**
     * 活动推荐成单礼品规则
     */
    List<ActivitySalesGiftEntity> listGift;
    /**
     * 活动查看、分享、购买次数
     */
    List<OperationalRecordsEntity> listOperationalCount;
    /**
     * 活动用户查看记录
     */
    List<OperationalRecordsEntity> listOperational;
    /**
     * 用户推荐成单数
     */
    Integer tradeCount;
    /**
     * 活动中红包获取排行榜
     */
    List<RedPacketsRecordEntity> listRedPacketsRecord;
    /**
     * 活动购买前20条记录
     */
    List<WxTradeCustomerEntity> listTrade;

    public ActivitySalesEntity getmActivitySalesEntity() {
        return mActivitySalesEntity;
    }

    public void setmActivitySalesEntity(ActivitySalesEntity mActivitySalesEntity) {
        this.mActivitySalesEntity = mActivitySalesEntity;
    }

    public List<ActivitySalesGiftEntity> getListGift() {
        return listGift;
    }

    public void setListGift(List<ActivitySalesGiftEntity> listGift) {
        this.listGift = listGift;
    }

    public List<OperationalRecordsEntity> getListOperational() {
        return listOperational;
    }

    public void setListOperational(List<OperationalRecordsEntity> listOperational) {
        this.listOperational = listOperational;
    }

    public Integer getTradeCount() {
        return tradeCount;
    }

    public void setTradeCount(Integer tradeCount) {
        this.tradeCount = tradeCount;
    }

    public List<RedPacketsRecordEntity> getListRedPacketsRecord() {
        return listRedPacketsRecord;
    }

    public void setListRedPacketsRecord(List<RedPacketsRecordEntity> listRedPacketsRecord) {
        this.listRedPacketsRecord = listRedPacketsRecord;
    }

    public List<WxTradeCustomerEntity> getListTrade() {
        return listTrade;
    }

    public void setListTrade(List<WxTradeCustomerEntity> listTrade) {
        this.listTrade = listTrade;
    }

    public List<OperationalRecordsEntity> getListOperationalCount() {
        return listOperationalCount;
    }

    public void setListOperationalCount(List<OperationalRecordsEntity> listOperationalCount) {
        this.listOperationalCount = listOperationalCount;
    }
}
