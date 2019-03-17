package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 品牌表
 * </p>
 *
 * @author yangyp
 * @since 2018-10-01
 */
@TableName("brand")
class BrandEntity : BaseEntity() {

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 商户组名称
     */
    var name: String? = null
    /**
     * 商户组logo
     */
    var logo: String? = null
    /**
     * 商户组联系人
     */
    var contacts: String? = null
    /**
     * 联系人电话
     */
    var contactsPhone: String? = null
    /**
     * 联系人邮箱
     */
    var contactsMail: String? = null
    /**
     * 商户组地址
     */
    var commercialGroupAdress: String? = null
    /**
     * 状态：0有效 -1无效
     */
    var status: Int? = null
    /**
     * 父ID=0时，代表的是集团
     */
    var parentId: Long? = null
    /**
     * 类型 0代表集团 1代表公司
     */
    var type: Int? = null
    /**
     * 省份
     */
    var province: String? = null
    /**
     * 城市
     */
    var city: String? = null
    /**
     * 区域
     */
    var area: String? = null


    override fun toString(): String {
        return "BrandEntity{" +
                ", id=" + id +
                ", name=" + name +
                ", logo=" + logo +
                ", contacts=" + contacts +
                ", contactsPhone=" + contactsPhone +
                ", contactsMail=" + contactsMail +
                ", commercialGroupAdress=" + commercialGroupAdress +
                ", status=" + status +
                ", parentId=" + parentId +
                ", type=" + type +
                ", province=" + province +
                ", city=" + city +
                ", area=" + area +
                "}"
    }
}
