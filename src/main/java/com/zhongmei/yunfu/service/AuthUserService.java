package com.zhongmei.yunfu.service;

import com.baomidou.mybatisplus.service.IService;
import com.zhongmei.yunfu.domain.entity.AuthPermissionEntity;
import com.zhongmei.yunfu.domain.entity.AuthUserEntity;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author pigeon88
 * @since 2018-08-26
 */
public interface AuthUserService extends IService<AuthUserEntity> {

    AuthUserEntity login(String account, String password, Long shopId);

    AuthUserEntity tokenLogin(String token);

    /**
     * 获取用户权限
     *
     * @param account
     * @return
     */
    List<AuthPermissionEntity> getAuthPermissionEntityBy(String account);
}
