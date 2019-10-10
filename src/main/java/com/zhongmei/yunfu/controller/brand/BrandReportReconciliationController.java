package com.zhongmei.yunfu.controller.brand;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.BaseController;
import com.zhongmei.yunfu.controller.model.PurchSaleModel;
import com.zhongmei.yunfu.controller.model.ShopSearchModel;
import com.zhongmei.yunfu.controller.model.TradeModel;
import com.zhongmei.yunfu.controller.model.excel.ExcelData;
import com.zhongmei.yunfu.controller.model.excel.ExcelUtils;
import com.zhongmei.yunfu.domain.entity.BrandEntity;
import com.zhongmei.yunfu.domain.entity.CommercialEntity;
import com.zhongmei.yunfu.domain.entity.DishReport;
import com.zhongmei.yunfu.domain.entity.bean.ShopSalesReport;
import com.zhongmei.yunfu.service.BrandService;
import com.zhongmei.yunfu.service.CommercialService;
import com.zhongmei.yunfu.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 对账报表
 */
@Controller
@RequestMapping("/internal/brand/report")
public class BrandReportReconciliationController extends BaseController {

    @Autowired
    TradeService mTradeService;

    @Autowired
    BrandService mBrandService;

    @Autowired
    CommercialService mCommercialService;

    @RequestMapping("/reconciliation")
    public String reportReconciliation(Model model, PurchSaleModel mPurchSaleModel) {


        try {
            //初始化查询时间
            if(mPurchSaleModel.getStartDate() == null || mPurchSaleModel.getEndDate() == null || mPurchSaleModel.getStartDate().equals("") || mPurchSaleModel.getEndDate().equals("")){

                if(mPurchSaleModel.getSearchDate() == null){
                    mPurchSaleModel.setSearchDate(1);
                }

                int searchDate = mPurchSaleModel.getSearchDate();

                Calendar cal=Calendar.getInstance();
                cal.add(Calendar.DATE,0);

                if(searchDate == 1){
                    cal.add(Calendar.DATE,0);
                }else if(searchDate == 2){
                    cal.add(Calendar.DATE,-1);
                }else if(searchDate == 3){
                    cal.add(Calendar.DATE,-2);
                }
                Date d=cal.getTime();
                SimpleDateFormat sp=new SimpleDateFormat("yyyy-MM-dd");
                String ZUOTIAN=sp.format(d);//获取昨天日期

                String startDate = ZUOTIAN + " 00:00:00";
                String endDate = ZUOTIAN + " 23:59:59";

                mPurchSaleModel.setStartDate(startDate);
                mPurchSaleModel.setEndDate(endDate);
            }

            queryShopMessage(model,mPurchSaleModel);

            Page<CommercialEntity> listData = queryCommerical(mPurchSaleModel);

            List<ShopSalesReport> showReportData = queryData(mPurchSaleModel,listData.getRecords(),false);

            setWebPage(model, "/internal/brand/report/reconciliation", listData, mPurchSaleModel);

            model.addAttribute("listReportData", showReportData);
            model.addAttribute("mPurchSaleModel", mPurchSaleModel);

        }catch (Exception e){
            e.printStackTrace();
        }


        model.addAttribute("mPurchSaleModel", mPurchSaleModel);
        return "brand_report_reconciliation";
    }

    public Page<CommercialEntity> queryCommerical(PurchSaleModel mPurchSaleModel) throws Exception{
        ShopSearchModel mShopSearchModel = new ShopSearchModel ();
        mShopSearchModel.setBrandIdenty(mPurchSaleModel.getBrandIdenty());
        mShopSearchModel.setCommercialName(mPurchSaleModel.getName());
        mShopSearchModel.setShopIdenty(mPurchSaleModel.getShopIdenty());
        mShopSearchModel.setInvalidStatus(1);

        Page<CommercialEntity> listData = mCommercialService.queryCommercialList(mShopSearchModel,mPurchSaleModel.getPageNo(),mPurchSaleModel.getPageSize());
        return listData;
    }
    public List<ShopSalesReport> queryData(PurchSaleModel mPurchSaleModel,List<CommercialEntity> listData,boolean isExcel) throws Exception{


        String shopIds = null;
        if(!isExcel){
            for(CommercialEntity entity : listData){
                if(shopIds == null){
                    shopIds = entity.getCommercialId().toString();
                }else{
                    shopIds = shopIds+","+entity.getCommercialId();
                }

            }
        }


        //查询门店销货统计
        TradeModel mTradeModel = new TradeModel();
        mTradeModel.setBrandIdenty(mPurchSaleModel.getBrandIdenty());
        mTradeModel.setTradeStatus(4);
        mTradeModel.setTradeType(1);
        mTradeModel.setStartDate(mPurchSaleModel.getStartDate());
        mTradeModel.setEndDate(mPurchSaleModel.getEndDate());
        List<ShopSalesReport> listSalesReport = mTradeService.batchQuerySalesReport(mTradeModel,shopIds);

        //查询门店退货统计
        mTradeModel.setTradeStatus(5);
        mTradeModel.setTradeType(2);
        List<ShopSalesReport> listReturnReport = mTradeService.batchQuerySalesReport(mTradeModel,shopIds);

        //构建界面展示数据
        List<ShopSalesReport> showReportData = new LinkedList<>();
        for(CommercialEntity entity : listData){
            ShopSalesReport tempEntity = new ShopSalesReport();
            tempEntity.setShopName(entity.getCommercialName());
            tempEntity.setShopIdenty(entity.getCommercialId());

            tempEntity.setTotalAmount(BigDecimal.ZERO);
            tempEntity.setTotalAmountCount(BigDecimal.ZERO);
            tempEntity.setTotalSave(BigDecimal.ZERO);
            tempEntity.setTotalSaveCount(BigDecimal.ZERO);
            tempEntity.setTotalCard(BigDecimal.ZERO);
            tempEntity.setTotalCardCount(BigDecimal.ZERO);
            tempEntity.setTotalWeiXin(BigDecimal.ZERO);
            tempEntity.setTotalWeiXinCount(BigDecimal.ZERO);
            tempEntity.setReturnAmount(BigDecimal.ZERO);
            tempEntity.setReturnCount(BigDecimal.ZERO);
            tempEntity.setSalesAmount(BigDecimal.ZERO);
            tempEntity.setSalesCount(BigDecimal.ZERO);

            BigDecimal salesAmount = BigDecimal.ZERO;
            BigDecimal salesCount = BigDecimal.ZERO;

            for(ShopSalesReport sales : listSalesReport){
                if(sales.getShopIdenty().longValue() == entity.getCommercialId().longValue()){
                    if(sales.getBusinessType() == 1){
                        tempEntity.setTotalAmount(sales.getTotalAmount());
                        tempEntity.setTotalAmountCount(sales.getTotalAmountCount());
                    }else if(sales.getBusinessType() == 2){
                        tempEntity.setTotalSave(sales.getTotalAmount());
                        tempEntity.setTotalSaveCount(sales.getTotalAmountCount());
                    }else if(sales.getBusinessType() == 3){
                        tempEntity.setTotalCard(sales.getTotalAmount());
                        tempEntity.setTotalCardCount(sales.getTotalAmountCount());
                    }else if(sales.getBusinessType() == 4){
                        tempEntity.setTotalWeiXin(sales.getTotalAmount());
                        tempEntity.setTotalWeiXinCount(sales.getTotalAmountCount());
                    }

                    salesAmount = salesAmount.add(sales.getTotalAmount());
                    salesCount = salesCount.add(sales.getTotalAmountCount());
                }
            }

            for(ShopSalesReport ret : listReturnReport){
                if(ret.getShopIdenty().longValue() == entity.getCommercialId().longValue()){
                    tempEntity.setReturnAmount(ret.getTotalAmount().add(tempEntity.getReturnAmount()));
                    tempEntity.setReturnCount(ret.getTotalAmountCount().add(tempEntity.getReturnCount()));

                    salesAmount = salesAmount.subtract(ret.getTotalAmount());
                    salesCount = salesCount.subtract(ret.getTotalAmountCount());
                }
            }
            tempEntity.setSalesAmount(salesAmount);
            tempEntity.setSalesCount(salesCount);

            showReportData.add(tempEntity);
        }
        return showReportData;
    }

    public Model queryShopMessage(Model model, PurchSaleModel mPurchSaleModel) throws Exception {

        BrandEntity brand = mBrandService.queryBrandByAppId(mPurchSaleModel.getBrandIdenty());

        List<CommercialEntity> listCommercial = mCommercialService.queryCommercialByBrandId(brand.getId());

        model.addAttribute("brand", brand);
        model.addAttribute("listCommercial", listCommercial);
        return model;
    }

    /**
     * 导出数据
     * @param response
     * @param mPurchSaleModel
     * @throws Exception
     */
    @RequestMapping("/reconciliation/excel")
    public void exportExcel(HttpServletResponse response, PurchSaleModel mPurchSaleModel) throws Exception{

        List<CommercialEntity> listCommercial = mCommercialService.queryCommercialByBrandId(mPurchSaleModel.getBrandIdenty());

        List<ShopSalesReport> showReportData = queryData(mPurchSaleModel,listCommercial,true);

        ExcelData data = new ExcelData();
        data.setSheetName("对账报表");
        List<String> titles = new ArrayList();
        titles.add("序");
        titles.add("门店名称");
        titles.add("门店交易笔数");
        titles.add("门店交易金额");
        titles.add("储值笔数");
        titles.add("储值金额");
        titles.add("次卡售卖笔数");
        titles.add("次卡售卖金额");
        titles.add("小程序交易笔数");
        titles.add("小程序交易金额");
        titles.add("退货笔数");
        titles.add("退货金额");
        titles.add("总交易笔数");
        titles.add("总交易金额");

        data.setTitles(titles);

        List<List<Object>> rows = new ArrayList();
        data.setRows(rows);

        int i = 1;
        if(showReportData != null){
            for (ShopSalesReport entity : showReportData) {
                List<Object> row = new ArrayList();
                rows.add(row);
                row.add(i++);
                row.add(entity.getShopName());
                row.add(entity.getTotalAmountCount());
                row.add(entity.getTotalAmount());
                row.add(entity.getTotalSaveCount());
                row.add(entity.getTotalSave());
                row.add(entity.getTotalCardCount());
                row.add(entity.getTotalCard());
                row.add(entity.getTotalWeiXinCount());
                row.add(entity.getTotalWeiXin());
                row.add(entity.getReturnCount());
                row.add(entity.getReturnAmount());
                row.add(entity.getSalesCount());
                row.add(entity.getSalesAmount());
            }
        }

        SimpleDateFormat fdate = new SimpleDateFormat("yyyyMMdd");
        String fileName = String.format("对账报表-%s.xls", fdate.format(new Date()));
        ExcelUtils.exportExcel(response, fileName, data);
    }

}
