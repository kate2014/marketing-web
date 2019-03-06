package com.zhongmei.yunfu.dubbo.service.consumer;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.zhongmei.yunfu.domain.entity.AuthUserEntity;
import com.zhongmei.yunfu.dubbo.service.UserService;

@Service
public class DemoService {

    @Reference(timeout = 10000)
    UserService userService;

    public void sayHello(String name) {
        AuthUserEntity authUserEntity = userService.saveUser(null);
        System.out.println("authUserEntity: " + authUserEntity);
    }
}
