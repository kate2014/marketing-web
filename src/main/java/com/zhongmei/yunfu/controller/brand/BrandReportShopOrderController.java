package com.zhongmei.yunfu.controller.brand;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.BaseController;
import com.zhongmei.yunfu.controller.model.ShopSearchModel;
import com.zhongmei.yunfu.controller.model.TradeModel;
import com.zhongmei.yunfu.domain.entity.CommercialEntity;
import com.zhongmei.yunfu.domain.entity.ShopSalesReport;
import com.zhongmei.yunfu.service.CommercialService;
import com.zhongmei.yunfu.service.TradeService;
import com.zhongmei.yunfu.util.DateFormatUtil;
import com.zhongmei.yunfu.util.ToolsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


/**
 * 门店销售业绩排行
 */
@Controller
@RequestMapping("/internal/brand/report")
public class BrandReportShopOrderController extends BaseController {

    @Autowired
    TradeService mTradeService;

    @RequestMapping({"/shopOrder"})
    public String shopList(Model model, TradeModel mTradeModel) {

        try {

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

                mTradeModel.setStartDate(temp);
            }
            if (mTradeModel.getEndDate() == null) {
                end = new Date();
                mTradeModel.setEndDate(DateFormatUtil.format(end, DateFormatUtil.FORMAT_FULL_DATE));
            }

            List<ShopSalesReport> listData = mTradeService.queryShopOrderSales(mTradeModel);

            BigDecimal totalCount = BigDecimal.ZERO;
            BigDecimal totalAmount = BigDecimal.ZERO;

            List<String> shopName = new LinkedList<>();
            List<BigDecimal> saleCount = new LinkedList<>();
            List<BigDecimal> saleAmount = new LinkedList<>();

            Long maxCount = 0l;
            Long maxAmount = 0l;

            int i = 0;
            for(ShopSalesReport entity : listData){

                if(i==20){
                    break;
                }
                i++;

                totalCount = totalCount.add(entity.getSalesCount());
                totalAmount = totalAmount.add(entity.getSalesAmount());

                if (maxCount < entity.getSalesCount().longValue()) {
                    maxCount = entity.getSalesCount().longValue();
                }
                if (maxAmount < entity.getSalesAmount().longValue()) {
                    maxAmount = entity.getSalesAmount().longValue();
                }

                shopName.add(entity.getShopName());
                saleCount.add(entity.getSalesCount());
                saleAmount.add(entity.getSalesAmount());

            }

            maxCount = ToolsUtil.getMaxData(maxCount);
            maxAmount = ToolsUtil.getMaxData(maxAmount);

            model.addAttribute("maxCount", maxCount);
            model.addAttribute("intervalCount", maxCount / 10);
            model.addAttribute("maxAmount", maxAmount);
            model.addAttribute("intervalAmount", maxAmount / 10);


            model.addAttribute("shopName", shopName);
            model.addAttribute("saleCount", saleCount);
            model.addAttribute("saleAmount", saleAmount);
            model.addAttribute("totalCount", totalCount);
            model.addAttribute("totalAmount", totalAmount);

            model.addAttribute("listData", listData);
        }catch (Exception e){
            e.printStackTrace();
        }

        model.addAttribute("mTradeModel", mTradeModel);
        return "brand_report_shop_order";
    }

}
