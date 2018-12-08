package com.zhongmei.yunfu.thirdlib.wxapp.msg

import java.math.BigDecimal

/**
 * 订单支付成功
 */
class OrderPayMessage : WxTempMsg() {

    var tradePayAmount: BigDecimal? = null
    var tradeNo: String? = null
    var payDate: String? = null
    var dishList: String? = null
}
