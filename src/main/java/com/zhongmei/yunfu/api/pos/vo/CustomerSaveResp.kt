package com.zhongmei.yunfu.api.pos.vo

import java.math.BigDecimal

class CustomerSaveResp {
    /**
     * 会员识别号
     */
    var uniqueCode: String? = null

    /**
     * 顾客id
     */
    var customerID: String? = null

    /**
     * 商户id
     */
    var commercialID: String? = null

    /**
     * 品牌id
     */
    var brandId: String? = null

    /**
     * 发票抬头
     */
    var invoice: String? = null

    /**
     * 洗好
     */
    var birthday: String? = null

    /**
     * 备注
     */
    var memo: String? = null

    /**
     * 会员名称
     */
    var name: String? = null

    /**
     * 成为会员时间
     */
    var upgradeTime: String? = null

    /**
     * 用户喜好
     */
    var environmentHobby: String? = null

    /**
     * 品牌id
     */
    var createDateTime: String? = null

    /**
     * 性别
     */
    private var sex: Int? = null

    /**
     * 地址
     */
    var address: String? = null

    /**
     * 分组
     */
    var groupId: String? = null

    /**
     * 停用
     */
    var isDisable: String? = null

    /**
     * 级别
     */
    var levelId: String? = null

    /**
     * 服务器创建时间
     */
    var serverCreateTime: String? = null

    /**
     * 服务器更新时间
     */
    var serverUpdateTime: String? = null

    /**
     * 积分
     */
    var integral: BigDecimal? = null

    /**
     * 余额
     */
    var remainValue: BigDecimal? = null

    /**
     * 手机
     */
    var mobile: String? = null

    /**
     * 固话
     */
    var tel: String? = null

    /**
     * 分机
     */
    var ext: String? = null

    /**
     * 邮箱
     */
    var email: String? = null

    /**
     * 门店名称
     */
    var commercialName: String? = null

    /**
     * 操作人Id
     */
    var userId: String? = null

    /**
     * 来源
     */
    var source: String? = null

    /**
     * 会员等级
     */
    var levelName: String? = null

    /**
     * 会员消费密码（md5）
     */
    var consumePwd: String? = null
    /**
     * 同步
     */
    var synflag: String? = null

}
