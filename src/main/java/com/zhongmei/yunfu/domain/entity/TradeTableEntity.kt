package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 仅为数据迁移暂定，后续按产品需求修改
 * </p>
 *
 * @author pigeon88
 * @since 2018-10-01
 */
@TableName("trade_table")
class TradeTableEntity : BaseEntity() {

    /**
     * 服务端自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 订单id
     */
    var tradeId: Long? = null
    /**
     * 订单UUID
     */
    var tradeUuid: String? = null
    /**
     * 关联桌台id
     */
    var tableId: Long? = null
    var tableName: String? = null
    /**
     * 品牌标识
     */
    var brandIdenty: Long? = null
    /**
     * 门店标识
     */
    var shopIdenty: Long? = null
    /**
     * 设备标识
     */
    var deviceIdenty: String? = null
    /**
     * UUID，本笔记录唯一值
     */
    var uuid: String? = null
    /**
     * 顾客 数默认1
     */
    var tablePeopleCount: Int? = null
    /**
     * 备注
     */
    var memo: String? = null
    /**
     * 1空闲，2使用中
     */
    var selfTableStatus: Int? = null
    /**
     * PAD本地创建时间
     */
    var clientCreateTime: Date? = null
    /**
     * PAD本地最后修改时间
     */
    var clientUpdateTime: Date? = null


    override fun toString(): String {
        return "TradeTableEntity{" +
        ", id=" + id +
        ", tradeId=" + tradeId +
        ", tradeUuid=" + tradeUuid +
        ", tableId=" + tableId +
        ", tableName=" + tableName +
        ", brandIdenty=" + brandIdenty +
        ", shopIdenty=" + shopIdenty +
        ", deviceIdenty=" + deviceIdenty +
        ", uuid=" + uuid +
        ", tablePeopleCount=" + tablePeopleCount +
        ", memo=" + memo +
        ", selfTableStatus=" + selfTableStatus +
        ", clientCreateTime=" + clientCreateTime +
        ", clientUpdateTime=" + clientUpdateTime +
        "}"
    }
}
