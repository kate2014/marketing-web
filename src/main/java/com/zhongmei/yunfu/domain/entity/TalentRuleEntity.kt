package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 人效规则算法
 * </p>
 *
 * @author yangyp
 * @since 2018-10-01
 */
@TableName("talent_rule")
class TalentRuleEntity : BaseEntity() {

    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 提出算法规则 1：固定金额提成  2：按比例提成
     */
    var ruleType: Int? = null
    /**
     * 提成算法基数
     */
    var ruleValue: String? = null
    /**
     * 提成数字  如：5% 或固定金额10元
     */
    var ruleCommission: String? = null
    /**
     * 对于的方案ID
     */
    var planId: Long? = null
    /**
     * 项目提成时对应的品项ID
     */
    var dishShopId: String? = null
    /**
     * 如果归属门店，则为门店id；如果归属品牌，则为       品牌id.
新的权限体系下，全部为品牌id
就是登录标示!!仅登录使用
     */
    var shopIdenty: Long? = null
    /**
     * 品牌标识 : 品牌标识
     */
    var brandIdenty: Long? = null


    override fun toString(): String {
        return "TalentRuleEntity{" +
        ", id=" + id +
        ", ruleType=" + ruleType +
        ", ruleValue=" + ruleValue +
        ", ruleCommission=" + ruleCommission +
        ", planId=" + planId +
        ", dishShopId=" + dishShopId +
        ", shopIdenty=" + shopIdenty +
        ", brandIdenty=" + brandIdenty +
        "}"
    }
}
