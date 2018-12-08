package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 拼团活动
 * </p>
 *
 * @author yangyp
 * @since 2018-10-01
 */
@TableName("collage_marketing")
class CollageMarketingEntity : BaseEntity() {

    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 拼团名称
     */
    var name: String? = null
    /**
     * 团类型：1 主团  2 开团
     */
    var type: Int? = null
    /**
     * 拼团简介
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
     * 活动使用有效期
     */
    var validityPeriod: Date? = null
    /**
     * 商品Id
     */
    var productId: Long? = null
    /**
     * 商品名称
     */
    var productName: String? = null
    /**
     * 成团人数
     */
    var collagePeopleCount: Int? = null
    /**
     * 成团价格
     */
    var collagePrice: BigDecimal? = null
    /**
     * 原价格
     */
    var originalPrice: BigDecimal? = null
    /**
     * 已参团人数
     */
    var joinCount: Int? = null
    /**
     * 活动图片
     */
    var imgUrl: String? = null
    /**
     * 拼团描述
     */
    var describe: String? = null
    /**
     * 最大开团数量
     */
    var maxOpenGroup: Int? = null
    /**
     * 启用停用标识 : 1:启用;2:停用
     */
    var enabledFlag: Int? = null
    /**
     * 开团数量
     */
    var openGroupCount: Int? = null
    /**
     * 成团数量
     */
    var finishGroupCount: Int? = null
    /**
     * 品牌id
     */
    var brandIdentity: Long? = null
    /**
     * 门店id
     */
    var shopIdentity: Long? = null


    override fun toString(): String {
        return "CollageMarketingEntity{" +
        ", id=" + id +
        ", name=" + name +
        ", type=" + type +
        ", profile=" + profile +
        ", beginTime=" + beginTime +
        ", endTime=" + endTime +
        ", validityPeriod=" + validityPeriod +
        ", productId=" + productId +
        ", productName=" + productName +
        ", collagePeopleCount=" + collagePeopleCount +
        ", collagePrice=" + collagePrice +
        ", originalPrice=" + originalPrice +
        ", joinCount=" + joinCount +
        ", imgUrl=" + imgUrl +
        ", describe=" + describe +
        ", maxOpenGroup=" + maxOpenGroup +
        ", enabledFlag=" + enabledFlag +
        ", openGroupCount=" + openGroupCount +
        ", finishGroupCount=" + finishGroupCount +
        ", brandIdentity=" + brandIdentity +
        ", shopIdentity=" + shopIdentity +
        "}"
    }
}
