package com.zhongmei.yunfu.api.web;

import com.zhongmei.yunfu.controller.model.BookingSearchModel;
import com.zhongmei.yunfu.controller.model.ReportMarketingModel;
import com.zhongmei.yunfu.domain.entity.BookingEntity;
import com.zhongmei.yunfu.domain.entity.BrandEntity;
import com.zhongmei.yunfu.domain.entity.CommercialEntity;
import com.zhongmei.yunfu.domain.entity.bean.BookingReport;
import com.zhongmei.yunfu.service.BookingService;
import com.zhongmei.yunfu.service.BrandService;
import com.zhongmei.yunfu.service.CommercialService;
import com.zhongmei.yunfu.util.DateFormatUtil;
import com.zhongmei.yunfu.util.ToolsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
@RequestMapping("/internal/bookingReport")
public class ReportBookingController {

    @Autowired
    BrandService mBrandService;
    @Autowired
    CommercialService mCommercialService;
    @Autowired
    BookingService mBookingService;

    @RequestMapping("/booking")
    public String marketingCouponReport(Model model, BookingSearchModel mBookingSearchModel) {

        try{
            //获取门店品牌和门店编号
            queryShopMessage(model, mBookingSearchModel);

            //设置默认查询时间
            if (mBookingSearchModel.getStartDate() == null) {
                Calendar c = Calendar.getInstance();
                //过去15天
                c.setTime(new Date());
                c.add(Calendar.DATE, -15);
                Date start = c.getTime();
                String temp = DateFormatUtil.format(start, DateFormatUtil.FORMAT_FULL_DATE);

                mBookingSearchModel.setStartDate(temp);
            }

            if (mBookingSearchModel.getEndDate() == null) {
                Date end = new Date();
                mBookingSearchModel.setEndDate(DateFormatUtil.format(end, DateFormatUtil.FORMAT_FULL_DATE));
            }

            List<BookingReport> listBookingData = mBookingService.queryAllBookingReport(mBookingSearchModel);

            mBookingSearchModel.setOrderStatus(1);
            List<BookingReport> listSalesData = mBookingService.queryAllBookingReport(mBookingSearchModel);

            List<Integer> listBookingCount = new LinkedList<>();
            List<Integer> listUseCount = new LinkedList<>();
            List<String> listBookingDate = new LinkedList<>();

            int allCount = 0;
            int useCount = 0;

            long maxBookingCount = 0l;


            if(listBookingData != null && listBookingData.size() > 0){
                for(BookingReport br : listBookingData){
                    listBookingDate.add(DateFormatUtil.format(br.getCreateTime(),DateFormatUtil.FORMAT_DATE));
                    listBookingCount.add(br.getBookingCount());

                    if(maxBookingCount < br.getBookingCount()){
                        maxBookingCount = br.getBookingCount();
                    }
                    allCount += br.getBookingCount();
                }
            }

            for(BookingReport sd : listSalesData){
                useCount += sd.getBookingCount();
            }

            for(String bookingDate : listBookingDate){
                Boolean isHad = false;
                for(BookingReport sd : listSalesData){
                    String createDate = DateFormatUtil.format(sd.getCreateTime(), DateFormatUtil.FORMAT_DATE);
                    if(createDate.equals(bookingDate) ){
                        isHad = true;
                        listUseCount.add(sd.getBookingCount());
                        break;
                    }
                }
                if(!isHad){
                    listUseCount.add(0);
                }
            }


            int wxBookingCount = 0;
            int posBookingCount = 0;
            List<BookingReport> bookingSource =  mBookingService.queryBookingGroupSourceReport(mBookingSearchModel);
            for(BookingReport bs : bookingSource){
                if(bs.getBookingSource() == 1){
                    posBookingCount = bs.getBookingCount();
                }else if(bs.getBookingSource() == 2){
                    wxBookingCount = bs.getBookingCount();
                }
            }
            model.addAttribute("allCount", allCount);
            model.addAttribute("useCount", useCount);

            model.addAttribute("posBookingCount", posBookingCount);
            model.addAttribute("wxBookingCount", wxBookingCount);

            model.addAttribute("mBookingSearchModel", mBookingSearchModel);
            model.addAttribute("listBookingCount", listBookingCount);
            model.addAttribute("listUseCount", listUseCount);
            model.addAttribute("listBookingDate", listBookingDate);

            maxBookingCount = ToolsUtil.getMaxData(maxBookingCount);

            model.addAttribute("maxBookingCount", maxBookingCount);
            model.addAttribute("intervalBooking", maxBookingCount/10);


        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }


        return "report_booking";
    }

    public Model queryShopMessage(Model model, BookingSearchModel mBookingSearchModel) throws Exception {

        BrandEntity brand = mBrandService.queryBrandByAppId(mBookingSearchModel.getBrandIdenty());

        List<CommercialEntity> listCommercial = mCommercialService.queryCommercialByBrandId(brand.getId());

        model.addAttribute("brand", brand);
        model.addAttribute("listCommercial", listCommercial);
        return model;
    }
}
