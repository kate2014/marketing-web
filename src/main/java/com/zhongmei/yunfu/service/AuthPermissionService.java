package com.zhongmei.yunfu.service;

import com.baomidou.mybatisplus.service.IService;
import com.zhongmei.yunfu.domain.entity.AuthPermissionEntity;

import java.util.List;

/**
 * <p>
 * 权限 服务类
 * </p>
 *
 * @author yangyp
 * @since 2018-08-26
 */
public interface AuthPermissionService extends IService<AuthPermissionEntity> {

    /**
     * 获取所有权限
     * @return
     * @throws Exception
     */
    List<AuthPermissionEntity> queryAuthPermission()throws Exception;
}
