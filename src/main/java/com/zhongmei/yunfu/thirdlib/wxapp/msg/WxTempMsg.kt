package com.zhongmei.yunfu.thirdlib.wxapp.msg

/**
 * 消息模板基类
 */
abstract class WxTempMsg(msgType: Int?) {

    var shopIdenty: Long? = null
    var brandIdenty: Long? = null
    var customerId: Long? = null
    //var openId: String? = null
    var msgType: Int? = null //消信类型
        private set

    init {
        this.msgType = msgType
    }

    constructor(msgType: Int?,
                shopIdenty: Long?,
                brandIdenty: Long?,
                customerId: Long?) : this(msgType) {
        this.shopIdenty = shopIdenty
        this.brandIdenty = brandIdenty
        this.customerId = customerId
    }

    companion object {
        const val msgType_OrderPay = 1
        const val msgType_CouponPush = 2
        const val msgCollage_fail = 3
        const val member_charge = 4
        const val service_time_card = 5
        const val activity_buy = 6
    }
}
