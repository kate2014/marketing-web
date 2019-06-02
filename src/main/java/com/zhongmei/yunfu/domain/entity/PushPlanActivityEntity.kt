package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;
import com.zhongmei.yunfu.service.LoginManager

/**
 * <p>
 * 活动推送活动方案
 * </p>
 *
 * @author yangyp
 * @since 2018-10-01
 */
@TableName("push_plan_activity")
class PushPlanActivityEntity : BaseEntity() {

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
     * 浏览次数
     */
    var scanNumber: Int? = null
    /**
     * 分享次数
     */
    var shareNumber: Int? = null
    /**
     * 活动来源类型 1：品牌下发  2：门店自建  3:品牌下发
     */
    var sourceType: Int? = null
    /**
     * 活动来源Id
     */
    var sourceId: Long? = null


    override fun toString(): String {
        return "PushPlanActivityEntity{" +
                ", id=" + id +
                ", brandIdentity=" + brandIdentity +
                ", shopIdentity=" + shopIdentity +
                ", name=" + name +
                ", planDesc=" + planDesc +
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

    fun cloneEntity(mainEntity: PushPlanActivityEntity, shopIdenty: Long?, sourceId: Long?): PushPlanActivityEntity {
        val mPushPlanActivity = PushPlanActivityEntity()

        mPushPlanActivity.name = mainEntity.name
        mPushPlanActivity.endTime = mainEntity.beginTime
        mPushPlanActivity.planDesc = mainEntity.planDesc
        mPushPlanActivity.imgUrl = mainEntity.imgUrl
        mPushPlanActivity.describe = mainEntity.describe
        mPushPlanActivity.planState = 3
        mPushPlanActivity.beginTime = mainEntity.beginTime
        mPushPlanActivity.brandIdentity = mainEntity.brandIdentity
        mPushPlanActivity.shopIdentity = shopIdenty
        mPushPlanActivity.scanNumber = 0
        mPushPlanActivity.shareNumber = 0
        mPushPlanActivity.serverCreateTime = mainEntity.serverCreateTime
        mPushPlanActivity.serverUpdateTime = mainEntity.serverUpdateTime
        mPushPlanActivity.statusFlag = 1
        mPushPlanActivity.creatorId = LoginManager.get().user!!.creatorId
        mPushPlanActivity.creatorName = LoginManager.get().user!!.creatorName
        mPushPlanActivity.sourceType = 3
        mPushPlanActivity.sourceId = sourceId

        return mPushPlanActivity
    }
}
