package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 推送信息给顾客
 * </p>
 *
 * @author pigeon88
 * @since 2018-10-01
 */
@TableName("push_message_customer")
class PushMessageCustomerEntity : BaseEntity() {

    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 推送类型：1：新品活动推送  2：门店活动推送  3：优惠券推送
     */
    var type: Int? = null
    /**
     * 会员id
     */
    var customerId: Long? = null
    /**
     * 会员微信openId
     */
    var wxOpenId: String? = null
    /**
     * 推送信息关联Id
     */
    var relationId: Long? = null
    /**
     * 推送信息标题
     */
    var heading: String? = null
    /**
     * 推送信息图片
     */
    var imgUrl: String? = null
    /**
     * 推送信息
     */
    var message: String? = null
    /**
     * 推送状态：1：用户未查看  2：用户已查看
     */
    var state: Int? = null
    /**
     * 品牌id
     */
    var brandIdenty: Long? = null
    /**
     * 门店id
     */
    var shopIdenty: Long? = null


    override fun toString(): String {
        return "PushMessageCustomerEntity{" +
        ", id=" + id +
        ", type=" + type +
        ", customerId=" + customerId +
        ", wxOpenId=" + wxOpenId +
        ", relationId=" + relationId +
        ", heading=" + heading +
        ", imgUrl=" + imgUrl +
        ", message=" + message +
        ", state=" + state +
        ", brandIdenty=" + brandIdenty +
        ", shopIdenty=" + shopIdenty +
        "}"
    }
}
