package com.zhongmei.yunfu.domain.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zhongmei.yunfu.domain.entity.CustomerSaveRuleEntity;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * <p>
 * 会员储值规则表 Mapper 接口
 * </p>
 *
 * @author pigeon88
 * @since 2019-06-04
 */
public interface CustomerSaveRuleMapper extends BaseMapper<CustomerSaveRuleEntity> {

    BigDecimal getShopGiveValue(@Param("shopId") Long shopId, @Param("tradeAmount") BigDecimal tradeAmount);
}
