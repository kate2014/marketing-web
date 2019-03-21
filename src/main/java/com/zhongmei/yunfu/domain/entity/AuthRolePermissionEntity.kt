package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 角色权限关系表
 * </p>
 *
 * @author yangyp
 * @since 2018-10-01
 */
@TableName("auth_role_permission")
class AuthRolePermissionEntity : BaseEntity() {

    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 角色id : 角色id
     */
    var roleId: Long? = null
    /**
     * 权限id
     */
    var permissionId: Long? = null
    /**
     * 品牌标识 : 品牌标识
     */
    var brandIdenty: Long? = null

    var platform: Int? = null
    /**
     * 是门店资源还是品牌资源，1品牌，2门店，3pos收银，4OnMobile
     */
    var groupFlag: Int? = null
    /**
     * 门店标识
     */
    var shopIdenty: Long? = null


    override fun toString(): String {
        return "AuthRolePermissionEntity{" +
        ", id=" + id +
        ", roleId=" + roleId +
        ", permissionId=" + permissionId +
        ", brandIdenty=" + brandIdenty +
        ", platform=" + platform +
        ", groupFlag=" + groupFlag +
        ", shopIdenty=" + shopIdenty +
        "}"
    }
}
