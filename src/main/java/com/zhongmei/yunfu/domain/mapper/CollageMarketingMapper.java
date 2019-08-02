package com.zhongmei.yunfu.domain.mapper;

import com.baomidou.mybatisplus.mapper.Condition;
import com.zhongmei.yunfu.controller.model.CollageReportModel;
import com.zhongmei.yunfu.domain.entity.CollageMarketingEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * <p>
 * 拼团活动 Mapper 接口
 * </p>
 *
 * @author yangyp
 * @since 2018-09-10
 */
public interface CollageMarketingMapper extends BaseMapper<CollageMarketingEntity> {

    @Select("SELECT id,name,begin_time,end_time,collage_people_count,collage_price,original_price,profile,img_url FROM `collage_marketing`  ${ew.sqlSegment} ORDER BY  `server_create_time`  DESC LIMIT 1;")
    CollageMarketingEntity queryNewCollage(@Param("ew") Condition wrapper);

    @Select("SELECT m.id as marketingId, m.`join_count` as joinCount , m.`name` as name,m.`open_group_count` as openCount ,m.finish_group_count as finishCount \n" +
            "FROM collage_marketing m \n" +
            "${ew.sqlSegment} and unix_timestamp(`validity_period`) >= unix_timestamp(NOW()) \n" +
            "ORDER BY m.`server_create_time` DESC ;")
    List<CollageReportModel> queryCollageReport(@Param("ew") Condition wrapper);
}
