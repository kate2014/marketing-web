package com.zhongmei.yunfu.controller.model.base

interface IShopIdenty {

    /**
     * 如果归属门店，则为门店id；如果归属品牌，则为品牌id.
    新的权限体系下，全部为品牌id
    就是登录标示!!仅登录使用
     */
    var shopIdenty: Long?
        get() = null
        set(value) = TODO()
    /**
     * 品牌标识 : 品牌标识
     */
    var brandIdenty: Long?
        get() = null
        set(value) = TODO()
}