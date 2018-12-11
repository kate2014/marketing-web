package com.zhongmei.yunfu.domain.mapper;

import com.zhongmei.yunfu.domain.entity.CommercialEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 商户信息表 Mapper 接口
 * </p>
 *
 * @author yangyp
 * @since 2018-08-26
 */
public interface CommercialMapper extends BaseMapper<CommercialEntity> {

    @Select("SELECT s.* FROM commercial s INNER JOIN shop_device d ON s.commercial_id = d.shop_identy WHERE d.device_mac = #{device} AND d.status_flag = 1")
    CommercialEntity selectByDevice(String device);
}
