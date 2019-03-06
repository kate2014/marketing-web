package com.zhongmei.yunfu.dubbo.service;

import com.zhongmei.yunfu.domain.entity.AuthUserEntity;

public interface UserService {

    AuthUserEntity saveUser(AuthUserEntity user);
}
