package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.annotations.TableName
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 会员扩展表
 * </p>
 *
 * @author yangyp
 * @since 2018-10-01
 */
@TableName("customer_extra")
class CustomerExtraEntity : CustomerEntity() {

    var customerId: Long? = null
    /**
     * 卡余额
     */
    var cardBalance: BigDecimal? = null
    /**
     * 最近消费时间
     */
    var cardConsumptionTime: Date? = null


    override fun toString(): String {
        return "CustomerExtraEntity{" +
        ", customerId=" + customerId +
        ", cardBalance=" + cardBalance +
        ", cardResidueCount=" + cardResidueCount +
        ", cardConsumptionTime=" + cardConsumptionTime +
        "}"
    }
}
