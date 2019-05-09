package com.zhongmei.yunfu.controller.brand;

import com.zhongmei.yunfu.controller.BaseController;
import com.zhongmei.yunfu.controller.model.TradeModel;
import com.zhongmei.yunfu.controller.model.excel.ExcelData;
import com.zhongmei.yunfu.controller.model.excel.ExcelUtils;
import com.zhongmei.yunfu.domain.entity.bean.ShopSalesReport;
import com.zhongmei.yunfu.service.TradeService;
import com.zhongmei.yunfu.util.DateFormatUtil;
import com.zhongmei.yunfu.util.ToolsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;


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

            for(ShopSalesReport entity : listData){

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

            //获取门店业绩详情
            Map<Long,ShopSalesReport> temp = shopSalesData(mTradeModel);

            List<ShopSalesReport> listDetail = new ArrayList<>();
            for (Long key : temp.keySet()) {
                listDetail.add(temp.get(key));
            }

            model.addAttribute("listData", listDetail);
        }catch (Exception e){
            e.printStackTrace();
        }

        model.addAttribute("mTradeModel", mTradeModel);
        return "brand_report_shop_order";
    }

    public Map<Long,ShopSalesReport> shopSalesData(TradeModel mTradeModel) throws Exception{
        //获取门店业绩详情
        List<ShopSalesReport> templist = mTradeService.queryShopSalesData(mTradeModel);

        Map<Long,ShopSalesReport> temp = new HashMap<>();
        for(ShopSalesReport entity : templist){
            ShopSalesReport mShopSalesReport = temp.get(entity.getShopIdenty());
            if(mShopSalesReport == null){
                mShopSalesReport = new ShopSalesReport();
                temp.put(entity.getShopIdenty(),mShopSalesReport);

                mShopSalesReport.setShopName(entity.getShopName());
            }
            if(entity.getBusinessType() == 1){
                mShopSalesReport.setSalesAmount(entity.getSalesAmount());
                mShopSalesReport.setSalesCount(entity.getSalesCount());
            }else if(entity.getBusinessType() == 2){
                mShopSalesReport.setTotalSave(entity.getSalesAmount());
                mShopSalesReport.setTotalSaveCount(entity.getSalesCount());
            }else if(entity.getBusinessType() == 3){
                mShopSalesReport.setTotalCard(entity.getSalesAmount());
                mShopSalesReport.setTotalCardCount(entity.getSalesCount());
            }else if(entity.getBusinessType() == 4){
                mShopSalesReport.setTotalWeiXin(entity.getSalesAmount());
                mShopSalesReport.setTotalWeiXinCount(entity.getSalesCount());
            }

            if(mShopSalesReport.getTotalAmount() == null){
                mShopSalesReport.setTotalAmount(BigDecimal.ZERO);
                mShopSalesReport.setTotalAmountCount(BigDecimal.ZERO);
            }

            mShopSalesReport.setTotalAmount(mShopSalesReport.getTotalAmount().add(entity.getSalesAmount()));
            mShopSalesReport.setTotalAmountCount(mShopSalesReport.getTotalAmountCount().add(entity.getSalesCount()));

        }

        return temp;
    }

    @RequestMapping("/shopOrder/export/excel")
    public void exportExcel(HttpServletResponse response, TradeModel mTradeModel) throws Exception{

        //获取门店业绩详情
        Map<Long,ShopSalesReport> temp = shopSalesData(mTradeModel);

        ExcelData data = new ExcelData();
        data.setSheetName("各门店业绩报表");
        List<String> titles = new ArrayList();
        titles.add("序");
        titles.add("门店名称");
        titles.add("储值笔数");
        titles.add("储值金额");
        titles.add("次卡销售笔数");
        titles.add("次卡销售金额");
        titles.add("小程序销售笔数");
        titles.add("小程序销售金额");
        titles.add("销货笔数");
        titles.add("销货金额");
        titles.add("营业总单数");
        titles.add("营业合计");
        data.setTitles(titles);

        List<List<Object>> rows = new ArrayList();
        data.setRows(rows);

        int i = 1;
        if(temp != null){
            for (Long key : temp.keySet()) {

                ShopSalesReport entity = temp.get(key);

                List<Object> row = new ArrayList();
                rows.add(row);
                row.add(i++);
                row.add(entity.getShopName());
                row.add(entity.getTotalSaveCount());
                row.add(entity.getTotalSave());
                row.add(entity.getTotalCardCount());
                row.add(entity.getTotalCard());
                row.add(entity.getTotalWeiXinCount());
                row.add(entity.getTotalWeiXin());
                row.add(entity.getSalesCount());
                row.add(entity.getSalesAmount());
                row.add(entity.getTotalAmountCount());
                row.add(entity.getTotalAmount());
            }
        }

        SimpleDateFormat fdate = new SimpleDateFormat("yyyyMMdd");
        String fileName = String.format("各门店业绩报表-%s.xls", fdate.format(new Date()));
        ExcelUtils.exportExcel(response, fileName, data);
    }

}
