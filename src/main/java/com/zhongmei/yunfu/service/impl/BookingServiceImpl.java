package com.zhongmei.yunfu.service.impl;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhongmei.yunfu.controller.model.BookingSearchModel;
import com.zhongmei.yunfu.core.mybatis.mapper.ConditionFilter;
import com.zhongmei.yunfu.domain.entity.BookingEntity;
import com.zhongmei.yunfu.domain.entity.bean.BookingReport;
import com.zhongmei.yunfu.domain.mapper.BookingMapper;
import com.zhongmei.yunfu.service.BookingService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 预订表 服务实现类
 * </p>
 *
 * @author pigeon88
 * @since 2018-09-14
 */
@Service
public class BookingServiceImpl extends ServiceImpl<BookingMapper, BookingEntity> implements BookingService {

    @Override
    public Boolean createBooking(BookingEntity mBooking) throws Exception {
        Boolean isSuccess = insert(mBooking);
        return isSuccess;
    }

    @Override
    public List<BookingEntity> queryBooking(BookingSearchModel mBookingSearchModel) throws Exception {
        Condition eWrapper = ConditionFilter.create();
        eWrapper.isWhere(true);
        eWrapper.eq("brand_identy", mBookingSearchModel.getBrandIdenty());
        eWrapper.eq("shop_identy", mBookingSearchModel.getShopIdenty());
        eWrapper.in("commercial_id", mBookingSearchModel.getCustomerList());
        eWrapper.eq("status_flag", 1);

        List<BookingEntity> listData = baseMapper.queryBookingByCustomer(eWrapper);
        return listData;
    }

    public BookingEntity queryNewBookingByCustomer(BookingSearchModel mBookingSearchModel)throws Exception{
        Condition eWrapper = ConditionFilter.create();
        eWrapper.isWhere(true);
        eWrapper.eq("brand_identy", mBookingSearchModel.getBrandIdenty());
        eWrapper.eq("shop_identy", mBookingSearchModel.getShopIdenty());
        eWrapper.in("commercial_id", mBookingSearchModel.getCustomerList());
        eWrapper.eq("status_flag", 1);
        BookingEntity mBookingEntity = baseMapper.queryNewBookingByCustomer(eWrapper);
        return mBookingEntity;
    }

    @Override
    public BookingEntity queryBookingByUUid(Long brandIdenty, Long shopIdenty, String uuid) throws Exception {
        EntityWrapper<BookingEntity> eWrapper = new EntityWrapper<>();
        eWrapper.eq("brand_identy", brandIdenty);
        eWrapper.eq("shop_identy", shopIdenty);
        eWrapper.eq("uuid", uuid);
        BookingEntity mBooking = selectOne(eWrapper);
        return mBooking;
    }

    @Override
    public List<BookingReport> queryAllBookingReport(BookingSearchModel mBookingSearchModel) throws Exception {
        Condition eWrapper = ConditionFilter.create();
        eWrapper.isWhere(true);
        eWrapper.eq("brand_identy", mBookingSearchModel.getBrandIdenty());
        eWrapper.eq("shop_identy", mBookingSearchModel.getShopIdenty());
        eWrapper.eq("order_status", mBookingSearchModel.getOrderStatus());
        eWrapper.between("server_create_time", mBookingSearchModel.getStartDate(), mBookingSearchModel.getEndDate());

        List<BookingReport> listBooking = baseMapper.queryAllBookingReport(eWrapper);

        return listBooking;
    }

    @Override
    public List<BookingReport> queryBookingGroupSourceReport(BookingSearchModel mBookingSearchModel) throws Exception {
        Condition eWrapper = ConditionFilter.create();
        eWrapper.isWhere(true);
        eWrapper.eq("brand_identy", mBookingSearchModel.getBrandIdenty());
        eWrapper.eq("shop_identy", mBookingSearchModel.getShopIdenty());
        eWrapper.between("server_create_time", mBookingSearchModel.getStartDate(), mBookingSearchModel.getEndDate());

        List<BookingReport> listBooking = baseMapper.queryBookingSourceReport(eWrapper);

        return listBooking;
    }
}
