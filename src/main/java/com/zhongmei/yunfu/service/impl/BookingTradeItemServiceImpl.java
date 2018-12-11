package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhongmei.yunfu.domain.entity.BookingTradeItemEntity;
import com.zhongmei.yunfu.domain.mapper.BookingTradeItemMapper;
import com.zhongmei.yunfu.service.BookingTradeItemService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 交易明细 服务实现类
 * </p>
 *
 * @author yangyp
 * @since 2018-09-14
 */
@Service
public class BookingTradeItemServiceImpl extends ServiceImpl<BookingTradeItemMapper, BookingTradeItemEntity> implements BookingTradeItemService {

    @Override
    public Boolean createBoolingItem(BookingTradeItemEntity mBookingTradeItem) throws Exception {

        Boolean isSuccess = insert(mBookingTradeItem);
        return isSuccess;
    }

    @Override
    public List<BookingTradeItemEntity> queryBookingItemById(Long bookingId) throws Exception {
        EntityWrapper<BookingTradeItemEntity> eWrapper = new EntityWrapper<>();
        eWrapper.eq("booking_id", bookingId);
        List<BookingTradeItemEntity> listData = selectList(eWrapper);
        return listData;
    }
}
