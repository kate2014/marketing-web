package com.zhongmei.yunfu.controller.api.model

/**
 * 用户反馈请求体
 */
class WxFeedbackReq {

    var shopIdenty: Long? = null

    var brandIdenty: Long? = null
    /**
     * 反馈类型 1：订单反馈，2：门店反馈建议
     */
    var type: Int? = null
    /**
     * 门店回复关联Id
     */
    var relateId: Long? = null
    /**
     * 反馈信息
     */
    var content: String? = null
    /**
     * 订单Id
     */
    var tradeId: Long? = null
    /**
     * 顾客Id
     */
    var customerId: Long? = null
    /**
     * 电话号码
     */
    var mobile: String? = null
    /**
     * 顾客名称
     */
    var customerName: String? = null
    /**
     * 服务员Id
     */
    var userId: Long? = null
    /**
     * 服务员名称
     */
    var userName: String? = null

    /**
     * 反馈类型 1：订单评星，2：门店评星
     */
    var starType: Integer? = null
    /**
     * 评分标签Id
     */
    var starLableId: Long? = null
    /**
     * 评分标签
     */
    var starLable: String? = null
    /**
     * 服务环境评分等级
     */
    var hj: Int? = null
    /**
     * 服务效果评分等级
     */
    var xg: Int? = null
    /**
     * 服务态度评分等级
     */
    var td: Int? = null
}
