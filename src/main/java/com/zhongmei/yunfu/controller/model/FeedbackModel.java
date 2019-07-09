package com.zhongmei.yunfu.controller.model;

import com.zhongmei.yunfu.controller.model.base.WebBaseModel;
import com.zhongmei.yunfu.domain.entity.StarRatingEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 下单参：该服务只支持小程程序下单，而小程程序的拼团、秒杀和砍价只支持单商品，单服务的购买。
 */
public class FeedbackModel extends WebBaseModel {

    private Long feedbackId;
    /**
     * 反馈类型 1：订单反馈，2：门店反馈建议
     */
    private Integer type;
    /**
     * 关联订单Id
     */
    private Long tradeId;
    /**
     * 门店回复关联Id
     */
    private Long relateId;
    /**
     * 反馈信息
     */
    private String content;
    /**
     * 门店回复
     */
    private String replayContent;

    private String startDate;

    private String endDate;

    private Integer starNum;
    /**
     * 会员id
     */
    private Long customerId;
    /**
     * 会员名称
     */
    private String customerName;
    /**
     * 服务员Id
     */
    private Long userId;
    /**
     * 服务员名称
     */
    private String userName;
    /**
     * 状态：1：未回复  2：已回复
     */
    private Integer start;
    /**
     * 服务器时间
     */
    private String serverCreateTime;

    private String serverUpdateTime;

    private Long brandIdenty;

    private Long shopIdenty;

    private List<StarRatingEntity> listStar;

    public Long getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Long feedbackId) {
        this.feedbackId = feedbackId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getRelateId() {
        return relateId;
    }

    public void setRelateId(Long relateId) {
        this.relateId = relateId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public String getServerCreateTime() {
        return serverCreateTime;
    }

    public void setServerCreateTime(String serverCreateTime) {
        this.serverCreateTime = serverCreateTime;
    }

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

    public String getReplayContent() {
        return replayContent;
    }

    public void setReplayContent(String replayContent) {
        this.replayContent = replayContent;
    }

    public String getServerUpdateTime() {
        return serverUpdateTime;
    }

    public void setServerUpdateTime(String serverUpdateTime) {
        this.serverUpdateTime = serverUpdateTime;
    }

    public List<StarRatingEntity> getListStar() {
        return listStar;
    }

    public void setListStar(List<StarRatingEntity> listStar) {
        this.listStar = listStar;
    }

    public Long getTradeId() {
        return tradeId;
    }

    public void setTradeId(Long tradeId) {
        this.tradeId = tradeId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getStarNum() {
        return starNum;
    }

    public void setStarNum(Integer starNum) {
        this.starNum = starNum;
    }
}
