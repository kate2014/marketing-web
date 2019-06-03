package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.annotations.TableId
import com.baomidou.mybatisplus.annotations.TableName
import com.baomidou.mybatisplus.enums.IdType
import com.zhongmei.yunfu.domain.entity.base.BaseEntity
import java.math.BigDecimal
import java.util.*

/**
 * <p>
 * 会员表
 * </p>
 *
 * @author yangyp
 * @since 2018-10-01
 */
@TableName("customer")
open class CustomerEntity : BaseEntity() {

    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * UUID记录唯一值
     */
    var uuid: String? = null
    /**
     * 用户名称 : 用户名称
     */
    var name: String? = null
    /**
     * 性别 : 未知：0 ； 男：1女：2；
     */
    var gender: Int? = null
    /**
     * 手机号
     */
    var mobile: String? = null
    /**
     * 生日 : 生日
     */
    var birthday: Date? = null
    /**
     * 分组（会员等级）id
     */
    var groupLevelId: Long? = null
    /**
     * 分组（会员等级）
     */
    var groupLevel: String? = null
    /**
     * 固话
     */
    var telephone: String? = null
    /**
     * 邮箱
     */
    var email: String? = null
    /**
     * 喜好
     */
    var hobby: String? = null
    /**
     * 所在地址
     */
    var address: String? = null
    /**
     * 备注
     */
    var profile: String? = null
    /**
     * 管理会员id: 如果是小程序会员绑定了手机号码，则该字段指向对应的手机号码会员id
     */
    var relateId: Long? = null
    /**
     * 第三方id, 如微信小程序OpenId
     */
    var thirdId: String? = null
    /**
     * 密码
     */
    var password: String? = null
    /**
     * 会员积分
     */
    var integralTotal: Int? = null
    /**
     * 已使用积分
     */
    var integralUsed: Int? = null
    /**
     * 会员可用积分
     */
    var integral: Int? = null
    /**
     * 会员来源 1：pos本地  2：小程序
     */
    var sourceId: Int? = null
    /**
     * 邀请会员ID
     */
    var expandedId: Long? = null
    /**
     * 最近一次消费时间
     */
    var consumptionLastTime: Date? = null
    /**
     * 消费总金额
     */
    var consumptionAmount: BigDecimal? = null
    /**
     * 消费总次数
     */
    var consumptionNumber: Int? = null
    /**
     * 储值余额
     */
    var storedBalance: BigDecimal? = null
    /**
     * 已使用储值余额
     */
    var storedBalanceUsed: BigDecimal? = null
    /**
     * 储值赠送总金额
     */
    var storedBalanceGive: BigDecimal? = null
    /**
     * 剩余服务次数
     */
    var cardResidueCount: Int? = null
    /**
     * 卡过期时间
     */
    var cardExpireDate: Date? = null
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
    /**
     * 启用停用标识 : 1:启用;2:停用
     */
    var enabledFlag: Int? = null

    override fun toString(): String {
        return "CustomerEntity{" +
                ", id=" + id +
                ", name=" + name +
                ", gender=" + gender +
                ", mobile=" + mobile +
                ", birthday=" + birthday +
                ", groupLevel=" + groupLevel +
                ", telephone=" + telephone +
                ", email=" + email +
                ", hobby=" + hobby +
                ", address=" + address +
                ", profile=" + profile +
                ", relateId=" + relateId +
                ", thirdId=" + thirdId +
                ", password=" + password +
                ", sourceId=" + sourceId +
                ", expandedId=" + expandedId +
                ", consumptionLastTime=" + consumptionLastTime +
                ", consumptionNumber=" + consumptionNumber +
                ", consumptionAmount=" + consumptionAmount +
                ", storedBalance=" + storedBalance +
                ", storedBalanceUsed=" + storedBalanceUsed +
                ", shopIdenty=" + shopIdenty +
                ", brandIdenty=" + brandIdenty +
                ", enabledFlag=" + enabledFlag +
                "}"
    }
}
