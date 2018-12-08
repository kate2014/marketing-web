package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 会员等级积分成长规则、积分使用规则
 * </p>
 *
 * @author yangyp
 * @since 2018-10-01
 */
@TableName("customer_score_rule")
class CustomerScoreRuleEntity : BaseEntity() {

    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 类型 1：积分成长规则  2：积分抵用规则
     */
    var type: Int? = null
    /**
     * 1.积分成长值增长值：1元消费增加多少成长值; 2.积分抵用规则：多少积分可抵用1元
     */
    var convertValue: Int? = null
    /**
     * 如果归属门店，则为门店id；如果归属品牌，则为品牌id.
新的权限体系下，全部为品牌id
就是登录标示!!仅登录使用
     */
    var shopIdenty: Long? = null
    /**
     * 品牌标识 : 品牌标识
     */
    var brandIdenty: Long? = null


    override fun toString(): String {
        return "CustomerScoreRuleEntity{" +
        ", id=" + id +
        ", type=" + type +
        ", convertValue=" + convertValue +
        ", shopIdenty=" + shopIdenty +
        ", brandIdenty=" + brandIdenty +
        "}"
    }
}
