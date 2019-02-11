package com.zhongmei.yunfu.domain.mapper;

import com.baomidou.mybatisplus.mapper.Condition;
import com.zhongmei.yunfu.controller.model.CutDownCustomerModel;
import com.zhongmei.yunfu.domain.entity.CutDownCustomerEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 会员发起砍价记录 Mapper 接口
 * </p>
 *
 * @author yangyp
 * @since 2018-09-13
 */
public interface CutDownCustomerMapper extends BaseMapper<CutDownCustomerEntity> {

    @Select("select m.`name` ,m.`begin_time` ,m.`end_time` ,m.start_price,m.`end_price` ,m.`validity_period`, m.`img_url` ,c.state, c.id,c.cut_down_id,c.`current_price` ,c.`join_count` \n" +
            "from cut_down_customer c LEFT JOIN cut_down_marketing  m on c.`cut_down_id` = m.`id` \n" +
            " ${ew.sqlSegment} and unix_timestamp(m.`validity_period`) >= unix_timestamp(NOW()) ORDER BY c.`server_create_time` desc;")
    List<CutDownCustomerModel> queryCutDowmByCustomer(@Param("ew") Condition wrapper);
}

