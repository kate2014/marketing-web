package com.zhongmei.yunfu.domain.mapper;

import com.baomidou.mybatisplus.mapper.Condition;
import com.zhongmei.yunfu.controller.model.CutDownCustomerModel;
import com.zhongmei.yunfu.controller.model.CutDownReportModel;
import com.zhongmei.yunfu.domain.entity.CutDownMarketingEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 砍价活动 Mapper 接口
 * </p>
 *
 * @author pigeon88
 * @since 2018-09-10
 */
public interface CutDownMarketingMapper extends BaseMapper<CutDownMarketingEntity> {

    @Select("SELECT id,name,begin_time,end_time,start_price,end_price,img_url,profile FROM `cut_down_marketing` ${ew.sqlSegment} ORDER BY  `server_create_time`  DESC LIMIT 1")
    CutDownMarketingEntity queryNewCutDown(@Param("ew") Condition wrapper);

    @Select("SELECT m.`name` ,COUNT(h.`id`) joinCount ,m.`sold_count` as soldCount  FROM `cut_down_marketing` m LEFT JOIN `cut_down_history` h on m.`id` = h.`cut_down_id` \n" +
            "${ew.sqlSegment}\n" +
            "GROUP BY h.`cut_down_id` ORDER BY m.`server_create_time` DESC  ;")
    List<CutDownReportModel> queryCutDownReport(@Param("ew") Condition wrapper);

    @Select("update `cut_down_marketing` set `sold_count` = `sold_count` +1 ${ew.sqlSegment}")
    void modifyCutDownCount(@Param("ew") Condition wrapper);
}
