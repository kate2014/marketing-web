package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 砍价活动
 * </p>
 *
 * @author yangyp
 * @since 2018-10-01
 */
@TableName("cut_down_marketing")
class CutDownMarketingEntity : BaseEntity() {

    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 砍价活动名称
     */
    var name: String? = null
    /**
     * 砍价活动简介
     */
    var profile: String? = null
    /**
     * 活动开始时间
     */
    var beginTime: Date? = null
    /**
     * 活动结束时间
     */
    var endTime: Date? = null
    /**
     * 砍价起始价格
     */
    var startPrice: BigDecimal? = null
    /**
     * 砍价结束价格
     */
    var endPrice: BigDecimal? = null
    /**
     * 活动使用有效期
     */
    var validityPeriod: Date? = null
    /**
     * 砍价随机最小金额
     */
    var randomPriceMini: BigDecimal? = null
    /**
     * 砍价随机最大金额
     */
    var randomPriceMax: BigDecimal? = null
    /**
     * 商品Id
     */
    var productId: Long? = null
    /**
     * 商品名称
     */
    var productName: String? = null
    /**
     * 活动图片
     */
    var imgUrl: String? = null
    /**
     * 砍价描述
     */
    var describe: String? = null
    /**
     * 同一活动同一会员参与砍价次数
     */
    var customerCutCount: Int? = null
    /**
     * 活动售卖数量
     */
    var salesCount: Int? = null
    /**
     * 已售卖活动数量
     */
    var soldCount: Int? = null
    /**
     * 启用停用标识 : 1:启用;2:停用
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
        return "CutDownMarketingEntity{" +
        ", id=" + id +
        ", name=" + name +
        ", profile=" + profile +
        ", beginTime=" + beginTime +
        ", endTime=" + endTime +
        ", startPrice=" + startPrice +
        ", endPrice=" + endPrice +
        ", validityPeriod=" + validityPeriod +
        ", randomPriceMini=" + randomPriceMini +
        ", randomPriceMax=" + randomPriceMax +
        ", productId=" + productId +
        ", productName=" + productName +
        ", imgUrl=" + imgUrl +
        ", describe=" + describe +
        ", customerCutCount=" + customerCutCount +
        ", salesCount=" + salesCount +
        ", soldCount=" + soldCount +
        ", enabledFlag=" + enabledFlag +
        ", brandIdentity=" + brandIdentity +
        ", shopIdentity=" + shopIdentity +
        "}"
    }
}
