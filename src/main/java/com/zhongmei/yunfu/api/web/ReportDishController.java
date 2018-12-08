package com.zhongmei.yunfu.api.web;

import com.zhongmei.yunfu.util.DateFormatUtil;
import com.zhongmei.yunfu.util.ToolsUtil;
import com.zhongmei.yunfu.controller.model.TradeModel;
import com.zhongmei.yunfu.domain.entity.BrandEntity;
import com.zhongmei.yunfu.domain.entity.CommercialEntity;
import com.zhongmei.yunfu.domain.entity.DishReport;
import com.zhongmei.yunfu.service.BrandService;
import com.zhongmei.yunfu.service.CommercialService;
import com.zhongmei.yunfu.service.TradeItemService;
import com.zhongmei.yunfu.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/internal/dish")
public class ReportDishController {

    @Autowired
    TradeService mTradeService;
    @Autowired
    TradeItemService mTradeItemService;
    @Autowired
    BrandService mBrandService;
    @Autowired
    CommercialService mCommercialService;

    @RequestMapping("/dishReport")
    public String reportAddCustomer(Model model, TradeModel mTradeModel) {

        try {
            //获取门店品牌和门店编号
            queryShopMessage(model, mTradeModel);

            Date start = null;
            Date end = null;
            //设置默认查询时间
            if (mTradeModel.getStartDate() == null) {
                Calendar c = Calendar.getInstance();
                //过去15天
                c.setTime(new Date());
                c.add(Calendar.DATE, -15);
                start = c.getTime();
                String temp = DateFormatUtil.format(start, DateFormatUtil.FORMAT_FULL_DATE);
                start = DateFormatUtil.parseDate(temp, DateFormatUtil.FORMAT_FULL_DATE);

                mTradeModel.setStartDate(temp);
            } else {
                start = DateFormatUtil.parseDate(mTradeModel.getStartDate(), DateFormatUtil.FORMAT_FULL_DATE);
            }
            if (mTradeModel.getEndDate() == null) {
                end = new Date();
                mTradeModel.setEndDate(DateFormatUtil.format(end, DateFormatUtil.FORMAT_FULL_DATE));
            } else {
                end = DateFormatUtil.parseDate(mTradeModel.getEndDate(), DateFormatUtil.FORMAT_FULL_DATE);
            }

            List<DishReport> listData = mTradeItemService.selectDishSalesReport(mTradeModel.getBrandIdenty(), mTradeModel.getShopIdenty(), start, end);

            List<String> listDishName = new ArrayList<>();
            List<BigDecimal> listSalesAmount = new ArrayList<>();
            List<Long> listSalesCount = new ArrayList<>();

            Long maxCount = 0l;
            Long maxAmount = 0l;
            for (DishReport dp : listData) {

                listDishName.add(dp.getDishName());
                listSalesAmount.add(dp.getSalesAmount());
                listSalesCount.add(dp.getSalseCount());

                if (maxCount < dp.getSalseCount()) {
                    maxCount = dp.getSalseCount();
                }
                if (maxAmount < dp.getSalesAmount().longValue()) {
                    maxAmount = dp.getSalesAmount().longValue();
                }

            }

            maxCount = ToolsUtil.getMaxData(maxCount);
            maxAmount = ToolsUtil.getMaxData(maxAmount);

            model.addAttribute("maxCount", maxCount);
            model.addAttribute("intervalCount", maxCount / 10);
            model.addAttribute("maxAmount", maxAmount);
            model.addAttribute("intervalAmount", maxAmount / 10);

            model.addAttribute("mTradeModel", mTradeModel);
            model.addAttribute("listDishName", listDishName);
            model.addAttribute("listSalesAmount", listSalesAmount);
            model.addAttribute("listSalesCount", listSalesCount);

            model.addAttribute("listData", listData);
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }

        return "report_dish_sales";
    }

    public Model queryShopMessage(Model model, TradeModel mTradeModel) throws Exception {

        BrandEntity brand = mBrandService.queryBrandByAppId(mTradeModel.getBrandIdenty());

        List<CommercialEntity> listCommercial = mCommercialService.queryCommercialByBrandId(brand.getId());

        model.addAttribute("brand", brand);
        model.addAttribute("listCommercial", listCommercial);
        return model;
    }
}
