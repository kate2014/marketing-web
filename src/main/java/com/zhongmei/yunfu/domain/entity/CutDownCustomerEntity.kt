package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 会员发起砍价记录
 * </p>
 *
 * @author pigeon88
 * @since 2018-10-01
 */
@TableName("cut_down_customer")
class CutDownCustomerEntity : BaseEntity() {

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
     * 会员id
     */
    var customerId: Long? = null
    /**
     * 砍价会员名称
     */
    var wxName: String? = null
    /**
     * 砍价会员头像
     */
    var wxPhoto: String? = null
    /**
     * 砍价状态 1：砍价中  2：砍价完成  3：砍价失败 4、购买完成
     */
    var state: Int? = null
    /**
     * 已砍价格
     */
    var currentPrice: BigDecimal? = null
    /**
     * 参与人数
     */
    var joinCount: Int? = null
    /**
     * 品牌id
     */
    var brandIdentity: Long? = null
    /**
     * 门店id
     */
    var shopIdentity: Long? = null


    override fun toString(): String {
        return "CutDownCustomerEntity{" +
        ", id=" + id +
        ", cutDownId=" + cutDownId +
        ", customerId=" + customerId +
        ", wxName=" + wxName +
        ", wxPhoto=" + wxPhoto +
        ", state=" + state +
        ", currentPrice=" + currentPrice +
        ", joinCount=" + joinCount +
        ", brandIdentity=" + brandIdentity +
        ", shopIdentity=" + shopIdentity +
        "}"
    }
}
