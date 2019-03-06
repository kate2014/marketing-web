package com.zhongmei.yunfu.controller.model

import com.zhongmei.yunfu.controller.model.base.WebBaseModel
import java.math.BigDecimal

class CustomerDrainSearchModel : WebBaseModel() {

    var opType: String? = null
    var consumptionLastTime: String? = null
    var storedBalance: BigDecimal? = null
    var cardResidueCount: Int? = null
}
