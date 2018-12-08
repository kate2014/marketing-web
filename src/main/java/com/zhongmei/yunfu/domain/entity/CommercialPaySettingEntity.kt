package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 商户线上支付配置
 * </p>
 *
 * @author pigeon88
 * @since 2018-10-01
 */
@TableName("commercial_pay_setting")
class CommercialPaySettingEntity : BaseEntity() {

    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 应用密钥
     */
    var appsecret: String? = null
    /**
     * 商户标示
     */
    var appid: String? = null
    /**
     * 小程序支付api秘钥
     */
    var apiSecret: String? = null
    /**
     * 小程序支付商户编号
     */
    var wxShopId: String? = null
    /**
     * 密钥文件名称
     */
    var secretFilename: String? = null
    /**
     * 密钥文件路径
     */
    var secretFilepath: String? = null
    /**
     * 数据类型：1 小程序公众号信息  2商户支付信息
     */
    var type: Int? = null
    /**
     * 品牌id
     */
    var brandIdenty: Long? = null
    /**
     * 商户id
     */
    var shopIdenty: Long? = null


    override fun toString(): String {
        return "CommercialPaySettingEntity{" +
                ", id=" + id +
                ", appsecret=" + appsecret +
                ", appid=" + appid +
                ", wxShopId=" + wxShopId +
                ", secretName=" + secretFilename +
                ", secretPath=" + secretFilepath +
                ", type=" + type +
                ", brandIdenty=" + brandIdenty +
                ", shopIdenty=" + shopIdenty +
                "}"
    }
}
