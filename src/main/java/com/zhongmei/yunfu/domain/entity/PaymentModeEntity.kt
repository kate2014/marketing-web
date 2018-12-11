package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 
 * </p>
 *
 * @author yangyp
 * @since 2018-10-01
 */
@TableName("payment_mode")
class PaymentModeEntity : BaseEntity() {

    /**
     * 自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 支付方式名称
     */
    var name: String? = null
    /**
     * 1：在线支付  2：现金  3：银行卡  4：储值卡  5：券  6：其它收款 
     */
    var paymentModeType: Int? = null
    /**
     * 收银类型名称
     */
    var paymentModeName: String? = null
    /**
     * 排序
     */
    var sort: Int? = null


    override fun toString(): String {
        return "PaymentModeEntity{" +
        ", id=" + id +
        ", name=" + name +
        ", paymentModeType=" + paymentModeType +
        ", paymentModeName=" + paymentModeName +
        ", sort=" + sort +
        "}"
    }
}
