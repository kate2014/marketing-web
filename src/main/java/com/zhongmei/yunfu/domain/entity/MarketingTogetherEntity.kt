package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 同行特惠
 * </p>
 *
 * @author yangyp
 * @since 2018-10-01
 */
@TableName("marketing_together")
class MarketingTogetherEntity : BaseEntity() {

    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 活动状态 1：启用中  2：停止
     */
    var state: Int? = null
    /**
     * 方案名称
     */
    var planName: String? = null
    /**
     * 活动开始时间，保持启动时间即为起始时间
     */
    var startDate: Date? = null
    /**
     * 活动结束时间
     */
    var endDate: Date? = null
    /**
     * 简介
     */
    var profile: String? = null
    /**
     * 同行优惠信息ID
     */
    var couponId: Long? = null
    /**
     * 同行优惠名称
     */
    var couponName: String? = null
    /**
     * 同行特惠富文本
     */
    var description: String? = null
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
        return "MarketingTogetherEntity{" +
        ", id=" + id +
        ", state=" + state +
        ", planName=" + planName +
        ", startDate=" + startDate +
        ", endDate=" + endDate +
        ", profile=" + profile +
        ", couponId=" + couponId +
        ", couponName=" + couponName +
        ", description=" + description +
        ", scanNumber=" + scanNumber +
        ", shareNumber=" + shareNumber +
        ", shopIdenty=" + shopIdenty +
        ", brandIdenty=" + brandIdenty +
        "}"
    }
}
