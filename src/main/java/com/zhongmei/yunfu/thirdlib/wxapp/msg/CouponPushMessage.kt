package com.zhongmei.yunfu.thirdlib.wxapp.msg

/**
 * 优惠活动推送
 */
class CouponPushMessage : WxTempMsg(msgType_CouponPush) {

    var sendDate: Long? = null
    var endDate: Long? = null
    var productName: String? = null
    var notes: String? = null
}
