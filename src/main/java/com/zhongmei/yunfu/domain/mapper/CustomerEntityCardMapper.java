package com.zhongmei.yunfu.domain.mapper;

import com.zhongmei.yunfu.domain.entity.CustomerEntityCardEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author pigeon88
 * @since 2019-04-29
 */
public interface CustomerEntityCardMapper extends BaseMapper<CustomerEntityCardEntity> {

    CustomerEntityCardEntity selectByCustomerId(Long customerId);

    CustomerEntityCardEntity getByCardNo(@Param("shopId") Long shopId, @Param("cardNo") String cardNo);
}
