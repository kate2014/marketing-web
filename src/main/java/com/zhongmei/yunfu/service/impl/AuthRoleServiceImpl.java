package com.zhongmei.yunfu.service.impl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhongmei.yunfu.domain.entity.AuthRoleEntity;
import com.zhongmei.yunfu.domain.entity.AuthUserEntity;
import com.zhongmei.yunfu.domain.mapper.AuthRoleMapper;
import com.zhongmei.yunfu.service.AuthRoleService;

import java.util.List;

/**
 * <p>
 * 角色表 : 商户角色信息 服务实现类
 * </p>
 *
 * @author yangyp
 * @since 2018-08-26
 */
@Service
public class AuthRoleServiceImpl extends ServiceImpl<AuthRoleMapper, AuthRoleEntity> implements AuthRoleService {

    @Override
    public Boolean addAuthRole(AuthRoleEntity mAuthRoleEntity) throws Exception {
        Boolean isSuccess = insert(mAuthRoleEntity);
        return isSuccess;
    }

    @Override
    public List<AuthRoleEntity> querySystemAuthRole(AuthRoleEntity mAuthRoleEntity) throws Exception {

        EntityWrapper<AuthRoleEntity> eWrapper = new EntityWrapper<>();

        eWrapper.eq("brand_identy", mAuthRoleEntity.getBrandIdenty());
        eWrapper.eq("shop_identy", mAuthRoleEntity.getShopIdenty());
        eWrapper.eq("enable_flag", 1);
        eWrapper.eq("source_flag", 2);
        eWrapper.eq("status_flag", 1);
        List<AuthRoleEntity> listData = selectList(eWrapper);
        return listData;
    }
}
