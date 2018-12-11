package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 商户信息表
 * </p>
 *
 * @author yangyp
 * @since 2018-10-01
 */
@TableName("commercial")
class CommercialEntity : BaseEntity() {

    /**
     * 主键id
     */
    var commercialId: Long? = null
    var commercialName: String? = null
    /**
     * 联系人
     */
    var commercialContact: String? = null
    /**
     * 联系电话
     */
    var commercialPhone: String? = null
    var commercialAdress: String? = null
    /**
     * 商户描述
     */
    var commercialDesc: String? = null
    /**
     * LOGO对应的URL
     */
    var commercialLogo: String? = null
    /**
     * 商户状态 0-可用 -1-不可用,1-预装
     */
    var status: Int? = null
    /**
     * 作废状态:1 正常 2 已作废
     */
    var invalidStatus: Int? = null
    /**
     * 所属品牌id
     */
    var brandId: Long? = null
    /**
     * 分店名称
     */
    var branchName: String? = null
    /**
     * 营业时间
     */
    var openTime: String? = null
    /**
     * 人均消费
     */
    var consumePerson: String? = null
    /**
     * 商家设备类型，0:pad 1:phone
     */
    var deviceType: Int? = null
    /**
     * 经度
     */
    var longitude: String? = null
    /**
     * 纬度
     */
    var latitude: String? = null


    override fun toString(): String {
        return "CommercialEntity{" +
        ", commercialId=" + commercialId +
        ", commercialName=" + commercialName +
        ", commercialContact=" + commercialContact +
        ", commercialPhone=" + commercialPhone +
        ", commercialAdress=" + commercialAdress +
        ", commercialDesc=" + commercialDesc +
        ", commercialLogo=" + commercialLogo +
        ", status=" + status +
        ", invalidStatus=" + invalidStatus +
        ", brandId=" + brandId +
        ", branchName=" + branchName +
        ", openTime=" + openTime +
        ", consumePerson=" + consumePerson +
        ", deviceType=" + deviceType +
        ", longitude=" + longitude +
        ", latitude=" + latitude +
        "}"
    }
}
