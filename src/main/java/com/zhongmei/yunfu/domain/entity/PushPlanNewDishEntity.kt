package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;
import com.zhongmei.yunfu.domain.enums.StatusFlag
import com.zhongmei.yunfu.service.LoginManager

/**
 * <p>
 * 新品推送方案
 * </p>
 *
 * @author yangyp
 * @since 2018-10-01
 */
@TableName("push_plan_new_dish")
class PushPlanNewDishEntity : BaseEntity() {

    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 品牌id
     */
    var brandIdentity: Long? = null
    /**
     * 门店id
     */
    var shopIdentity: Long? = null
    /**
     * 方案名称
     */
    var name: String? = null
    /**
     * 活动简介
     */
    var planDesc: String? = null
    /**
     * 菜品Id
     */
    var dishId: Long? = null
    /**
     * 菜品名称
     */
    var dishName: String? = null
    /**
     * 菜品价格
     */
    var dishPrice: BigDecimal? = null
    /**
     * 菜品备注
     */
    var dishRemark: String? = null
    /**
     * 1, 进行中;2, 停止; 3：品牌下发未接受 4：品牌下发已接受  5：数据刷新未接受
     */
    var planState: Int? = null
    /**
     * 活动开始时间
     */
    var beginTime: Date? = null
    /**
     * 活动结束时间
     */
    var endTime: Date? = null
    /**
     * 活动描述
     */
    var describe: String? = null
    /**
     * 活动图片
     */
    var imgUrl: String? = null
    /**
     * 如为门店自建时表示浏览次数；如为品牌创建时表示下发投放门店数
     */
    var scanNumber: Int? = null
    /**
     * 分享次数
     */
    var shareNumber: Int? = null
    /**
     * 活动来源类型 1：品牌创建  2：门店自建 3:品牌下发
     */
    var sourceType: Int? = null
    /**
     * 活动来源Id
     */
    var sourceId: Long? = null


    override fun toString(): String {
        return "PushPlanNewDishEntity{" +
        ", id=" + id +
        ", brandIdentity=" + brandIdentity +
        ", shopIdentity=" + shopIdentity +
        ", name=" + name +
        ", planDesc=" + planDesc +
        ", dishId=" + dishId +
        ", dishName=" + dishName +
        ", dishPrice=" + dishPrice +
        ", dishRemark=" + dishRemark +
        ", planState=" + planState +
        ", beginTime=" + beginTime +
        ", endTime=" + endTime +
        ", describe=" + describe +
        ", imgUrl=" + imgUrl +
        ", scanNumber=" + scanNumber +
        ", shareNumber=" + shareNumber +
        ", sourceType=" + sourceType +
        ", sourceId=" + sourceId +
        "}"
    }

    fun cloneEntity(oldEntity: PushPlanNewDishEntity, shopIdentity: Long?, sourceId: Long?): PushPlanNewDishEntity {
        val newEntity = PushPlanNewDishEntity()

        newEntity.brandIdentity = oldEntity.brandIdentity
        newEntity.shopIdentity = shopIdentity
        newEntity.name = oldEntity.dishName
        newEntity.statusFlag = StatusFlag.VALiD.value()
        newEntity.planDesc = oldEntity.planDesc
        newEntity.dishId = oldEntity.dishId
        newEntity.dishName = oldEntity.dishName
        newEntity.dishPrice = oldEntity.dishPrice
        newEntity.dishRemark = oldEntity.dishRemark
        newEntity.planState = oldEntity.planState
        newEntity.beginTime = oldEntity.beginTime
        newEntity.endTime = oldEntity.endTime
        newEntity.describe = oldEntity.describe
        newEntity.imgUrl = oldEntity.imgUrl
        newEntity.scanNumber = 0
        newEntity.shareNumber = 0

        newEntity.creatorId = LoginManager.get().user!!.creatorId
        newEntity.creatorName = LoginManager.get().user!!.creatorName

        newEntity.updatorId = LoginManager.get().user!!.creatorId
        newEntity.updatorName = LoginManager.get().user!!.creatorName
        newEntity.serverCreateTime = Date()
        newEntity.serverUpdateTime = Date()
        newEntity.sourceType = 3
        newEntity.sourceId = sourceId

        return newEntity
    }
}
