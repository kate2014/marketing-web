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
 * @author pigeon88
 * @since 2018-10-01
 */
@TableName("pos_sync_config")
class PosSyncConfigEntity : BaseEntity() {

    @TableId(value = "id", type = IdType.AUTO)
    var id: Int? = null
    /**
     * 需要同步的DB的真实表名
     */
    var syncTable: String? = null
    /**
     * 在上行接口中标注的需要同步的表名
     */
    var requestTable: String? = null
    /**
     * 需要同步最近几天的数据
     */
    var syncRecentDays: Int? = null
    /**
     * 是否需要按照shop_identy字段过滤，Y--需要，N--不需要
     */
    var filterShopIdenty: String? = null
    /**
     * 是否需要按照brand_identy过滤，Y需要，N不需要
     */
    var filterBrandIdenty: String? = null


    override fun toString(): String {
        return "PosSyncConfigEntity{" +
        ", id=" + id +
        ", syncTable=" + syncTable +
        ", requestTable=" + requestTable +
        ", syncRecentDays=" + syncRecentDays +
        ", filterShopIdenty=" + filterShopIdenty +
        ", filterBrandIdenty=" + filterBrandIdenty +
        "}"
    }
}
