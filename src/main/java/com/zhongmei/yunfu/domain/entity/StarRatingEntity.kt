package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 服务评星
 * </p>
 *
 * @author yangyp
 * @since 2018-10-01
 */
@TableName("star_rating")
class StarRatingEntity : BaseEntity() {

    /**
     * 自增id : 自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 反馈类型 1：订单评星，2：门店评星
     */
    var type: Integer? = null
    /**
     * 评分标签Id
     */
    var starLableId: Long? = null
    /**
     * 评分标签
     */
    var starLable: String? = null
    /**
     * 评分等级
     */
    var starNum: Int? = null
    /**
     * 订单Id
     */
    var tradeId: Long? = null
    /**
     * 顾客Id
     */
    var customerId: Long? = null
    /**
     * 顾客名称
     */
    var customerName: String? = null
    /**
     * 服务员Id
     */
    var userId: Long? = null
    /**
     * 服务员名称
     */
    var userName: String? = null
    /**
     * 品牌编号 : 品牌编号
     */
    var brandIdenty: Long? = null
    /**
     * 门店标识
     */
    var shopIdenty: Long? = null


    override fun toString(): String {
        return "StarRatingEntity{" +
        ", id=" + id +
        ", type=" + type +
        ", starLableId=" + starLableId +
        ", starLable=" + starLable +
        ", starNum=" + starNum +
        ", tradeId=" + tradeId +
        ", customerId=" + customerId +
        ", customerName=" + customerName +
        ", userId=" + userId +
        ", userName=" + userName +
        ", brandIdenty=" + brandIdenty +
        ", shopIdenty=" + shopIdenty +
        "}"
    }
}
