package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 预订表
 * </p>
 *
 * @author yangyp
 * @since 2018-10-01
 */
@TableName("booking")
class BookingEntity : BaseEntity() {

    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    var uuid: String? = null
    /**
     * 顾客id
     */
    var commercialId: Long? = null
    /**
     * 顾客名称
     */
    var commercialName: String? = null
    /**
     * 顾客性别 1:男 2:女
     */
    var commercialGender: Long? = null
    /**
     * 顾客联系方式
     */
    var commercialPhone: String? = null
    /**
     * 预定时间段开始时间
     */
    var startTime: Date? = null
    /**
     * 预定的时间段结束时间
     */
    var endTime: Date? = null
    /**
     * 桌台id
     */
    var tableId: String? = null
    /**
     * 桌台名称
     */
    var tableName: String? = null
    /**
     * 预定人数
     */
    var cousterNum: Int? = null
    /**
     * 订单状态,-1:用户未到店,1:用户到店,2:用户离店,9:已取消,-2:未处理,-3:已拒绝,-4:逾期未到店
     */
    var orderStatus: Int? = null
    /**
     * 备注
     */
    var remark: String? = null
    /**
     * 取消预订时间
     */
    var cancelOrderTime: Date? = null
    /**
     * 订单来源 1：pos ，2：微信小程序
     */
    var bookingSource: Int? = null
    /**
     * 1 美业
     */
    var bookingType: Int? = null
    /**
     * 预订确认状态. 0:未确认 1:已确认
     */
    var confirmed: Int? = null
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
     * 本地创建时间
     */
    var clientCreateTime: Date? = null
    /**
     * 本地更新时间
     */
    var clientUpdateTime: Date? = null


    override fun toString(): String {
        return "BookingEntity{" +
        ", id=" + id +
        ", uuid=" + uuid +
        ", commercialId=" + commercialId +
        ", commercialName=" + commercialName +
        ", commercialGender=" + commercialGender +
        ", commercialPhone=" + commercialPhone +
        ", startTime=" + startTime +
        ", endTime=" + endTime +
        ", tableId=" + tableId +
        ", tableName=" + tableName +
        ", cousterNum=" + cousterNum +
        ", orderStatus=" + orderStatus +
        ", remark=" + remark +
        ", cancelOrderTime=" + cancelOrderTime +
        ", bookingSource=" + bookingSource +
        ", bookingType=" + bookingType +
        ", confirmed=" + confirmed +
        ", brandIdenty=" + brandIdenty +
        ", shopIdenty=" + shopIdenty +
        ", deviceIdenty=" + deviceIdenty +
        ", clientCreateTime=" + clientCreateTime +
        ", clientUpdateTime=" + clientUpdateTime +
        "}"
    }
}
