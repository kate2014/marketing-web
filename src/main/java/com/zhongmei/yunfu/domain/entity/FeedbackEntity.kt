package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 顾客反馈
 * </p>
 *
 * @author yangyp
 * @since 2018-10-01
 */
@TableName("feedback")
class FeedbackEntity : BaseEntity() {

    /**
     * 自增id : 自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 反馈类型 1：订单反馈，2：门店反馈建议
     */
    var type: Int? = null
    /**
     * 门店回复关联Id
     */
    var relateId: Long? = null
    /**
     * 反馈信息
     */
    var content: String? = null
    /**
     * 门店回复
     */
    var replayContent: String? = null
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
     * 状态：1：未回复  2：已回复
     */
    var start: Int? = null
    /**
     * 品牌编号 : 品牌编号
     */
    var brandIdenty: Long? = null
    /**
     * 门店标识
     */
    var shopIdenty: Long? = null


    override fun toString(): String {
        return "FeedbackEntity{" +
        ", id=" + id +
        ", type=" + type +
        ", relateId=" + relateId +
        ", replayContent=" + replayContent +
        ", content=" + content +
        ", tradeId=" + tradeId +
        ", customerId=" + customerId +
        ", customerName=" + customerName +
        ", userId=" + userId +
        ", userName=" + userName +
        ", start=" + start +
        ", brandIdenty=" + brandIdenty +
        ", shopIdenty=" + shopIdenty +
        "}"
    }
}
