package com.zhongmei.yunfu.thirdlib.wxapp.msg

/**
 * 消息模板基类
 */
abstract class WxTempMsg {

    var shopIdenty: Long? = null
    var brandIdenty: Long? = null
    var customerId: Long? = null
    //var openId: String? = null
    var msgType: Int? = null //消信类型

    companion object {
        const val msgType_OrderPay = 1
        const val msgType_CouponPush = 2
        const val msgCollage_fail = 3
    }
}
