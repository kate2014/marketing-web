package com.zhongmei.yunfu.domain.entity

import com.baomidou.mybatisplus.annotations.TableId
import com.baomidou.mybatisplus.annotations.TableName
import com.baomidou.mybatisplus.enums.IdType
import java.util.*

/**
 *
 *

 *

 * @author yangyp
 * *
 * @since 2018-11-09
 */
@TableName("wx_form")
class WxFormEntity /*extends BaseEntity*/ {

    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    var formId: String? = null
    var openId: String? = null
    var customerId: Long? = null
    var status: Int? = null
    var shopIdenty: Long? = null
    var brandIdenty: Long? = null
    /**
     * 服务器创建时间
     */
    var serverCreateTime: Date? = null

    override fun toString(): String {
        return "WxFormEntity{" +
                ", id=" + id +
                ", formId=" + formId +
                ", openId=" + openId +
                ", customerId=" + customerId +
                ", status=" + status +
                ", shopIdenty=" + shopIdenty +
                ", brandIdenty=" + brandIdenty +
                ", serverCreateTime=" + serverCreateTime +
                "}"
    }
}
