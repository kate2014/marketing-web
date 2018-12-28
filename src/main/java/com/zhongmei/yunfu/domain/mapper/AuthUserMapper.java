package com.zhongmei.yunfu.domain.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zhongmei.yunfu.domain.entity.AuthPermissionEntity;
import com.zhongmei.yunfu.domain.entity.AuthUserEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author pigeon88
 * @since 2018-08-26
 */
public interface AuthUserMapper extends BaseMapper<AuthUserEntity> {

    @Select("SELECT * FROM auth_user WHERE status_flag = 1 AND account = #{account} AND shop_identy = #{shopId}")
    AuthUserEntity findByAccount(@Param("account") String account, @Param("shopId") Long shopId);


    @Select("SELECT\n" +
            "  ap.code,\n" +
            "  ap.name\n" +
            "FROM\n" +
            "  auth_permission ap\n" +
            "  LEFT JOIN auth_role_permission arp\n" +
            "    ON ap.id = arp.permission_id\n" +
            "  LEFT JOIN auth_user au\n" +
            "    ON arp.role_id = au.role_id\n" +
            "WHERE ap.platform = 1\n" +
            "  AND au.account = #{account}" +
            "  AND au.shop_identy = #{shopId}")
    List<AuthPermissionEntity> getAuthPermissionEntityBy(@Param("account") String account, @Param("shopId") Long shopId);
}
