package com.zhongmei.yunfu.service;

import com.baomidou.mybatisplus.service.IService;
import com.zhongmei.yunfu.domain.entity.AuthRoleEntity;
import com.zhongmei.yunfu.domain.entity.AuthUserEntity;
import com.zhongmei.yunfu.erp.model.ERPCommercialModel;

import java.util.List;

/**
 * <p>
 * 角色表 : 商户角色信息 服务类
 * </p>
 *
 * @author yangyp
 * @since 2018-08-26
 */
public interface AuthRoleService extends IService<AuthRoleEntity> {

    /**
     * 创建门店角色
     * @param mAuthRoleEntity
     * @return
     * @throws Exception
     */
    Boolean addAuthRole(AuthRoleEntity mAuthRoleEntity)throws Exception;

    /**
     *
     * @param mAuthRoleEntity
     * @return
     * @throws Exception
     */
    List<AuthRoleEntity> querySystemAuthRole(AuthRoleEntity mAuthRoleEntity)throws Exception;

}
