package com.zhongmei.yunfu.controller.model.base

import com.zhongmei.yunfu.domain.entity.AuthUserEntity

abstract class WebBaseModel {
    var user: AuthUserEntity? = null
    var pageNo: Int = 0
    var pageSize: Int = 10
}