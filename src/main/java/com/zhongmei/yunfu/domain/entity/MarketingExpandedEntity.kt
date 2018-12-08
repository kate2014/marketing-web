package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;
import java.math.BigDecimal

/**
 * <p>
 * 全员推广营销
 * </p>
 *
 * @author pigeon88
 * @since 2018-10-01
 */
@TableName("marketing_expanded")
class MarketingExpandedEntity : BaseEntity() {

    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 方案名称
     */
    var planName: String? = null
    /**
     * 开始时间
     */
    var startDate: Date? = null
    /**
     * 结束时间
     */
    var endDate: Date? = null
    /**
     * 简介
     */
    var profile: String? = null
    /**
     * 营销活动状态 1:投放中 2:停止
     */
    var expandedState: Int? = null
    /**
     * 一级推广提成比例
     */
    var firstLevelDiscount: BigDecimal? = null
    /**
     * 二季推广提成比例
     */
    var secondClassDicount: BigDecimal? = null
    /**
     * 提成方式，目前写上为 1：按比例提成
     */
    var dicountType: Int? = null
    /**
     * 推广活动结束，富文本信息
     */
    var description: String? = null
    /**
     * 门店id
     */
    var shopIdenty: Long? = null
    /**
     * 品牌标识
     */
    var brandIdenty: Long? = null


    override fun toString(): String {
        return "MarketingExpandedEntity{" +
        ", id=" + id +
        ", planName=" + planName +
        ", startDate=" + startDate +
        ", endDate=" + endDate +
        ", profile=" + profile +
        ", expandedState=" + expandedState +
        ", firstLevelDiscount=" + firstLevelDiscount +
        ", secondClassDicount=" + secondClassDicount +
        ", dicountType=" + dicountType +
        ", description=" + description +
        ", shopIdenty=" + shopIdenty +
        ", brandIdenty=" + brandIdenty +
        "}"
    }
}
