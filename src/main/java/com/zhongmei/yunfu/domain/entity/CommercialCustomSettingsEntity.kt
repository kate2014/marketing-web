package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 商户设置表
 * </p>
 *
 * @author yangyp
 * @since 2018-10-01
 */
@TableName("commercial_custom_settings")
class CommercialCustomSettingsEntity : BaseEntity() {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 品牌id
     */
    var brandIdenty: Long? = null
    /**
     * 商户id
     */
    var shopIdenty: Long? = null
    /**
     * 设置type :1门店设置，2,微信小程序，3，会员设置
     */
    var type: Long? = null
    /**
     * key
     */
    var settingKey: String? = null
    /**
     * value
     */
    var settingValue: String? = null


    override fun toString(): String {
        return "CommercialCustomSettingsEntity{" +
        ", id=" + id +
        ", brandIdenty=" + brandIdenty +
        ", shopIdenty=" + shopIdenty +
        ", type=" + type +
        ", settingKey=" + settingKey +
        ", settingValue=" + settingValue +
        "}"
    }
}
