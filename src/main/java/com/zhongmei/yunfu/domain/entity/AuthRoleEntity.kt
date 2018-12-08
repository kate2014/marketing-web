package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 角色表 : 商户角色信息
 * </p>
 *
 * @author yangyp
 * @since 2018-10-01
 */
@TableName("auth_role")
class AuthRoleEntity : BaseEntity() {

    /**
     * 自增主键 : 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 角色名称 : 角色名称
     */
    var name: String? = null
    /**
     * 角色编码
     */
    var code: String? = null
    /**
     * 排序 : 排序
     */
    var sort: Int? = null
    /**
     * 品牌标识 : 品牌标识
     */
    var brandIdenty: Long? = null
    /**
     * 1:用户(b.kry用户创建,)
2:系统(b.kry不可见,系统自动创建,如营销发布员)
3:品牌(只读模式,系统自动创建如admin)
     */
    var sourceFlag: Int? = null
    /**
     * 启用停用标识 : 1:启用;2:停用
     */
    var enableFlag: Int? = null
    /**
     * 是否允许经销商创建该角色类型的账户1:是2:否
     */
    var isCreateAccountByDealer: Int? = null
    /**
     * 是否允许门店创建该角色类型的账户1:是2:否(只有当isCreateAccountByDealer为1时显示)
     */
    var isCreateAccountByShop: Int? = null
    var shopIdenty: Long? = null


    override fun toString(): String {
        return "AuthRoleEntity{" +
        ", id=" + id +
        ", name=" + name +
        ", code=" + code +
        ", sort=" + sort +
        ", brandIdenty=" + brandIdenty +
        ", sourceFlag=" + sourceFlag +
        ", enableFlag=" + enableFlag +
        ", isCreateAccountByDealer=" + isCreateAccountByDealer +
        ", isCreateAccountByShop=" + isCreateAccountByShop +
        ", shopIdenty=" + shopIdenty +
        "}"
    }
}
