package com.zhongmei.yunfu.controller.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.zhongmei.yunfu.controller.model.base.WebBaseModel;

import java.util.Date;

public class ShareMarketingModel extends WebBaseModel {
    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 已选择分享类型：1：门店分享 2：新品分享 3：活动分享
     */
    private Integer selectShareType;
    /**
     * 分享类型：1：门店分享 2：新品分享 3：活动分享
     */
    private Integer shareType;
    /**
     * 开始时间，保持投放时间
     */
    private Date startDate;
    /**
     * 结束时间
     */
    private String endDate;
    /**
     * 分享多次次能获取优惠
     */
    private Integer shareCount;
    /**
     * 分享后触发的优惠
     */
    private Long couponId;
    /**
     * 分享后触发的优惠名称
     */
    private String couponName;
    /**
     * 分享简介
     */
    private String profile;
    /**
     * 分享状态:1:投放中 2：停止投放
     */
    private Integer shareState;
    /**
     * 浏览次数
     */
    private Integer scanNumber;
    /**
     * 分享次数
     */
    private Integer shareNumber;
    /**
     * 门店id
     */
    private Long shopIdenty;
    /**
     * 品牌标识
     */
    private Long brandIdenty;
    /**
     * 状态标识1:启用 2:禁用
     */
    private Integer statusFlag;
    /**
     * 创建者id
     */
    private Long creatorId;
    /**
     * 创建者名称
     */
    private String creatorName;
    /**
     * 更新者id
     */
    private Long updatorId;
    /**
     * 最后修改者姓名
     */
    private String updatorName;
    /**
     * 服务器创建时间
     */
    private Date serverCreateTime;
    /**
     * 服务器更新时间
     */
    private Date serverUpdateTime;
    /**
     * 门店分享营销状态
     */
    private String shareShopState;
    /**
     * 门店分享营销分享数量
     */
    private Date shareShopDate;
    /**
     * 新品分享营销状态
     */
    private String shareDishState;
    /**
     * 新品分享营销分享数量
     */
    private Date shareDishDate;
    /**
     * 活动分享营销状态
     */
    private String shareActivityState;
    /**
     * 活动分享营销分享数量
     */
    private Date shareActivityDate;

    private String successOrfail;

    public String getSuccessOrfail() {
        return successOrfail;
    }

    public void setSuccessOrfail(String successOrfail) {
        this.successOrfail = successOrfail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSelectShareType() {
        return selectShareType;
    }

    public void setSelectShareType(Integer selectShareType) {
        this.selectShareType = selectShareType;
    }

    public Integer getShareType() {
        return shareType;
    }

    public void setShareType(Integer shareType) {
        this.shareType = shareType;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getShareCount() {
        return shareCount;
    }

    public void setShareCount(Integer shareCount) {
        this.shareCount = shareCount;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public Integer getShareState() {
        return shareState;
    }

    public void setShareState(Integer shareState) {
        this.shareState = shareState;
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

    public Integer getStatusFlag() {
        return statusFlag;
    }

    public void setStatusFlag(Integer statusFlag) {
        this.statusFlag = statusFlag;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Long getUpdatorId() {
        return updatorId;
    }

    public void setUpdatorId(Long updatorId) {
        this.updatorId = updatorId;
    }

    public String getUpdatorName() {
        return updatorName;
    }

    public void setUpdatorName(String updatorName) {
        this.updatorName = updatorName;
    }

    public Date getServerCreateTime() {
        return serverCreateTime;
    }

    public void setServerCreateTime(Date serverCreateTime) {
        this.serverCreateTime = serverCreateTime;
    }

    public Date getServerUpdateTime() {
        return serverUpdateTime;
    }

    public void setServerUpdateTime(Date serverUpdateTime) {
        this.serverUpdateTime = serverUpdateTime;
    }

    public String getShareShopState() {
        return shareShopState;
    }

    public void setShareShopState(String shareShopState) {
        this.shareShopState = shareShopState;
    }

    public Date getShareShopDate() {
        return shareShopDate;
    }

    public void setShareShopDate(Date shareShopDate) {
        this.shareShopDate = shareShopDate;
    }

    public String getShareDishState() {
        return shareDishState;
    }

    public void setShareDishState(String shareDishState) {
        this.shareDishState = shareDishState;
    }

    public Date getShareDishDate() {
        return shareDishDate;
    }

    public void setShareDishDate(Date shareDishDate) {
        this.shareDishDate = shareDishDate;
    }

    public String getShareActivityState() {
        return shareActivityState;
    }

    public void setShareActivityState(String shareActivityState) {
        this.shareActivityState = shareActivityState;
    }

    public Date getShareActivityDate() {
        return shareActivityDate;
    }

    public void setShareActivityDate(Date shareActivityDate) {
        this.shareActivityDate = shareActivityDate;
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
}
