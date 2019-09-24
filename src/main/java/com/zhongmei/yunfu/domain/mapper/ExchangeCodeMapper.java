package com.zhongmei.yunfu.domain.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Condition;
import com.zhongmei.yunfu.domain.entity.AuthPermissionEntity;
import com.zhongmei.yunfu.domain.entity.AuthUserEntity;
import com.zhongmei.yunfu.domain.entity.ExchangeCodeEntity;
import com.zhongmei.yunfu.domain.entity.UserSalaryReport;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 推广提成兑换码 Mapper 接口
 * </p>
 *
 */
public interface ExchangeCodeMapper extends BaseMapper<ExchangeCodeEntity> {


}
