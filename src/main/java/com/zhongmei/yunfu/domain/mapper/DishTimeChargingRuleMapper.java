package com.zhongmei.yunfu.domain.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Condition;
import com.zhongmei.yunfu.domain.entity.BookingEntity;
import com.zhongmei.yunfu.domain.entity.DishTimeChargingRuleEntity;
import com.zhongmei.yunfu.domain.entity.bean.BookingReport;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 预订表 Mapper 接口
 * </p>
 *
 * @author yangyp
 * @since 2018-09-14
 */
public interface DishTimeChargingRuleMapper extends BaseMapper<DishTimeChargingRuleEntity> {


}
