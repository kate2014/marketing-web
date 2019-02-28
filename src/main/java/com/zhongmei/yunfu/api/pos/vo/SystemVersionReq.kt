package com.zhongmei.yunfu.api.pos.vo

import com.zhongmei.yunfu.api.PosReq

class SystemVersionReq : PosReq() {

    var shopId: Long? = null

    var brandId: Long? = null

    var versionCode: Integer? = null
}