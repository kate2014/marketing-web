package com.zhongmei.yunfu.thirdlib.wxapp.msg

import java.math.BigDecimal

/**
 * 活动购买成功
 */
class ActivityBuyMessage : WxTempMsg(WxTempMsg.activity_buy) {

    var tradeNo: String? = null
    var code: String? = null
    var tradePayAmount: BigDecimal? = null
    var dishName: String? = null
    var validityPeriod: String? = null
    var buyDate: String? = null
    var shopName: String? = null
    var remarks: String? = null

}
