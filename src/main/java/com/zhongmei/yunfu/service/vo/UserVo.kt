package com.zhongmei.yunfu.service.vo

import com.zhongmei.yunfu.domain.entity.*

class UserVo {
    lateinit var authUser: AuthUserEntity
    lateinit var brand: BrandEntity
    lateinit var shop: CommercialEntity
    lateinit var authRole: AuthRoleEntity
    var authPermission: AuthPermissionEntity? = null
}
