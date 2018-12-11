package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import java.util.*

/**
 * <p>
 * 砍价记录
 * </p>
 *
 * @author yangyp
 * @since 2018-10-01
 */
@TableName("cut_down_history")
class CutDownHistoryEntity {

    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 关联砍价活动id
     */
    var cutDownId: Long? = null
    /**
     * 管理会员发起的砍价活动
     */
    var relationId: Long? = null
    /**
     * 砍价会员id
     */
    var customerId: Long? = null
    /**
     * 砍价会员openId
     */
    var openId: String? = null
    /**
     * 砍价会员名称
     */
    var wxName: String? = null
    /**
     * 砍价会员头像
     */
    var wxPhoto: String? = null
    /**
     * 砍价金额
     */
    var cutPrice: BigDecimal? = null
    /**
     * 品牌id
     */
    var brandIdentity: Long? = null
    /**
     * 门店id
     */
    var shopIdentity: Long? = null

    /**
     * 服务器创建时间
     */
    var serverCreateTime: Date? = null
    /**
     * 服务器更新时间
     */
    var serverUpdateTime: Date? = null

    override fun toString(): String {
        return "CutDownHistoryEntity{" +
        ", id=" + id +
        ", cutDownId=" + cutDownId +
        ", relationId=" + relationId +
        ", customerId=" + customerId +
        ", openId=" + openId +
        ", wxName=" + wxName +
        ", wxPhoto=" + wxPhoto +
        ", cutPrice=" + cutPrice +
        ", brandIdentity=" + brandIdentity +
        ", shopIdentity=" + shopIdentity +
        ", serverCreateTime=" + serverCreateTime +
        ", serverUpdateTime=" + serverUpdateTime +
        "}"
    }
}
