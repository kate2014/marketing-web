package com.zhongmei.yunfu.controller.model

import com.zhongmei.yunfu.controller.model.base.WebBaseModel

class CustomerEditModel : WebBaseModel() {
    var id: Long? = null
    var name: String? = null
    var birthday: String? = null
    var gender: Int? = null
    var groupLevel: String? = null
    var mobile: String? = null
    var telephone: String? = null
    var address: String? = null
    var hobby: String? = null
    var profile: String? = null
    var password: String? = null
    var checkPassword: String? = null
}