package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 会员关联推广回馈
 * </p>
 *
 * @author yangyp
 * @since 2018-10-01
 */
@TableName("customer_marketing_expanded")
class CustomerMarketingExpandedEntity : BaseEntity() {

    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 会员id
     */
    var customerId: Long? = null
    /**
     * 推广活动ID
     */
    var expandedId: Long? = null
    /**
     * 推广邀请码
     */
    var expandedCode: String? = null
    /**
     * 接受推广会员名称
     */
    var expandedCustomerName: String? = null
    /**
     * 接受推广会员微信id
     */
    var expandedCustomerOpenid: String? = null
    /**
     * 累积消费金额
     */
    var consumptionPrice: BigDecimal? = null
    /**
     * 门店id
     */
    var shopIdenty: Long? = null
    /**
     * 品牌标识
     */
    var brandIdenty: Long? = null
    /**
     * 推广状态 1：未接受 2：好友已被推广 3：成功 
     */
    var state: Integer? = null

    var expandedCustomerPic: String? = null


    override fun toString(): String {
        return "CustomerMarketingExpandedEntity{" +
        ", id=" + id +
        ", customerId=" + customerId +
        ", expandedId=" + expandedId +
        ", expandedCode=" + expandedCode +
        ", expandedCustomerName=" + expandedCustomerName +
        ", expandedCustomerOpenid=" + expandedCustomerOpenid +
        ", consumptionPrice=" + consumptionPrice +
        ", shopIdenty=" + shopIdenty +
        ", brandIdenty=" + brandIdenty +
        ", state=" + state +
        ", expandedCustomerPic=" + expandedCustomerPic +
        "}"
    }
}
