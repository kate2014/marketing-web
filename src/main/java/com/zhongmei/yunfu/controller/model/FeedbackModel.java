package com.zhongmei.yunfu.controller.model;

import com.zhongmei.yunfu.controller.model.base.WebBaseModel;

import java.math.BigDecimal;

/**
 * 下单参：该服务只支持小程程序下单，而小程程序的拼团、秒杀和砍价只支持单商品，单服务的购买。
 */
public class FeedbackModel extends WebBaseModel {

    /**
     * 反馈类型 1：订单反馈，2：门店反馈建议
     */
    private Integer type;
    /**
     * 门店回复关联Id
     */
    private Long relateId;
    /**
     * 反馈信息
     */
    private String content;
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
}
