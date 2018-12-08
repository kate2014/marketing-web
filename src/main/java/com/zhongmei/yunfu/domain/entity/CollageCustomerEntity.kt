package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 会员拼团记录
 * </p>
 *
 * @author yangyp
 * @since 2018-10-01
 */
@TableName("collage_customer")
class CollageCustomerEntity : BaseEntity() {

    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 关联拼团活动id
     */
    var collageId: Long? = null
    /**
     * 关联会员发起的拼团活动id
     */
    var relationId: Long? = null
    /**
     * 关联对应的tradeId
     */
    var tradeId: Long? = null
    /**
     * 是否已支付 1：未支付  2：已支付
     */
    var isPaid: Int? = null
    /**
     * 参团类型：1：发起拼团  2：参与拼团
     */
    var type: Int? = null
    /**
     * 会员id
     */
    var customerId: Long? = null
    /**
     * 拼团状态 1：拼团中  2：拼团完成  3：拼团失败
     */
    var state: Int? = null
    /**
     * 参与人数
     */
    var joinCount: Int? = null
    /**
     * 是否生效标识 : 1:生效;2:未生效
     */
    var enabledFlag: Int? = null
    /**
     * 品牌id
     */
    var brandIdentity: Long? = null
    /**
     * 门店id
     */
    var shopIdentity: Long? = null


    override fun toString(): String {
        return "CollageCustomerEntity{" +
        ", id=" + id +
        ", collageId=" + collageId +
        ", relationId=" + relationId +
        ", tradeId=" + tradeId +
        ", isPaid=" + isPaid +
        ", type=" + type +
        ", customerId=" + customerId +
        ", state=" + state +
        ", joinCount=" + joinCount +
        ", enabledFlag=" + enabledFlag +
        ", brandIdentity=" + brandIdentity +
        ", shopIdentity=" + shopIdentity +
        "}"
    }
}
