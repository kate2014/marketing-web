package com.zhongmei.yunfu.domain.mapper;

import com.baomidou.mybatisplus.mapper.Condition;
import com.zhongmei.yunfu.domain.entity.BookingEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zhongmei.yunfu.domain.entity.bean.BookingReport;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 预订表 Mapper 接口
 * </p>
 *
 * @author pigeon88
 * @since 2018-09-14
 */
public interface BookingMapper extends BaseMapper<BookingEntity> {

    @Select("SELECT id,commercial_name,commercial_phone,start_time,remark,order_status,confirmed FROM `booking` ${ew.sqlSegment} ORDER BY `server_create_time` desc LIMIT 1")
    BookingEntity queryNewBookingByCustomer(@Param("ew") Condition wrapper);

    @Select("SELECT id,commercial_name,commercial_phone,start_time,remark,order_status,confirmed FROM `booking` ${ew.sqlSegment} ORDER BY `server_create_time` desc LIMIT 50")
    List<BookingEntity> queryBookingByCustomer(@Param("ew") Condition wrapper);

    @Select("SELECT count(*) as booking_count, date_format(client_create_time,'%Y-%m-%d') as create_time FROM `booking` ${ew.sqlSegment} GROUP BY  date_format(client_create_time,'%Y-%m-%d') ORDER BY  date_format(client_create_time,'%Y-%m-%d')")
    List<BookingReport> queryAllBookingReport(@Param("ew") Condition wrapper);

    @Select("SELECT count(*) as bookingCount ,booking_source FROM `booking` ${ew.sqlSegment} GROUP BY  booking_source;")
    List<BookingReport> queryBookingSourceReport(@Param("ew") Condition wrapper);
}
