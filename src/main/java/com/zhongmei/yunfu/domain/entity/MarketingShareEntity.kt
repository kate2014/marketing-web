package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 分享营销
 * </p>
 *
 * @author pigeon88
 * @since 2018-10-01
 */
@TableName("marketing_share")
class MarketingShareEntity : BaseEntity() {

    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 分享类型：1：门店分享 2：新品分享 3：活动分享
     */
    var shareType: Int? = null
    /**
     * 开始时间，保持投放时间
     */
    var startDate: Date? = null
    /**
     * 结束时间
     */
    var endDate: Date? = null
    /**
     * 分享多次次能获取优惠
     */
    var shareCount: Int? = null
    /**
     * 分享优惠信息ID
     */
    var couponId: Long? = null
    /**
     * 分享优惠名称
     */
    var couponName: String? = null
    /**
     * 分享简介
     */
    var profile: String? = null
    /**
     * 分享状态:1:投放中 2：停止投放
     */
    var shareState: Int? = null
    /**
     * 浏览次数
     */
    var scanNumber: Int? = null
    /**
     * 分享次数
     */
    var shareNumber: Int? = null
    /**
     * 门店id
     */
    var shopIdenty: Long? = null
    /**
     * 品牌标识
     */
    var brandIdenty: Long? = null


    override fun toString(): String {
        return "MarketingShareEntity{" +
        ", id=" + id +
        ", shareType=" + shareType +
        ", startDate=" + startDate +
        ", endDate=" + endDate +
        ", shareCount=" + shareCount +
        ", couponId=" + couponId +
        ", couponName=" + couponName +
        ", profile=" + profile +
        ", shareState=" + shareState +
        ", scanNumber=" + scanNumber +
        ", shareNumber=" + shareNumber +
        ", shopIdenty=" + shopIdenty +
        ", brandIdenty=" + brandIdenty +
        "}"
    }
}
