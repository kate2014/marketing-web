package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 不同角色对于的人效方案
 * </p>
 *
 * @author pigeon88
 * @since 2018-10-01
 */
@TableName("talent_role")
class TalentRoleEntity : BaseEntity() {

    /**
     * 自增主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    var roleId: Long? = null
    var planId: Long? = null
    /**
     * 如果归属门店，则为门店id；如果归属品牌，则为       品牌id.
新的权限体系下，全部为品牌id
就是登录标示!!仅登录使用
     */
    var shopIdenty: Long? = null
    /**
     * 品牌标识 : 品牌标识
     */
    var brandIdenty: Long? = null


    override fun toString(): String {
        return "TalentRoleEntity{" +
        ", id=" + id +
        ", roleId=" + roleId +
        ", planId=" + planId +
        ", shopIdenty=" + shopIdenty +
        ", brandIdenty=" + brandIdenty +
        "}"
    }
}
