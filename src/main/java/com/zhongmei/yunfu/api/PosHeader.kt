package com.zhongmei.yunfu.api

interface IPosHeader {
    fun getHeader(): PosHeader?
    fun setHeader(header: PosHeader)
}

class PosHeader {

    //yf-api-msgid
    var msgId: String? = null

    //yf-api-device-id
    var deviceId: String? = null

    //yf-api-brand-id
    var brandId: Long? = null

    //yf-api-shop-id
    var shopId: Long? = null

}
