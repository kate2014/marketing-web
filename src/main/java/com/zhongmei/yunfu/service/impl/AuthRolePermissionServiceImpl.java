package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhongmei.yunfu.domain.entity.AuthRolePermissionEntity;
import com.zhongmei.yunfu.domain.mapper.AuthRolePermissionMapper;
import com.zhongmei.yunfu.erp.model.ERPCommercialModel;
import com.zhongmei.yunfu.service.AuthRolePermissionService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色权限关系表 服务实现类
 * </p>
 *
 * @author yangyp
 * @since 2018-08-26
 */
@Service
public class AuthRolePermissionServiceImpl extends ServiceImpl<AuthRolePermissionMapper, AuthRolePermissionEntity> implements AuthRolePermissionService {

    @Override
    public Boolean andAuthRolePermission(AuthRolePermissionEntity mAuthRolePermissionEntity) throws Exception {
        Boolean isSuccess = insert(mAuthRolePermissionEntity);
        return isSuccess;
    }

    @Override
    public Boolean insertBatchData(List<AuthRolePermissionEntity> listData) throws Exception {

        Boolean isSuccess = insertBatch(listData);
        return isSuccess;
    }
}
