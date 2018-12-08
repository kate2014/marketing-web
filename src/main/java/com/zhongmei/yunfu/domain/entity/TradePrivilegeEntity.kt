package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 优惠信息
 * </p>
 *
 * @author pigeon88
 * @since 2018-10-01
 */
@TableName("trade_privilege")
class TradePrivilegeEntity : BaseEntity() {

    /**
     * 服务端自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 交易ID
     */
    var tradeId: Long? = null
    /**
     * 交易明细ID，如果是整单优惠此字段为null
     */
    var tradeItemId: Long? = null
    /**
     * 关联TRADE表的UUID
     */
    var tradeUuid: String? = null
    /**
     * 关联TRADE_ITEM的UUID
     */
    var tradeItemUuid: String? = null
    /**
     * 优惠类型：1:DISCOUNT:手动折扣,2:REBATE:折让,3:FREE:免单,4:PROMO:优惠活动或者优惠券,5:INTEGRAL:积分抵现,6:AUTO_DISCOUNT:自动折扣,7:平台优惠,8:商户优惠,9:物流优惠,10:代理商优惠,11:会员特价,12:附加费,13商品营销,14熟客折扣,15微信卡券,16宴请,17问题品项,18批量赠送,19分享减免,20微信礼品卡,21服务费,22次卡,23拼团,24秒杀,25砍价
     */
    var privilegeType: Int? = null
    /**
     * 优惠值，恒为正数 ，如9折时为90，折让5元时为5，抵现5积分时为5
     */
    var privilegeValue: BigDecimal? = null
    /**
     * 优惠金额，销售时为负数，退货时为正数
     */
    var privilegeAmount: BigDecimal? = null
    /**
     * 优惠活动或者优惠券id,如果没有优惠活动，会为空
     */
    var promoId: Long? = null
    /**
     * 附加费名称
     */
    var surchargeName: String? = null
    /**
     * 优惠类型名称
     */
    var privilegeName: String? = null
    /**
     * 是否被反核销 1 未核销  2：已核销 3：已反核销
     */
    var useStatus: Int? = null
    /**
     * 品牌标识
     */
    var brandIdenty: Long? = null
    /**
     * 门店标识
     */
    var shopIdenty: Long? = null
    /**
     * 设备标识
     */
    var deviceIdenty: String? = null
    /**
     * UUID，本笔记录唯一值
     */
    var uuid: String? = null
    /**
     * PAD本地创建时间
     */
    var clientCreateTime: Date? = null
    /**
     * PAD本地最后修改时间
     */
    var clientUpdateTime: Date? = null


    override fun toString(): String {
        return "TradePrivilegeEntity{" +
                ", id=" + id +
                ", tradeId=" + tradeId +
                ", tradeItemId=" + tradeItemId +
                ", tradeUuid=" + tradeUuid +
                ", tradeItemUuid=" + tradeItemUuid +
                ", privilegeType=" + privilegeType +
                ", privilegeValue=" + privilegeValue +
                ", privilegeAmount=" + privilegeAmount +
                ", promoId=" + promoId +
                ", surchargeName=" + surchargeName +
                ", privilegeName=" + privilegeName +
                ", brandIdenty=" + brandIdenty +
                ", shopIdenty=" + shopIdenty +
                ", deviceIdenty=" + deviceIdenty +
                ", uuid=" + uuid +
                ", clientCreateTime=" + clientCreateTime +
                ", clientUpdateTime=" + clientUpdateTime +
                "}"
    }
}
