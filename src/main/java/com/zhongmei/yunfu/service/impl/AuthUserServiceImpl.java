package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhongmei.yunfu.controller.model.AuthUserModel;
import com.zhongmei.yunfu.core.mybatis.mapper.ConditionFilter;
import com.zhongmei.yunfu.core.security.Password;
import com.zhongmei.yunfu.core.security.Token;
import com.zhongmei.yunfu.domain.entity.*;
import com.zhongmei.yunfu.domain.enums.StatusFlag;
import com.zhongmei.yunfu.domain.mapper.AuthUserMapper;
import com.zhongmei.yunfu.erp.model.ERPCommercialModel;
import com.zhongmei.yunfu.service.AuthUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author pigeon88
 * @since 2018-08-26
 */
@Service
public class AuthUserServiceImpl extends ServiceImpl<AuthUserMapper, AuthUserEntity> implements AuthUserService {

    @Override
    public AuthUserEntity login(String account, String password, Long shopId) {
        return login(account, password, shopId, true);
    }

    public AuthUserEntity login(String account, String password, Long shopId, boolean isGeneratePwd) {
        if (StringUtils.isNotBlank(account) && StringUtils.isNotBlank(password)) {
            if (isGeneratePwd) {
                password = Password.create().generate(account, password);
            }
            AuthUserEntity authUser = baseMapper.findByAccount(account, shopId);
            if (authUser != null) {
                if (password.equals(authUser.getPassword())) {
                    return authUser;
                }
            }
        }
        return null;
    }

    @Override
    public AuthUserEntity tokenLogin(String token) {
        if (StringUtils.isNotBlank(token)) {
            Token.UserVo decode = Token.decode(token);
            if (decode != null) {
                AuthUserEntity userEntity = new AuthUserEntity();
                userEntity.setAccount(decode.getAccount());
                userEntity.setPassword(decode.getPassword());
                userEntity.setId(decode.getUserId());
                userEntity.setName(decode.getUserName());
                userEntity.setShopIdenty(decode.getShopId());
                userEntity.setBrandIdenty(decode.getBrandId());
                return userEntity;
            }
        }
        return null;
    }


    @Override
    public List<AuthPermissionEntity> getAuthPermissionEntityBy(Long userId) {
        return baseMapper.getAuthPermissionEntityBy(userId);
    }

    /*@Override
    public Map<String, String> getAuthPermissionMapBy(String account, Long shopId) {
        Map<String, String> result = new HashMap<>();
        List<AuthPermissionEntity> authPermissionEntityBy = getAuthPermissionEntityBy(userId);
        authPermissionEntityBy.forEach(it -> result.put(it.getCode(), it.getName()));
        return result;
    }*/

    @Override
    public Map<String, String> getAuthPermissionMap(Long authUserId, Long shopId) {

        Map<String, String> result = new HashMap<>();
        List<AuthPermissionEntity> authPermissionEntityBy = baseMapper.getAuthPermissionEntity(authUserId, shopId);
        authPermissionEntityBy.forEach(it -> result.put(it.getCode(), it.getName()));
        return result;
    }

    @Override
    public Boolean addAuthUser(AuthUserEntity mAuthUserEntity) throws Exception {
        Boolean isSuccess = insert(mAuthUserEntity);
        return isSuccess;
    }

    @Override
    public List<UserSalaryReport> querUserSaleryReport(AuthUserModel mAuthUserModel) throws Exception {
        Condition eWrapper = ConditionFilter.create();
        eWrapper.eq("t.brand_identy", mAuthUserModel.getBrandIdenty());
        eWrapper.eq("t.shop_identy", mAuthUserModel.getShopIdenty());
        eWrapper.eq("t.trade_status", 4);
        eWrapper.eq("t.status_flag", 1);
        eWrapper.between("t.server_create_time", mAuthUserModel.getStartDate(), mAuthUserModel.getEndDate());

        List<UserSalaryReport> listData = baseMapper.querUserSaleryReport(eWrapper);
        return listData;
    }

    @Override
    public List<UserSalaryReport> querUserSaleryDetailReport(AuthUserModel mAuthUserModel) throws Exception {
        Condition eWrapper = ConditionFilter.create();
        eWrapper.eq("t.brand_identy", mAuthUserModel.getBrandIdenty());
        eWrapper.eq("t.shop_identy", mAuthUserModel.getShopIdenty());
        eWrapper.eq("t.trade_status", 4);
        eWrapper.eq("t.status_flag", 1);
        eWrapper.between("t.server_create_time", mAuthUserModel.getStartDate(), mAuthUserModel.getEndDate());
        List<UserSalaryReport> listData = baseMapper.querUserSaleryDetailReport(eWrapper);
        return listData;
    }

    @Override
    public Page<AuthUserEntity> queryAuthUserByBrand(AuthUserModel mAuthUserModel, int curPage, int pageSize) throws Exception {

        EntityWrapper<AuthUserEntity> eWrapper = new EntityWrapper<>(new AuthUserEntity());
        eWrapper.setSqlSelect("id", "role_id", "name", "gender", "mobile", "job_number", "job_employee_type", "job_position", "source_flag","assigned_group","shop_identy","brand_identy");
        eWrapper.and().eq("brand_identy", mAuthUserModel.getBrandIdenty());
        eWrapper.and().eq("status_flag", StatusFlag.VALiD.value());
        eWrapper.and().eq("assigned_group", mAuthUserModel.getAssignedGroup());
        if(mAuthUserModel.getShopIdenty() != null){
            eWrapper.and().eq("shop_identy", mAuthUserModel.getShopIdenty());
        }
        if(mAuthUserModel.getJobEmployeeType() != null){
            eWrapper.and().eq("job_employee_type", mAuthUserModel.getJobEmployeeType());
        }
        if(mAuthUserModel.getJobNumber() != null && !mAuthUserModel.getJobNumber().equals("")){
            eWrapper.and().eq("job_number", mAuthUserModel.getJobNumber());
        }
        if(mAuthUserModel.getName() != null && !mAuthUserModel.getName().equals("")){
            eWrapper.and().like("name", mAuthUserModel.getName());
        }

        eWrapper.orderBy("shop_identy",false);
        Page<AuthUserEntity> page = new Page<>(curPage, pageSize);
        Page<AuthUserEntity> listData = selectPage(page, eWrapper);

        return listData;
    }
}
