package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhongmei.yunfu.core.security.Password;
import com.zhongmei.yunfu.core.security.Token;
import com.zhongmei.yunfu.domain.entity.AuthPermissionEntity;
import com.zhongmei.yunfu.domain.entity.AuthUserEntity;
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
}
