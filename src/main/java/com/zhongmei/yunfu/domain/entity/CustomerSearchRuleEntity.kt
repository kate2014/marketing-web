package com.zhongmei.yunfu.domain.entity

import com.baomidou.mybatisplus.enums.IdType
import com.baomidou.mybatisplus.annotations.TableId
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity

/**
 *
 *
 * 会员查询规则表
 *
 *
 * @author yangyp
 * @since 2018-11-14
 */
@TableName("customer_search_rule")
class CustomerSearchRuleEntity : BaseEntity() {

    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 消费主力群
     */
    var consumptionMainDay: Int? = null
    /**
     * 消费主力群金额
     */
    var consumptionMainAmount: Int? = null
    /**
     * 主力消费群累计次数
     */
    var consumptionMainNumber: Int? = null
    /**
     * 消费主力群金额到多少区间
     */
    var intervalConsumptionMainAmount: Int? = null
    /**
     * 主力消费群累计次数到多少区间
     */
    var intervalConsumptionMainNumber: Int? = null
    /**
     * 将流失会员
     */
    var membersWillDay: Int? = null
    /**
     * 将流失会员金额
     */
    var membersWillAmount: Int? = null
    /**
     * 将流失会员累计次数
     */
    var membersWillNumber: Int? = null
    /**
     * 将流失会员金额到多少区间
     */
    var intervalMembersWillAmount: Int? = null
    /**
     * 将流失会员累计次数到多少区间
     */
    var intervalMembersWillNumber: Int? = null
    /**
     * 已流失会员
     */
    var membersLossDay: Int? = null
    /**
     * 已流失会员金额
     */
    var membersLossAmount: Int? = null
    /**
     * 已流失会员累计次数
     */
    var membersLossNumber: Int? = null
    /**
     * 已流失会员金额多少区间
     */
    var intervalMembersLossAmount: Int? = null
    /**
     * 已流失会员累计次数多少区间
     */
    var intervalMembersLossNumber: Int? = null
    /**
     * 新会员 多少天为新会员
     */
    var membersNewIntervalDay: Int? = null
    /**
     * 将过生日会员
     */
    var membersBirthdayBeforeDay: Int? = null
    /**
     * 满周年会员
     */
    var membersAnniversaryBeforeDay: Int? = null
    /**
     * 如果归属门店，则为门店id；如果归属品牌，则为品牌id.
     * 新的权限体系下，全部为品牌id
     * 就是登录标示!!仅登录使用
     */
    var shopIdenty: Long? = null
    /**
     * 品牌标识 : 品牌标识
     */
    var brandIdenty: Long? = null

    override fun toString(): String {
        return "CustomerSearchRuleEntity{" +
                ", id=" + id +
                ", consumptionMainDay=" + consumptionMainDay +
                ", consumptionMainAmount=" + consumptionMainAmount +
                ", consumptionMainNumber=" + consumptionMainNumber +
                ", intervalConsumptionMainAmount=" + intervalConsumptionMainAmount +
                ", intervalConsumptionMainNumber=" + intervalConsumptionMainNumber +
                ", membersWillDay=" + membersWillDay +
                ", membersWillAmount=" + membersWillAmount +
                ", membersWillNumber=" + membersWillNumber +
                ", intervalMembersWillAmount=" + intervalMembersWillAmount +
                ", intervalMembersWillNumber=" + intervalMembersWillNumber +
                ", membersLossDay=" + membersLossDay +
                ", membersLossAmount=" + membersLossAmount +
                ", membersLossNumber=" + membersLossNumber +
                ", intervalMembersLossAmount=" + intervalMembersLossAmount +
                ", intervalMembersLossNumber=" + intervalMembersLossNumber +
                ", membersNewIntervalDay=" + membersNewIntervalDay +
                ", membersBirthdayBeforeDay=" + membersBirthdayBeforeDay +
                ", membersAnniversaryBeforeDay=" + membersAnniversaryBeforeDay +
                ", shopIdenty=" + shopIdenty +
                ", brandIdenty=" + brandIdenty +
                "}"
    }

    companion object {

        private val serialVersionUID = 1L
    }
}
