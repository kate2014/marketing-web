package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhongmei.yunfu.domain.entity.AuthPermissionEntity;
import com.zhongmei.yunfu.domain.mapper.AuthPermissionMapper;
import com.zhongmei.yunfu.service.AuthPermissionService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author yangyp
 * @since 2018-08-26
 */
@Service
public class AuthPermissionServiceImpl extends ServiceImpl<AuthPermissionMapper, AuthPermissionEntity> implements AuthPermissionService {

    @Override
    public List<AuthPermissionEntity> queryAuthPermission() throws Exception {
        EntityWrapper<AuthPermissionEntity> eWrapper = new EntityWrapper<>();

        eWrapper.eq("status_flag", 1);
        List<AuthPermissionEntity> listData = selectList(eWrapper);
        return listData;
    }
}
