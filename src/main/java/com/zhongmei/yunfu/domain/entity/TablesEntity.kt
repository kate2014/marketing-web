package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 桌台表
 * </p>
 *
 * @author pigeon88
 * @since 2018-10-01
 */
@TableName("tables")
class TablesEntity : BaseEntity() {

    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 自定义桌台编号
     */
    var tableNum: String? = null
    /**
     * 品牌id
     */
    var brandIdenty: Long? = null
    /**
     * 商户id
     */
    var shopIdenty: Long? = null
    var tableName: String? = null
    /**
     * 修改人
     */
    var updaterId: Long? = null
    /**
     * 创建人
     */
    var updaterName: String? = null
    /**
     * 顺序
     */
    var sort: Int? = null
    /**
     * 桌台的状态1空闲，2 占用
     */
    var tableStatus: Int? = null
    /**
     * 区域id
     */
    var areaId: Long? = null
    /**
     * 是否可预订
     */
    var canBooking: Int? = null
    var brandIdentity: Long? = null
    var shopIdentity: Long? = null


    override fun toString(): String {
        return "TablesEntity{" +
        ", id=" + id +
        ", tableNum=" + tableNum +
        ", brandIdenty=" + brandIdenty +
        ", shopIdenty=" + shopIdenty +
        ", tableName=" + tableName +
        ", updaterId=" + updaterId +
        ", updaterName=" + updaterName +
        ", sort=" + sort +
        ", tableStatus=" + tableStatus +
        ", areaId=" + areaId +
        ", canBooking=" + canBooking +
        ", brandIdentity=" + brandIdentity +
        ", shopIdentity=" + shopIdentity +
        "}"
    }
}
