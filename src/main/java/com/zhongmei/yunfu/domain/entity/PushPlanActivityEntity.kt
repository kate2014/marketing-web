package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 活动推送活动方案
 * </p>
 *
 * @author yangyp
 * @since 2018-10-01
 */
@TableName("push_plan_activity")
class PushPlanActivityEntity : BaseEntity() {

    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 品牌id
     */
    var brandIdentity: Long? = null
    /**
     * 门店id
     */
    var shopIdentity: Long? = null
    /**
     * 方案名称
     */
    var name: String? = null
    /**
     * 活动简介
     */
    var planDesc: String? = null
    /**
     * 1, 进行中;2, 停止; 
     */
    var planState: Int? = null
    /**
     * 活动开始时间
     */
    var beginTime: Date? = null
    /**
     * 活动结束时间
     */
    var endTime: Date? = null
    /**
     * 活动描述
     */
    var describe: String? = null
    /**
     * 活动图片
     */
    var imgUrl: String? = null
    /**
     * 浏览次数
     */
    var scanNumber: Int? = null
    /**
     * 分享次数
     */
    var shareNumber: Int? = null


    override fun toString(): String {
        return "PushPlanActivityEntity{" +
        ", id=" + id +
        ", brandIdentity=" + brandIdentity +
        ", shopIdentity=" + shopIdentity +
        ", name=" + name +
        ", planDesc=" + planDesc +
        ", planState=" + planState +
        ", beginTime=" + beginTime +
        ", endTime=" + endTime +
        ", describe=" + describe +
        ", imgUrl=" + imgUrl +
        ", scanNumber=" + scanNumber +
        ", shareNumber=" + shareNumber +
        "}"
    }
}
