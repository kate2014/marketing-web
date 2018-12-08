package com.zhongmei.yunfu.controller.model;


import com.zhongmei.yunfu.util.DateFormatUtil;
import com.zhongmei.yunfu.domain.entity.PushPlanNewDishEntity;

import java.math.BigDecimal;
import java.util.Date;

public class NewDishPushModel {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    private Long id;
    /**
     * 方案名称
     */
    private String name;
    /**
     * 活动简介
     */
    private String planDesc;
    /**
     * 菜品Id
     */
    private Long dishId;
    /**
     * 菜品名称
     */
    private String dishName;
    /**
     * 菜品价格
     */
    private BigDecimal dishPrice;
    /**
     * 菜品备注
     */
    private String dishRemark;
    /**
     * 1, 进行中;2, 停止;
     */
    private Integer planState;
    /**
     * 活动开始时间
     */
    private String beginTime;
    /**
     * 活动结束时间
     */
    private String endTime;
    /**
     * 活动描述
     */
    private String describe;
    /**
     * 活动图片
     */
    private String imgUrl;
    /**
     * 浏览次数
     */
    private Integer scanNumber;
    /**
     * 分享次数
     */
    private Integer shareNumber;

    /**
     * 服务器更新时间
     */
    private Date serverUpdateTime;

    public NewDishPushModel() {
    }

    public NewDishPushModel(PushPlanNewDishEntity newDishPlan) {
        this.id = newDishPlan.getId();
        this.name = newDishPlan.getName();
        this.planDesc = newDishPlan.getPlanDesc();
        this.dishId = newDishPlan.getDishId();
        this.dishName = newDishPlan.getDishName();
        this.dishPrice = newDishPlan.getDishPrice();
        this.dishRemark = newDishPlan.getDishRemark();
        this.planState = newDishPlan.getPlanState();
        this.describe = newDishPlan.getDescribe();
        this.imgUrl = newDishPlan.getImgUrl();
        this.scanNumber = newDishPlan.getScanNumber();
        this.shareNumber = newDishPlan.getShareNumber();
        this.serverUpdateTime = newDishPlan.getServerUpdateTime();
        if (newDishPlan.getBeginTime() != null) {
            this.beginTime = DateFormatUtil.format(newDishPlan.getBeginTime(), DateFormatUtil.FORMAT_DATE);
        }

        if (newDishPlan.getEndTime() != null) {
            this.endTime = DateFormatUtil.format(newDishPlan.getEndTime(), DateFormatUtil.FORMAT_DATE);
        }
    }

    public PushPlanNewDishEntity obtainNewDishPlan() {
        PushPlanNewDishEntity newDishPlan = new PushPlanNewDishEntity();
        newDishPlan.setId(id);
        newDishPlan.setName(name);
        newDishPlan.setPlanDesc(planDesc);
        newDishPlan.setDishId(dishId);
        newDishPlan.setDishName(dishName);
        newDishPlan.setDishPrice(dishPrice);
        newDishPlan.setDishRemark(dishRemark);
        newDishPlan.setPlanState(planState);
        newDishPlan.setDescribe(describe);
        newDishPlan.setImgUrl(imgUrl);
        newDishPlan.setScanNumber(scanNumber);
        newDishPlan.setShareNumber(shareNumber);
        try {
            if (endTime != null) {
                newDishPlan.setEndTime(DateFormatUtil.parseDate(endTime, DateFormatUtil.FORMAT_DATE));
            }
            if (beginTime != null) {
                newDishPlan.setBeginTime(DateFormatUtil.parseDate(beginTime, DateFormatUtil.FORMAT_DATE));
            }
            newDishPlan.setServerUpdateTime(serverUpdateTime);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return newDishPlan;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlanDesc() {
        return planDesc;
    }

    public void setPlanDesc(String planDesc) {
        this.planDesc = planDesc;
    }

    public Long getDishId() {
        return dishId;
    }

    public void setDishId(Long dishId) {
        this.dishId = dishId;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public BigDecimal getDishPrice() {
        return dishPrice;
    }

    public void setDishPrice(BigDecimal dishPrice) {
        this.dishPrice = dishPrice;
    }

    public String getDishRemark() {
        return dishRemark;
    }

    public void setDishRemark(String dishRemark) {
        this.dishRemark = dishRemark;
    }

    public Integer getPlanState() {
        return planState;
    }

    public void setPlanState(Integer planState) {
        this.planState = planState;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getScanNumber() {
        return scanNumber;
    }

    public void setScanNumber(Integer scanNumber) {
        this.scanNumber = scanNumber;
    }

    public Integer getShareNumber() {
        return shareNumber;
    }

    public void setShareNumber(Integer shareNumber) {
        this.shareNumber = shareNumber;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public Date getServerUpdateTime() {
        return serverUpdateTime;
    }

    public void setServerUpdateTime(Date serverUpdateTime) {
        this.serverUpdateTime = serverUpdateTime;
    }

    public String obtainStateStr() {
        if (getPlanState() == null) {
            return "未知状态";
        } else if (getPlanState() == 1) {
            return "进行中";
        } else if (getPlanState() == 2) {
            return "停止";
        }
        return "未知状态";
    }
}
