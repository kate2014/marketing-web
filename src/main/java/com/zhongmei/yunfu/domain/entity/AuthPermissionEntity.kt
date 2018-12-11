package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 权限
 * </p>
 *
 * @author yangyp
 * @since 2018-10-01
 */
@TableName("auth_permission")
class AuthPermissionEntity : BaseEntity() {

    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 上级权限id
     */
    var parentId: Long? = null
    /**
     * 权限层级id : 主要是为了构建权限树，三位表示一级。
     */
    var levelId: String? = null
    /**
     * 权限名称 : 菜单的权限名称同菜单名称
     */
    var name: String? = null
    /**
     * 权限编码 : 判断是否有权限的编码
     */
    var code: String? = null
    /**
     * 权限类型 : 1：菜单；2：按钮；3：数据(onpos)
     */
    var type: Int? = null
    /**
     * 连接 : 权限关联的菜单的连接，注意只在菜单下有效
     */
    var url: String? = null
    /**
     * 排序号
     */
    var sort: Int? = null
    /**
     * 平台类型 : 1-云后台；2-POS端
     */
    var platform: Int? = null
    /**
     * 1、V4=4；2、V5 =5
     */
    var supportVersion: Int? = null
    /**
     * 权限来源1-业务权限，2-管理权限
     */
    var sourceFlag: Int? = null
    /**
     * 组织结构类型，1-公司、2-门店、6-配送站、7-公司及门店、8-公司及配送站、9-门店及配送站、10-公司、门店及配送站
     */
    var groupFlag: Int? = null
    /**
     * 是否选中，1,勾选;2,不勾选
     */
    var checked: Int? = null
    /**
     * 分区code 关联枚举值
     */
    var zoneCode: String? = null


    override fun toString(): String {
        return "AuthPermissionEntity{" +
        ", id=" + id +
        ", parentId=" + parentId +
        ", levelId=" + levelId +
        ", name=" + name +
        ", code=" + code +
        ", type=" + type +
        ", url=" + url +
        ", sort=" + sort +
        ", platform=" + platform +
        ", supportVersion=" + supportVersion +
        ", sourceFlag=" + sourceFlag +
        ", groupFlag=" + groupFlag +
        ", checked=" + checked +
        ", zoneCode=" + zoneCode +
        "}"
    }
}
