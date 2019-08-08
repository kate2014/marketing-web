package com.zhongmei.yunfu.domain.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Condition;
import com.zhongmei.yunfu.domain.entity.OperationalRecordsEntity;
import com.zhongmei.yunfu.domain.entity.RedPacketsRecordEntity;
import com.zhongmei.yunfu.domain.entity.SalesReport;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 会员操作记录 Mapper 接口
 * </p>
 *
 */
public interface RedPacketsRecordMapper extends BaseMapper<RedPacketsRecordEntity> {



    @Select("SELECT sum(amount) as amount,r.customer_name, r.wx_photo\n" +
            "    FROM red_packets_record r\n" +
            "    ${ew.sqlSegment}\n" +
            "    GROUP BY r.customer_id\n" +
            "    ORDER BY  sum(amount) desc\n" +
            "    LIMIT 10;")
    List<RedPacketsRecordEntity> queryRankingList(@Param("ew") Condition wrapper);
}
