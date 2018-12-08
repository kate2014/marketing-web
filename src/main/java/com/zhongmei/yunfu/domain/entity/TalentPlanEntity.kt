package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * <p>
 * 
 * </p>
 *
 * @author yangyp
 * @since 2018-10-01
 */
@TableName("talent_plan")
class TalentPlanEntity : BaseEntity() {

    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 方案名称
     */
    var planName: String? = null
    /**
     * 方案类型
     */
    var planType: Int? = null
    /**
     * 方案状态
     */
    var planState: Int? = null
    /**
     * 提成方式
     */
    var planMode: Int? = null
    /**
     * 门店ID
     */
    var shopIdenty: Long? = null
    /**
     * 品牌标识
     */
    var brandIdenty: Long? = null
    /**
     * 启用时间戳
     */
    var enabledTime: Date? = null
    /**
     * 启用停用标识 : 区别与StatusFlag，启用停用的作用是该数据是有效数据，但是被停用。 1:启用;2:停用
     */
    var enabledFlag: Int? = null
    var planPype: Long? = null


    override fun toString(): String {
        return "TalentPlanEntity{" +
        ", id=" + id +
        ", planName=" + planName +
        ", planType=" + planType +
        ", planState=" + planState +
        ", planMode=" + planMode +
        ", shopIdenty=" + shopIdenty +
        ", brandIdenty=" + brandIdenty +
        ", enabledTime=" + enabledTime +
        ", enabledFlag=" + enabledFlag +
        ", planPype=" + planPype +
        "}"
    }
}
