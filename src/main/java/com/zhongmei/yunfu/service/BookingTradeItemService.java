package com.zhongmei.yunfu.service;

import com.zhongmei.yunfu.domain.entity.BookingTradeItemEntity;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 交易明细 服务类
 * </p>
 *
 * @author pigeon88
 * @since 2018-09-14
 */
public interface BookingTradeItemService extends IService<BookingTradeItemEntity> {

    /**
     * 创建预订服务项目
     *
     * @return
     */
    Boolean createBoolingItem(BookingTradeItemEntity mBookingTradeItem) throws Exception;

    /**
     * 根据预订id获取预订服务项目
     *
     * @param bookingId
     * @return
     */
    List<BookingTradeItemEntity> queryBookingItemById(Long bookingId) throws Exception;
}
