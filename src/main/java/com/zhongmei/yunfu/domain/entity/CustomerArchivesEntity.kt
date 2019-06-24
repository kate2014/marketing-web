package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * 会员档案
 */
@TableName("customer_archives")
class CustomerArchivesEntity : BaseEntity() {

    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    var type: Int? = null
    var title: String? = null
    var content: String? = null
    var customerId: Long? = null
    var shopIdenty: Long? = null
    var brandIdenty: Long? = null

    override fun toString(): String {
        return "AppConfigEntity{" +
                ", id=" + id +
                ", type=" + type +
                ", title=" + title +
                ", content=" + content +
                ", customerId=" + customerId +
                ", shopIdenty=" + shopIdenty +
                ", brandIdenty=" + brandIdenty +
                "}"
    }
}
