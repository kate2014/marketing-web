package com.zhongmei.yunfu.domain.mapper;

import com.zhongmei.yunfu.domain.entity.CustomerExtraEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 会员扩展表 Mapper 接口
 * </p>
 *
 * @author pigeon88
 * @since 2019-06-05
 */
public interface CustomerExtraMapper extends BaseMapper<CustomerExtraEntity> {

    @Select("SELECT * FROM customer_extra WHERE customer_id = #{customerId}")
    CustomerExtraEntity getCustomerExtra(@Param("customerId") Long customerId);
}
