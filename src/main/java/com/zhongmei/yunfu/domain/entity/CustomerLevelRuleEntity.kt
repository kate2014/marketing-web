package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 会员等级积分表
 * </p>
 *
 * @author yangyp
 * @since 2018-10-01
 */
@TableName("customer_level_rule")
class CustomerLevelRuleEntity : BaseEntity() {

    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 等级编号
     */
    var levelCode: Int? = null
    /**
     * 等级名称
     */
    var levelName: String? = null
    /**
     * 等级对应积分
     */
    var levelScore: Int? = null
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
        return "CustomerLevelRuleEntity{" +
        ", id=" + id +
        ", levelCode=" + levelCode +
        ", levelName=" + levelName +
        ", levelScore=" + levelScore +
        ", shopIdenty=" + shopIdenty +
        ", brandIdenty=" + brandIdenty +
        "}"
    }
}
