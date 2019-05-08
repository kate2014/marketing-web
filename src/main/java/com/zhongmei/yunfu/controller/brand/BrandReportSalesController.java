package com.zhongmei.yunfu.controller.brand;

import com.zhongmei.yunfu.controller.BaseController;
import com.zhongmei.yunfu.controller.model.AuthUserModel;
import com.zhongmei.yunfu.controller.model.PaymentItemModel;
import com.zhongmei.yunfu.controller.model.ReportSalesExportModel;
import com.zhongmei.yunfu.controller.model.TradeModel;
import com.zhongmei.yunfu.controller.model.excel.ExcelData;
import com.zhongmei.yunfu.controller.model.excel.ExcelUtils;
import com.zhongmei.yunfu.domain.entity.BrandEntity;
import com.zhongmei.yunfu.domain.entity.CommercialEntity;
import com.zhongmei.yunfu.domain.entity.SalesReport;
import com.zhongmei.yunfu.domain.entity.TradeEntity;
import com.zhongmei.yunfu.service.BrandService;
import com.zhongmei.yunfu.service.CommercialService;
import com.zhongmei.yunfu.service.PaymentItemService;
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
 * 营业报表
 */
@Controller
@RequestMapping("/internal/brand/report")
public class BrandReportSalesController extends BaseController {

    @Autowired
    TradeService mTradeService;
    @Autowired
    BrandService mBrandService;
    @Autowired
    CommercialService mCommercialService;
    @Autowired
    PaymentItemService mPaymentItemService;

    @RequestMapping("/salesReport")
    public String reportAddCustomer(Model model, TradeModel mTradeModel) {
        try {

            //获取门店品牌和门店编号
            queryShopMessage(model, mTradeModel);

            //设置默认查询时间
            if (mTradeModel.getStartDate() == null) {
                Calendar c = Calendar.getInstance();
                //过去15天
                c.setTime(new Date());
                c.add(Calendar.DATE, -15);
                Date start = c.getTime();
                String temp = DateFormatUtil.format(start, DateFormatUtil.FORMAT_FULL_DATE);
                mTradeModel.setStartDate(temp);

            }
            if (mTradeModel.getEndDate() == null) {
                mTradeModel.setEndDate(DateFormatUtil.format(new Date(), DateFormatUtil.FORMAT_FULL_DATE));
            }

            //销售总额统计
            querySalesData(model, mTradeModel);
            //获取各支付方式支付总额
            queryPaymentReport(model, mTradeModel);

            //销货单记录
            mTradeModel.setTradeType(1);
            mTradeModel.setTradeStatus(4);
            List<SalesReport> listSalesReport = mTradeService.querySalesReport(mTradeModel);
            //退货单记录
            mTradeModel.setTradeType(2);
            mTradeModel.setTradeStatus(5);
            List<SalesReport> listReturnSalesReport = mTradeService.querySalesReport(mTradeModel);

            List<BigDecimal> listSalesCount = new LinkedList<>();
            List<BigDecimal> listReturnSalesCount = new LinkedList<>();
            List<String> listCreateDate = new LinkedList<>();

            Map<String, Map<String,BigDecimal>> saldMap = new LinkedHashMap<>();


            for (SalesReport sr : listSalesReport) {
                String dateValue = DateFormatUtil.format(sr.getCreateDate(), "MM月dd日");
                Map<String,BigDecimal> tempMap = new HashMap<>();
                tempMap.put("salse",sr.getSalesAmount());
                saldMap.put(dateValue,tempMap);
            }

            for(SalesReport sr : listReturnSalesReport){
                String dateValue = DateFormatUtil.format(sr.getCreateDate(), "MM月dd日");
                Map<String,BigDecimal> tempMap = saldMap.get(dateValue);
                if(tempMap == null){
                    tempMap = new HashMap<>();
                }
                tempMap.put("return",sr.getSalesAmount());
                saldMap.put(dateValue,tempMap);
            }

            for(String key : saldMap.keySet()){
                listCreateDate.add(key);
                Map<String,BigDecimal> tempMap = saldMap.get(key);
                if(tempMap.get("salse") == null){
                    listSalesCount.add(BigDecimal.ZERO);
                }else{
                    listSalesCount.add(tempMap.get("salse"));
                }

                if(tempMap.get("return") == null){
                    listReturnSalesCount.add(BigDecimal.ZERO);
                }else{
                    listReturnSalesCount.add(tempMap.get("return"));
                }

            }


            //pos端销售额
            mTradeModel.setBusinessType(null);
            mTradeModel.setTradeType(1);
            mTradeModel.setTradeStatus(4);
            mTradeModel.setSource(1);
            BigDecimal posSalesAmount = mTradeService.querySalesAmount(mTradeModel);
            if(posSalesAmount == null){
                posSalesAmount = BigDecimal.ZERO;
            }


            //小程序
            mTradeModel.setBusinessType(null);
            mTradeModel.setTradeType(1);
            mTradeModel.setTradeStatus(4);
            mTradeModel.setSource(2);
            BigDecimal wxSalesAmount = mTradeService.querySalesAmount(mTradeModel);
            if(wxSalesAmount == null){
                wxSalesAmount = BigDecimal.ZERO;
            }


            model.addAttribute("listSalesCount", listSalesCount);
            model.addAttribute("listReturnSalesCount", listReturnSalesCount);
            model.addAttribute("listCreateDate", listCreateDate);

            model.addAttribute("posSalesAmount", posSalesAmount);
            model.addAttribute("wxSalesAmount", wxSalesAmount);
            model.addAttribute("totalSales", posSalesAmount.add(wxSalesAmount));

            model.addAttribute("mTradeModel", mTradeModel);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "brand_report_sales_situation";
    }

    /**
     * 查询门店时间段内营业情况
     * @param model
     * @param mTradeModel
     * @return
     * @throws Exception
     */
    public Model querySalesData(Model model, TradeModel mTradeModel)throws Exception{
        //获取当天所有订单
        List<TradeEntity> listSales = mTradeService.queryListTrade(mTradeModel);
        //保存产生销货订单时间点
        List<String> listTime = new ArrayList<>();
        //销货订单金额保存
        List<String> listAmount = new ArrayList<>();
        //销售总额
        BigDecimal totalAmount = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
        //销售单数
        Integer totalCount = 0;
        //销售总额
        BigDecimal salesAmount = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
        //储值单数
        Integer storeCount = 0;
        //储值总额
        BigDecimal storeAmount = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
        //次卡售卖单数
        Integer cardTimeCount = 0;
        //储值总额
        BigDecimal cardTimeAmount = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
        //退货单数
        Integer returnCount = 0;
        //退款总额
        BigDecimal returnAmount = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
        //未收银订单数
        Integer notSales = 0;
        //未收银订金额
        BigDecimal notSalesAmount = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
        //销售总额
        BigDecimal salesTotal = BigDecimal.ZERO;

        //保存产生销货的订单金额
        for (TradeEntity td : listSales) {
            //展示销货单
            if (td.getTradeType() == 1 && td.getTradeStatus() == 4) {
                listTime.add(DateFormatUtil.format(td.getServerUpdateTime(), DateFormatUtil.FORMAT_FULL_time));
                listAmount.add(td.getTradeAmount().toString());
            }

            if ((td.getBusinessType() == 1 || td.getBusinessType() == 4)&& td.getTradeStatus() == 4) {//销货单 已支付完成
                totalCount += 1;
                salesAmount = salesAmount.add(td.getTradeAmount());

            } else if (td.getBusinessType() == 2 && td.getTradeStatus() == 4) {//余额储值 已支付完成
                storeCount += 1;
                storeAmount = storeAmount.add(td.getTradeAmount());
            } else if(td.getBusinessType() == 3 && td.getTradeStatus() == 4){//次卡储值 已支付完成
                cardTimeCount += 1;
                cardTimeAmount = cardTimeAmount.add(td.getTradeAmount());
            }else if (td.getTradeType() == 2 && td.getTradeStatus() == 5) {//退货数据信息
                returnCount += 1;
                returnAmount = returnAmount.add(td.getTradeAmount());
            } else if (td.getTradeStatus() == 3 && td.getTradePayStatus() == 1) {//已确认，未收银
                notSales += 1;
                notSalesAmount = storeAmount.add(td.getTradeAmount());
            }
        }
        salesTotal = salesAmount.add(storeAmount).add(cardTimeAmount).subtract(returnAmount);

        //销售额
        model.addAttribute("salesTotal", salesTotal);
        model.addAttribute("totalCount", totalCount+storeCount+cardTimeCount+returnCount);
        //销货额
        model.addAttribute("salesAmount", salesAmount);
        model.addAttribute("salesCount", totalCount);
        //次卡、余额储值总额
        model.addAttribute("saveAmount", storeAmount.add(cardTimeAmount));
        model.addAttribute("saveCount", storeCount+cardTimeCount);
        //余额储值
        model.addAttribute("storeCount", storeCount);
        model.addAttribute("storeAmount", storeAmount);
        //次卡
        model.addAttribute("cardTimeCount", cardTimeCount);
        model.addAttribute("cardTimeAmount", cardTimeAmount);
        //退货
        model.addAttribute("returnCount", returnCount);
        model.addAttribute("returnAmount", returnAmount);
        //未支付
        model.addAttribute("notSales", notSales);
        model.addAttribute("notSalesAmount", notSalesAmount);

        return model;
    }

    /**
     * 获取支付相关报表
     * @param model
     * @param mTradeModel
     */
    public void queryPaymentReport(Model model, TradeModel mTradeModel) throws Exception{

        PaymentItemModel mPaymentItemModel = new PaymentItemModel();
        mPaymentItemModel.setBrandIdenty(mTradeModel.getBrandIdenty());
        mPaymentItemModel.setShopIdenty(mTradeModel.getShopIdenty());
        mPaymentItemModel.setStartDate(mTradeModel.getStartDate());
        mPaymentItemModel.setEndDate(mTradeModel.getEndDate());
        List<PaymentItemModel> listData = mPaymentItemService.queryPaymentItemReport(mPaymentItemModel);
        List<String> listName = new ArrayList<>();
        List<BigDecimal> listAmount = new ArrayList<>();
        List<Long> listCount = new ArrayList<>();

        Long maxCount = 0l;
        Long maxAmount = 0l;

        BigDecimal hyAmount = BigDecimal.ZERO;
        BigDecimal xjAmount = BigDecimal.ZERO;
        BigDecimal yhAmount = BigDecimal.ZERO;
        BigDecimal wxAmount = BigDecimal.ZERO;
        BigDecimal zfAmount = BigDecimal.ZERO;
        for(PaymentItemModel ptm : listData){
            listName.add(ptm.getPayModeName());
            listAmount.add(ptm.getTotalAmount());
            listCount.add(ptm.getCount());

            switch (ptm.getPayModeId()){
                case 1:
                    hyAmount = ptm.getTotalAmount();
                    break;
                case 2:
                    xjAmount = ptm.getTotalAmount();
                    break;
                case 3:
                    yhAmount = ptm.getTotalAmount();
                    break;
                case 4:
                    wxAmount = ptm.getTotalAmount();
                    break;
                case 5:
                    zfAmount = ptm.getTotalAmount();
                    break;
            }

            if (maxCount < ptm.getCount()) {
                maxCount = ptm.getCount();
            }
            if (maxAmount < ptm.getTotalAmount().longValue()) {
                maxAmount = ptm.getTotalAmount().longValue();
            }
        }

        maxCount = ToolsUtil.getMaxData(maxCount);
        maxAmount = ToolsUtil.getMaxData(maxAmount);

        model.addAttribute("payMaxCount", maxCount);
        model.addAttribute("payIntervalCount", maxCount / 10);
        model.addAttribute("payMaxAmount", maxAmount);
        model.addAttribute("payIntervalAmount", maxAmount / 10);


        model.addAttribute("hyAmount", hyAmount);
        model.addAttribute("xjAmount", xjAmount);
        model.addAttribute("yhAmount", yhAmount);
        model.addAttribute("wxAmount", wxAmount);
        model.addAttribute("zfAmount", zfAmount);

        model.addAttribute("listPayData", listData);
        model.addAttribute("listName", listName);
        model.addAttribute("listAmount", listAmount);
        model.addAttribute("listCount", listCount);
    }

    public Model queryShopMessage(Model model, TradeModel mTradeModel) throws Exception {

        BrandEntity brand = mBrandService.queryBrandByAppId(mTradeModel.getBrandIdenty());

        List<CommercialEntity> listCommercial = mCommercialService.queryCommercialByBrandId(brand.getId());

        model.addAttribute("brand", brand);
        model.addAttribute("listCommercial", listCommercial);
        return model;
    }

    @RequestMapping("/export/excel")
    public void exportExcel(HttpServletResponse response, TradeModel mTradeModel) throws Exception{

        List<ReportSalesExportModel> listData = mTradeService.querySalseExportExcel(mTradeModel);

        ExcelData data = new ExcelData();
        data.setSheetName("营业报表");
        List<String> titles = new ArrayList();
        titles.add("序");
        titles.add("门店编号");
        titles.add("交易时间");
        titles.add("交易类别");
        titles.add("交易类型");
        titles.add("交易金额");
        titles.add("交易来源");
        titles.add("交易方式");
        titles.add("交易状态");

        data.setTitles(titles);

        List<List<Object>> rows = new ArrayList();
        data.setRows(rows);

        int i = 1;
        if(listData != null){
            for (ReportSalesExportModel entity : listData) {
                List<Object> row = new ArrayList();
                rows.add(row);
                row.add(i++);
                row.add(entity.getShopIdenty());
                row.add(entity.getTradeDate());
                row.add(entity.getTradeType());
                row.add(entity.getBusinessType());
                row.add(entity.getTradeAmount());
                row.add(entity.getTradeSource());
                row.add(entity.getTradeMode());
                row.add(entity.getTradeState());
            }
        }

        SimpleDateFormat fdate = new SimpleDateFormat("yyyyMMdd");
        String fileName = String.format("营业统计报表-%s.xls", fdate.format(new Date()));
        ExcelUtils.exportExcel(response, fileName, data);
    }
}
