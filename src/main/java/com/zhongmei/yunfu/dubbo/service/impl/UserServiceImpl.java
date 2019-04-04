package com.zhongmei.yunfu.dubbo.service.impl;

//import com.alibaba.dubbo.config.annotation.Service;
import com.zhongmei.yunfu.domain.entity.AuthUserEntity;
import com.zhongmei.yunfu.dubbo.service.UserService;

import java.util.List;
import java.util.Map;

//@Service
public class UserServiceImpl implements UserService {

    @Override
    public AuthUserEntity saveUser(AuthUserEntity user) {
        AuthUserEntity authUserEntity = new AuthUserEntity();
        authUserEntity.setAccount("dubbo");
        authUserEntity.setName("dubbo");

        //List<String> aa = ["",""];
        //Map<String,Integer> = {"":11};
        return authUserEntity;
    }
}
