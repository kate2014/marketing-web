package com.zhongmei.yunfu.domain.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zhongmei.yunfu.domain.entity.AuthUserEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author yangyp
 * @since 2018-08-26
 */
public interface AuthUserMapper extends BaseMapper<AuthUserEntity> {

    @Select("SELECT * FROM auth_user WHERE status_flag = 1 AND account = #{account} AND shop_identy = #{shopId}")
    AuthUserEntity findByAccount(@Param("account") String account, @Param("shopId") Long shopId);
}
