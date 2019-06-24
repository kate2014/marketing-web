package com.zhongmei.yunfu.domain.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName
import com.zhongmei.yunfu.domain.entity.base.BaseEntity;

/**
 * 任务提醒
 */
@TableName("task_remind")
class TaskRemindEntity : BaseEntity() {

    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    var title: String? = null
    var content: String? = null
    var remindTime: Date? = null
    var type: Int? = null
    var status: Int? = null
    var customerDocId: Long? = null
    var taskResult: String? = null
    var customerId: Long? = null
    var customerName: String? = null
    var customerMobile: String? = null
    var userId: Long? = null
    var userName: String? = null
    var shopIdenty: Long? = null
    var brandIdenty: Long? = null

    override fun toString(): String {
        return "AppConfigEntity{" +
                ", id=" + id +
                ", title=" + title +
                ", content=" + content +
                ", remindTime=" + remindTime +
                ", type=" + type +
                ", status=" + status +
                ", customerDocId=" + customerDocId +
                ", taskResult=" + taskResult +
                ", customerId=" + customerId +
                ", customerName=" + customerName +
                ", customerMobile=" + customerMobile +
                ", userId=" + userId +
                ", userName=" + userName +
                ", shopIdenty=" + shopIdenty +
                ", brandIdenty=" + brandIdenty +

                "}"
    }
}
