package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 会员次卡消费表
 * </p>
 *
 * @author yangyp
 * @since 2018-10-02
 */
@TableName("customer_card_time_expend")
class CustomerCardTimeExpendEntity : BaseEntity() {

    /**
     * 自增主键 : 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 会员编号
     */
    var customerId: Long? = null
    /**
     * 次卡id
     */
    var customerCardTimeId: Long? = null
    /**
     * 记录类型1购买、2消费、3退货
     */
    var recordType: Int? = null
    /**
     * 订单id 
     */
    var tradeId: Long? = null
    /**
     * 订单uuid
     */
    var tradeUuid: String? = null
    /**
     * 品项id 
     */
    var dishId: Long? = null
    /**
     * 品项名称
     */
    var dishName: String? = null
    /**
     * 购买批次 
     */
    var groupId: Long? = null
    /**
     * 购买的批次名称
     */
    var groupName: String? = null
    /**
     * 交易次数(购买/消费)
     */
    var tradeCount: Int? = null
    /**
     * 剩余服务次数
     */
    var residueCount: Int? = null
    /**
     * 门店id 
     */
    var shopIdenty: Long? = null
    /**
     * 品牌标识 : 品牌标识
     */
    var brandIdenty: Long? = null


    override fun toString(): String {
        return "CustomerCardTimeExpendEntity{" +
        ", id=" + id +
        ", customerId=" + customerId +
        ", customerCardTimeId=" + customerCardTimeId +
        ", tradeId=" + tradeId +
        ", tradeUuid=" + tradeUuid +
        ", dishId=" + dishId +
        ", dishName=" + dishName +
        ", groupId=" + groupId +
        ", groupName=" + groupName +
        ", tradeCount=" + tradeCount +
        ", residueCount=" + residueCount +
        ", shopIdenty=" + shopIdenty +
        ", brandIdenty=" + brandIdenty +
        "}"
    }
}
