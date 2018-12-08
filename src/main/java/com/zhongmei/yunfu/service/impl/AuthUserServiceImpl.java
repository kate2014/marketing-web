package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zhongmei.yunfu.core.security.Password;
import com.zhongmei.yunfu.core.security.Token;
import com.zhongmei.yunfu.domain.entity.AuthUserEntity;
import com.zhongmei.yunfu.domain.mapper.AuthUserMapper;
import com.zhongmei.yunfu.service.AuthUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

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
}
