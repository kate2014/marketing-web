package com.zhongmei.yunfu.controller.model

import com.zhongmei.yunfu.controller.model.base.WebBaseModel

class CustomerSearchModel : WebBaseModel() {

    var type: String? = null
    var sourceId: Int? = null
    var groupLevelId: Long? = null
    var name: String? = null
    var mobile: String? = null

    companion object {
        const val consumptionMainCount      = "consumptionMainCount"
        const val membersWillCount          = "membersWillCount"
        const val membersLossCount          = "membersLossCount"
        const val membersNewIntervalCount   = "membersNewIntervalCount"
        const val membersBirthdayCount      = "membersBirthdayCount"
        const val membersAnniversaryCount   = "membersAnniversaryCount"
    }
}
