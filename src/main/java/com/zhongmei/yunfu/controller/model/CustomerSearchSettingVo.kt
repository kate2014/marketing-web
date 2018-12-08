package com.zhongmei.yunfu.controller.model

class CustomerSearchSettingVo {

    var shopIdenty: Long? = null
    var brandIdenty: Long? = null
    var creatorId: Long? = null
    var creatorName: String? = null
    var consumptionMainDay: Int? = null //消费主力群
    var consumptionMainAmount: Int? = null //
    var consumptionMainNumber: Int? = null
    var membersWillDay: Int? = null //将流失会员
    var membersWillAmount: Int? = null
    var membersWillNumber: Int? = null
    var membersLossDay: Int? = null //已流失会员
    var membersLossAmount: Int? = null
    var membersLossNumber: Int? = null
    var membersNewIntervalDay: Int? = null //新会员 多少天为新会员
    var membersBirthdayBeforeDay: Int? = null //将过生日会员  提前多少天
    var membersAnniversaryBeforeDay: Int? = null //满周年会员 提前多少天

}