package com.zhongmei.yunfu.controller.api;


import com.zhongmei.yunfu.util.DateFormatUtil;
import com.zhongmei.yunfu.controller.model.BaseDataModel;
import com.zhongmei.yunfu.controller.model.BookingModel;
import com.zhongmei.yunfu.controller.model.BookingSearchModel;
import com.zhongmei.yunfu.domain.entity.BookingEntity;
import com.zhongmei.yunfu.domain.entity.BookingTradeItemEntity;
import com.zhongmei.yunfu.domain.entity.CustomerEntity;
import com.zhongmei.yunfu.service.BookingService;
import com.zhongmei.yunfu.service.BookingTradeItemService;
import com.zhongmei.yunfu.service.CustomerCouponService;
import com.zhongmei.yunfu.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 预订表 前端控制器
 * </p>
 *
 * @author yangyp
 * @since 2018-09-14
 */
@RestController
@RequestMapping("/wxapp/booking")
public class BookingApiController {

    @Autowired
    BookingService mBookingService;

    @Autowired
    BookingTradeItemService mBookingTradeItemService;

    @Autowired
    CustomerCouponService mCustomerCouponService;

    @Autowired
    CustomerService mCustomerService;

    @GetMapping("/createBooking")
    public BaseDataModel createBooking(ModelMap model, BookingModel mBookingModel) {
        BaseDataModel mBaseDataModel = new BaseDataModel();
        Boolean isSuccess = true;
        try {
            BookingEntity mBooking = new BookingEntity();
            String bookingUuid = String.valueOf(new Date().getTime()) + mBookingModel.getCommercialId();
            mBooking.setUuid(bookingUuid);
            mBooking.setCommercialId(mBookingModel.getCommercialId());
            mBooking.setCommercialName(mBookingModel.getCommercialName());
            mBooking.setCommercialGender(mBookingModel.getCommercialGender());
            mBooking.setCommercialPhone(mBookingModel.getCommercialPhone());
            Date startTime = DateFormatUtil.parseDate(mBookingModel.getStartTime(), "yyyy-MM-dd HH:mm");
            mBooking.setStartTime(startTime);

            long curren = startTime.getTime() + 30 * 60 * 1000;
            Date endTime = new Date(curren);
            mBooking.setOrderStatus(-2);
            mBooking.setEndTime(endTime);
            mBooking.setCousterNum(1);
            mBooking.setRemark(mBookingModel.getRemark());
            mBooking.setStatusFlag(1);
            mBooking.setBookingSource(2);
            mBooking.setConfirmed(1);
            mBooking.setBrandIdenty(mBookingModel.getBrandIdenty());
            mBooking.setShopIdenty(mBookingModel.getShopIdenty());
            mBooking.setClientCreateTime(new Date());
            mBooking.setClientUpdateTime(new Date());
            mBooking.setServerCreateTime(new Date());
            mBooking.setServerUpdateTime(new Date());
            mBooking.setDeviceIdenty("微信小程序");

            isSuccess = mBookingService.createBooking(mBooking);

            BookingTradeItemEntity mBookingTradeItem = new BookingTradeItemEntity();
            String bookingItemUuid = String.valueOf(new Date().getTime());
            mBookingTradeItem.setUuid(bookingItemUuid);
            mBookingTradeItem.setBookingId(mBooking.getId());
            mBookingTradeItem.setBookingUuid(bookingUuid);
            mBookingTradeItem.setDishId(mBookingModel.getDishId());
            mBookingTradeItem.setDishName(mBookingModel.getDishName());
            mBookingTradeItem.setType(0);
            mBookingTradeItem.setBrandIdenty(mBookingModel.getBrandIdenty());
            mBookingTradeItem.setShopIdenty(mBookingModel.getShopIdenty());
            mBookingTradeItem.setStatusFlag(1);
            mBookingTradeItem.setServerCreateTime(new Date());
            mBookingTradeItem.setServerUpdateTime(new Date());

            isSuccess = mBookingTradeItemService.createBoolingItem(mBookingTradeItem);

            if (isSuccess) {

                //绑定成功，推送优惠券
                mCustomerCouponService.putOnCoupon(mBookingModel.getBrandIdenty(), mBookingModel.getShopIdenty(),mBooking.getCommercialId(),"", 6,5);

                mBaseDataModel.setState("1000");
                mBaseDataModel.setMsg("创建预订成功");
                mBaseDataModel.setData(true);
            } else {
                mBaseDataModel.setState("1001");
                mBaseDataModel.setMsg("创建预订失败");
                mBaseDataModel.setData(false);
            }


        } catch (Exception e) {
            e.printStackTrace();
            mBaseDataModel.setState("1001");
            mBaseDataModel.setMsg("创建预订失败");
            mBaseDataModel.setData(false);
        }


        return mBaseDataModel;
    }

    /**
     * 获取会员预约数据
     *
     * @param model
     * @param mBookingModel
     * @return
     */
    @GetMapping("/queryBooking")
    public BaseDataModel queryBooking(ModelMap model, BookingModel mBookingModel) {
        BaseDataModel mBaseDataModel = new BaseDataModel();
        try {
            List<BookingModel> listBookingData = new ArrayList<>();
            BookingSearchModel mBookingSearchModel = new BookingSearchModel();

            mBookingSearchModel.setBrandIdenty(mBookingModel.getBrandIdenty());
            mBookingSearchModel.setShopIdenty(mBookingModel.getShopIdenty());

            String listCustomer = mBookingModel.getCommercialId().toString();
            //查询绑定的会员id
            CustomerEntity mobileCustomer =  mCustomerService.queryCustomerById(mBookingModel.getBrandIdenty(), mBookingModel.getShopIdenty(),mBookingModel.getCommercialId());
            if(mobileCustomer != null && mobileCustomer.getRelateId() != null && mobileCustomer.getRelateId() != 0){
                listCustomer += "," + mobileCustomer.getRelateId();
            }
            mBookingSearchModel.setCustomerList(listCustomer);

            List<BookingEntity> listData = mBookingService.queryBooking(mBookingSearchModel);
            for (BookingEntity booking : listData) {
                BookingModel bm = new BookingModel();
                bm.setCommercialGender(booking.getCommercialGender());
                bm.setOrderStatus(booking.getOrderStatus());
                bm.setConfirmed(booking.getConfirmed());
                bm.setCommercialId(booking.getCommercialId());
                bm.setCommercialName(booking.getCommercialName());
                bm.setCommercialPhone(booking.getCommercialPhone());
                bm.setStartTime(DateFormatUtil.format(booking.getStartTime(), "yyyy-MM-dd HH:mm"));
                List<BookingTradeItemEntity> listItems = mBookingTradeItemService.queryBookingItemById(booking.getId());
                if (listItems != null && listItems.size() > 0) {
                    BookingTradeItemEntity mBookingTradeItem = listItems.get(0);
                    bm.setDishId(mBookingTradeItem.getDishId());
                    bm.setDishName(mBookingTradeItem.getDishName());
                }
                listBookingData.add(bm);
            }

            mBaseDataModel.setState("1000");
            mBaseDataModel.setMsg("获取数据成功");
            mBaseDataModel.setData(listBookingData);
        } catch (Exception e) {
            mBaseDataModel.setState("1001");
            mBaseDataModel.setMsg("获取数据失败");
            mBaseDataModel.setData(false);
        }

        return mBaseDataModel;
    }
}

