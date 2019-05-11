package com.zhongmei.yunfu.thirdlib.wxapp.msg

/**
 * 订单支付成功
 */
class ColloageFailMessage : WxTempMsg(WxTempMsg.msgCollage_fail) {

    var tradeNo: String? = null
    var collageName: String? = null
    var tradeAmount: String? = null
    var joinCount: String? = null
    var finishCount: String? = null
    var endTime: String? = null
    var collageStart: String? = null
    var failMessage: String? = null
    var tradeStart: String? = null
}
