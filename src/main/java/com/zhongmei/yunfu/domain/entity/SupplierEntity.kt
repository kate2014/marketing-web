package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 菜品属性表
 * </p>
 *
 * @author yangyp
 * @since 2018-10-01
 */
@TableName("supplier")
class SupplierEntity : BaseEntity() {

    @TableId(value = "id", type = IdType.AUTO)
    var id:Long? = null

    var name: String? = null

    var address: String? = null

    var contacts: String? = null

    var contactsPhone:String? = null

    var shopIdenty:Long? = null

    var brandIdenty:Long? = null

    override fun toString(): String {
        return "SupplierEntity{" +
        ", id=" + id +
        ", name=" + name +
        ", address=" + address +
        ", name=" + name +
        ", contacts=" + contacts +
        ", contactsPhone=" + contactsPhone +
        ", brandIdenty=" + brandIdenty +
        ", shopIdenty=" + shopIdenty +
        "}"
    }
}
