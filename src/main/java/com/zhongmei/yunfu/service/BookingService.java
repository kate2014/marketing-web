package com.zhongmei.yunfu.service;

import com.zhongmei.yunfu.controller.model.BookingSearchModel;
import com.zhongmei.yunfu.domain.entity.BookingEntity;
import com.baomidou.mybatisplus.service.IService;
import com.zhongmei.yunfu.domain.entity.bean.BookingReport;

import java.util.List;

/**
 * <p>
 * 预订表 服务类
 * </p>
 *
 * @author pigeon88
 * @since 2018-09-14
 */
public interface BookingService extends IService<BookingEntity> {

    /**
     * 发起预约
     *
     * @param mBooking
     * @return
     */
    Boolean createBooking(BookingEntity mBooking) throws Exception;

    /**
     * 获取会员预约信息
     *
     * @param mBookingSearchModel
     * @return
     */
    List<BookingEntity> queryBooking(BookingSearchModel mBookingSearchModel) throws Exception;

    /**
     * 获取会员最新预约信息
     *
     * @param mBookingSearchModel
     * @return
     * @throws Exception
     */
    BookingEntity queryNewBookingByCustomer(BookingSearchModel mBookingSearchModel) throws Exception;

    /**
     * 根据uuid或预约信息
     *
     * @param uuid
     * @return
     * @throws Exception
     */
    BookingEntity queryBookingByUUid(Long brandIdenty, Long shopIdenty, String uuid) throws Exception;

    /**
     * 查询所有预订数据报表
     * @param mBookingSearchModel
     * @return
     * @throws Exception
     */
    List<BookingReport> queryAllBookingReport(BookingSearchModel mBookingSearchModel)throws Exception;

    /**
     * 查询不同来源的订单预订情况
     * @param mBookingSearchModel
     * @return
     * @throws Exception
     */
    List<BookingReport> queryBookingGroupSourceReport(BookingSearchModel mBookingSearchModel)throws Exception;
}
