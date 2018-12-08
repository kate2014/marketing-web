package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 
 * </p>
 *
 * @author yangyp
 * @since 2018-10-01
 */
@TableName("shop_device")
class ShopDeviceEntity : BaseEntity() {

    /**
     * 自增主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 设备mac地址
     */
    var deviceMac: String? = null
    /**
     * 设备编号
     */
    var deviceNo: String? = null
    /**
     * 设备类型 1.pos收银机
     */
    var deviceType: Int? = null
    /**
     * 品牌标识
     */
    var brandIdenty: Long? = null
    /**
     * 门店标识
     */
    var shopIdenty: Long? = null


    override fun toString(): String {
        return "ShopDeviceEntity{" +
        ", id=" + id +
        ", deviceMac=" + deviceMac +
        ", deviceNo=" + deviceNo +
        ", deviceType=" + deviceType +
        ", brandIdenty=" + brandIdenty +
        ", shopIdenty=" + shopIdenty +
        "}"
    }
}
