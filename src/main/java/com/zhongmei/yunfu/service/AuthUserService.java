package com.zhongmei.yunfu.service;

import com.baomidou.mybatisplus.service.IService;
import com.zhongmei.yunfu.controller.model.AuthUserModel;
import com.zhongmei.yunfu.domain.entity.AuthPermissionEntity;
import com.zhongmei.yunfu.domain.entity.AuthUserEntity;
import com.zhongmei.yunfu.domain.entity.UserSalaryReport;
import com.zhongmei.yunfu.erp.model.ERPCommercialModel;

import java.util.List;
import java.util.Map;

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
     * @param userId
     * @return
     */
    List<AuthPermissionEntity> getAuthPermissionEntityBy(Long userId);

    /**
     * 获取用户权限
     *
     * @param account
     * @return
     */
    //Map<String, String> getAuthPermissionMapBy(String account, Long shopId);

    /**
     * 获取用户权限
     * @param authUserId
     * @param shopId
     * @return
     */
    Map<String, String> getAuthPermissionMap(Long authUserId, Long shopId);

    /**
     * 创建门店用户
     * @param mAuthUserEntity
     * @return
     * @throws Exception
     */
    Boolean addAuthUser(AuthUserEntity mAuthUserEntity)throws Exception;

    /**
     * 获取门店员工业绩排行榜
     * @param mAuthUserModel
     * @return
     * @throws Exception
     */
    List<UserSalaryReport> querUserSaleryReport(AuthUserModel mAuthUserModel)throws Exception;

    /**
     * 员工业绩详情
     * @param mAuthUserModel
     * @return
     * @throws Exception
     */
    List<UserSalaryReport> querUserSaleryDetailReport(AuthUserModel mAuthUserModel)throws Exception;

}
