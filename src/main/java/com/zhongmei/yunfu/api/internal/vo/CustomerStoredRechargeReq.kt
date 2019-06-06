package com.zhongmei.yunfu.api.internal.vo

import java.math.BigDecimal

class CustomerStoredRechargeReq : CustomerBalanceReq() {
    var giveAmout: BigDecimal? = null //赠送金额
}