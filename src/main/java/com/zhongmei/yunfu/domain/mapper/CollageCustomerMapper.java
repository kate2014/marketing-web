package com.zhongmei.yunfu.domain.mapper;

import com.baomidou.mybatisplus.mapper.Condition;
import com.zhongmei.yunfu.controller.model.CollageCustomerModel;
import com.zhongmei.yunfu.domain.entity.CollageCustomerEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 会员发起拼团记录 Mapper 接口
 * </p>
 *
 * @author pigeon88
 * @since 2018-09-14
 */
public interface CollageCustomerMapper extends BaseMapper<CollageCustomerEntity> {

    @Select("select m.`name` ,m.collage_people_count,m.`collage_price` ,m.original_price,m.begin_time,m.end_time,m.`validity_period` ,m.`img_url` ,c.id,c.state, c.relation_id,c.`type`,c.`join_count` \n" +
            "from `collage_customer` c LEFT JOIN `collage_marketing` m on c.`collage_id` = m.`id`  ${ew.sqlSegment} ORDER BY c.`server_create_time` desc;")
    List<CollageCustomerModel> querCollageByCustomer(@Param("ew") Condition wrapper);

}
