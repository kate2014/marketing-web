package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 新品推送方案
 * </p>
 *
 * @author pigeon88
 * @since 2018-10-01
 */
@TableName("push_plan_new_dish")
class PushPlanNewDishEntity : BaseEntity() {

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
     * 菜品Id
     */
    var dishId: Long? = null
    /**
     * 菜品名称
     */
    var dishName: String? = null
    /**
     * 菜品价格
     */
    var dishPrice: BigDecimal? = null
    /**
     * 菜品备注
     */
    var dishRemark: String? = null
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
        return "PushPlanNewDishEntity{" +
        ", id=" + id +
        ", brandIdentity=" + brandIdentity +
        ", shopIdentity=" + shopIdentity +
        ", name=" + name +
        ", planDesc=" + planDesc +
        ", dishId=" + dishId +
        ", dishName=" + dishName +
        ", dishPrice=" + dishPrice +
        ", dishRemark=" + dishRemark +
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
