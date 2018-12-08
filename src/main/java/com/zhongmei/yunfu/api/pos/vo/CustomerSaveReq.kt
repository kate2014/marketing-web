package com.zhongmei.yunfu.api.pos.vo

import com.zhongmei.yunfu.api.PosReq

class CustomerSaveReq : PosReq() {

    var customerId: Long? = null
    var userId: Long? = null
    var userName: String? = null
    var source: Int? = 1//顾客来源：1为Calm，2为手机app，3为其他系统导入，4为微信，5支付宝，6商家官网，7百度，8 后台，9百度外卖，10饿了么，11美团外卖，12大众点评，13熟客,14糯米点菜,15os mobile，16开放平台，17自助
    var nation: String? = null//国家英文名称(为空默认中国)
    var country: String? = null//国家中文名称(为空默认中国)
    var nationalTelCode: String? = null//电话国际区码(为空默认中国)
    var mobile: String? = null//顾客手机号码
    var name: String? = null//顾客名称
    var consumePassword: String? = null//消费密码（6位整数或者MD5加密,为空则随机密码，随机密码会短信通知顾客）
    var faceCode: String? = null//人脸识别标志(理论上是唯一)
    var birthday: String? = null//生日（毫秒级时间戳），为空时默认为18年前的当天
    var sex: Int? = null
    var address: String? = null
    var groupId: Long? = null//顾客分组ID
    var groupName: String? = null//顾客分组ID
    var environmentHobby: String? = null
    var invoiceTitle: String? = null
    var memo: String? = null
}
