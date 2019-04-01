package com.zhongmei.yunfu.api.web;

import com.zhongmei.yunfu.controller.model.PurchSaleModel;
import com.zhongmei.yunfu.controller.model.ReportSalesExportModel;
import com.zhongmei.yunfu.controller.model.TradeModel;
import com.zhongmei.yunfu.controller.model.excel.ExcelData;
import com.zhongmei.yunfu.controller.model.excel.ExcelUtils;
import com.zhongmei.yunfu.domain.entity.DishShopEntity;
import com.zhongmei.yunfu.domain.entity.PurchaseAndSaleEntity;
import com.zhongmei.yunfu.domain.entity.PurchaseSaleReport;
import com.zhongmei.yunfu.domain.entity.TradeItemEntity;
import com.zhongmei.yunfu.service.DishShopService;
import com.zhongmei.yunfu.service.PurchasSaleService;
import com.zhongmei.yunfu.service.TradeItemService;
import com.zhongmei.yunfu.service.TradeService;
import com.zhongmei.yunfu.util.DateFormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 进销存报表
 */
@Controller
@RequestMapping("/internal/purchaseSale")
public class ReportPurchaseSaleController {

    @Autowired
    TradeService mTradeService;
    @Autowired
    TradeItemService mTradeItemService;
    @Autowired
    PurchasSaleService mPurchasSaleService;
    @Autowired
    DishShopService mDishShopService;

    @RequestMapping("/report")
    public String reportAddCustomer(Model model, PurchSaleModel mPurchSaleModel) {

        try {

            List<PurchaseSaleReport> listPurchaseSaleReport = queryPurchaseSaleData(mPurchSaleModel);

            model.addAttribute("listPurchaseSaleReport", listPurchaseSaleReport);
            model.addAttribute("mPurchSaleModel", mPurchSaleModel);

            return "report_purchase_sale";
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

    }

    public List<PurchaseSaleReport> queryPurchaseSaleData(PurchSaleModel mPurchSaleModel) throws Exception{
        //设置默认查询时间
        if (mPurchSaleModel.getStartDate() == null) {
            Calendar c = Calendar.getInstance();
            //过去15天
            c.setTime(new Date());
            c.add(Calendar.DATE, -15);
            Date start = c.getTime();
            String temp = DateFormatUtil.format(start, DateFormatUtil.FORMAT_FULL_DATE);
            mPurchSaleModel.setStartDate(temp);

        }
        if (mPurchSaleModel.getEndDate() == null) {
            mPurchSaleModel.setEndDate(DateFormatUtil.format(new Date(), DateFormatUtil.FORMAT_FULL_DATE));
        }


        List<PurchaseAndSaleEntity> listPurchaseSale = mPurchasSaleService.queryPurchase(mPurchSaleModel);
        Map<Long,PurchaseAndSaleEntity> mapPurchaseSale = new HashMap<>();
        for(PurchaseAndSaleEntity purchaseAndSale : listPurchaseSale){
            mapPurchaseSale.put(purchaseAndSale.getDishShopId(),purchaseAndSale);
        }

        TradeModel mTradeModel = new TradeModel();
        mTradeModel.setBrandIdenty(mPurchSaleModel.getBrandIdenty());
        mTradeModel.setShopIdenty(mPurchSaleModel.getShopIdenty());
        mTradeModel.setStartDate(mPurchSaleModel.getStartDate());
        mTradeModel.setEndDate(mPurchSaleModel.getEndDate());
        mTradeModel.setTradeType(1);
        mTradeModel.setTradeStatus(4);
        //获取销货数据
        List<TradeItemEntity> listSaleDish = mTradeItemService.dishSaleData(mTradeModel);
        Map<String,TradeItemEntity> mapDishSale = new HashMap<>();
        for(TradeItemEntity item : listSaleDish){
            mapDishSale.put(item.getDishId(),item);
        }
        mTradeModel.setTradeType(2);
        mTradeModel.setTradeStatus(5);
        //获取销货数据
        List<TradeItemEntity> listReturnDish = mTradeItemService.dishSaleData(mTradeModel);
        Map<String,TradeItemEntity> mapDishReturn = new HashMap<>();
        for(TradeItemEntity item : listReturnDish){
            mapDishReturn.put(item.getDishId(),item);
        }

        DishShopEntity mDishShopEntity = new DishShopEntity();
        mDishShopEntity.setShopIdenty(mPurchSaleModel.getShopIdenty());
        mDishShopEntity.setBrandIdenty(mPurchSaleModel.getBrandIdenty());
        mDishShopEntity.setName(mPurchSaleModel.getName());
        List<DishShopEntity> listDishShop = mDishShopService.queryAllDishShop(mDishShopEntity);
        List<PurchaseSaleReport> listPurchaseSaleReport = new ArrayList<>();
        for(DishShopEntity dishShop : listDishShop){
            PurchaseSaleReport mPurchaseSaleReport = new PurchaseSaleReport();
            mPurchaseSaleReport.setName(dishShop.getName());
            mPurchaseSaleReport.setDishQty(dishShop.getDishQty());
            mPurchaseSaleReport.setDishId(dishShop.getId());

            PurchaseAndSaleEntity purchaseAndSale = mapPurchaseSale.get(dishShop.getId());
            if(purchaseAndSale != null){
                mPurchaseSaleReport.setNumber(purchaseAndSale.getNumber());
                mPurchaseSaleReport.setTotalPurchasePrice(purchaseAndSale.getTotalPurchasePrice());
            }

            TradeItemEntity salseTradeItem = mapDishSale.get(dishShop.getId().toString());

            TradeItemEntity returnTradeItem = mapDishReturn.get(dishShop.getId().toString());


            mPurchaseSaleReport.setSalePrice(BigDecimal.ZERO);
            mPurchaseSaleReport.setSaleTotal(BigDecimal.ZERO);

            if(salseTradeItem != null){
                mPurchaseSaleReport.setSalePrice(salseTradeItem.getActualAmount());
                mPurchaseSaleReport.setSaleTotal(salseTradeItem.getQuantity());
            }
            if(returnTradeItem != null){
                mPurchaseSaleReport.setSalePrice(mPurchaseSaleReport.getSalePrice().subtract(returnTradeItem.getActualAmount()));

                mPurchaseSaleReport.setSaleTotal(mPurchaseSaleReport.getSaleTotal().subtract(returnTradeItem.getQuantity()));
            }

            listPurchaseSaleReport.add(mPurchaseSaleReport);

        }
        return listPurchaseSaleReport;
    }

    @RequestMapping("/detail")
    public String purchaseSaleDetail(Model model, PurchSaleModel mPurchSaleModel){
        try {
            TradeModel mTradeModel = new TradeModel();
            mTradeModel.setShopIdenty(mPurchSaleModel.getShopIdenty());
            mTradeModel.setBrandIdenty(mPurchSaleModel.getBrandIdenty());
            mTradeModel.setStartDate(mPurchSaleModel.getStartDate());
            mTradeModel.setEndDate(mPurchSaleModel.getEndDate());
            //获取品项销售情况
            List<TradeItemEntity> listDishSale = mTradeItemService.dishSaleDetail(mTradeModel);

            List<PurchaseAndSaleEntity> listPurchas =  mPurchasSaleService.queryPurchaseDetail(mPurchSaleModel);

            return "report_purchase_sale_detail";
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

    }

    @RequestMapping("/export/excel")
    public void exportExcel(HttpServletResponse response, PurchSaleModel mPurchSaleModel) throws Exception{

        List<PurchaseSaleReport> listPurchaseSaleReport = queryPurchaseSaleData(mPurchSaleModel);

        ExcelData data = new ExcelData();
        data.setSheetName("进销存报表");
        List<String> titles = new ArrayList();
        titles.add("序");
        titles.add("品项名称");
        titles.add("剩余库存");
        titles.add("入库数量");
        titles.add("采购金额");
        titles.add("销货数量");
        titles.add("销货金额");

        data.setTitles(titles);

        List<List<Object>> rows = new ArrayList();
        data.setRows(rows);

        int i = 1;
        if(listPurchaseSaleReport != null){
            for (PurchaseSaleReport entity : listPurchaseSaleReport) {
                List<Object> row = new ArrayList();
                rows.add(row);
                row.add(i++);
                row.add(entity.getName());
                row.add(entity.getDishQty());
                row.add(entity.getNumber());
                row.add(entity.getTotalPurchasePrice());
                row.add(entity.getSaleTotal());
                row.add(entity.getSalePrice());
            }
        }

        SimpleDateFormat fdate = new SimpleDateFormat("yyyyMMdd");
        String fileName = String.format("进销存报表-%s.xls", fdate.format(new Date()));
        ExcelUtils.exportExcel(response, fileName, data);
    }
}
