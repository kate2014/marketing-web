package com.zhongmei.yunfu.api.web;

import com.zhongmei.yunfu.controller.model.PurchSaleModel;
import com.zhongmei.yunfu.controller.model.ReportSalesExportModel;
import com.zhongmei.yunfu.controller.model.TradeModel;
import com.zhongmei.yunfu.controller.model.excel.ExcelData;
import com.zhongmei.yunfu.controller.model.excel.ExcelUtils;
import com.zhongmei.yunfu.domain.entity.*;
import com.zhongmei.yunfu.service.*;
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
    @Autowired
    TradeCustomerService mTradeCustomerService;
    @Autowired
    TradeUserService mTradeUserService;

    @RequestMapping("/goToReport")
    public String goToreport(Model model, PurchSaleModel mPurchSaleModel) {

        try {

            model.addAttribute("mPurchSaleModel", mPurchSaleModel);

            return "report_purchase_sale_main";
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

    }

    @RequestMapping("/report")
    public String reportPurchaseSale(Model model, PurchSaleModel mPurchSaleModel) {

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
            c.add(Calendar.DATE, -7);
            Date start = c.getTime();
            String temp = DateFormatUtil.format(start, DateFormatUtil.FORMAT_FULL_DATE);
            mPurchSaleModel.setStartDate(temp);

        }
        if (mPurchSaleModel.getEndDate() == null) {
            mPurchSaleModel.setEndDate(DateFormatUtil.format(new Date(), DateFormatUtil.FORMAT_FULL_DATE));
        }

        mPurchSaleModel.setType(1);
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

    @RequestMapping("/purchase")
    public String purchaseList(Model model, PurchSaleModel mPurchSaleModel){
        try {
            List<PurchaseSaleReport> listData = listPurchase(mPurchSaleModel);

            BigDecimal totalPurchaseCount = BigDecimal.ZERO;
            BigDecimal totalPurchaseAmount = BigDecimal.ZERO;
            BigDecimal totalSHCount = BigDecimal.ZERO;
            BigDecimal totalSHAmount = BigDecimal.ZERO;

            for(PurchaseSaleReport ps : listData){
                if(ps.getType() == 1){
                    if(ps.getNumber() != null){
                        totalPurchaseCount = totalPurchaseCount.add(ps.getNumber());
                    }
                    if(ps.getTotalPurchasePrice() != null){
                        totalPurchaseAmount = totalPurchaseAmount.add(ps.getTotalPurchasePrice());
                    }
                }else{
                    if(ps.getNumber() != null){
                        totalSHCount = totalSHCount.add(ps.getNumber());
                    }
//                    if(ps.getTotalPurchasePrice() != null){
//                        totalSHAmount = totalSHAmount.add(ps.getTotalPurchasePrice());
//                    }
                }
            }

            model.addAttribute("listData", listData);
            model.addAttribute("totalPurchaseCount", totalPurchaseCount);
            model.addAttribute("totalPurchaseAmount", totalPurchaseAmount);
            model.addAttribute("totalSHCount", totalSHCount);
//            model.addAttribute("totalSHAmount", totalSHAmount);

            model.addAttribute("mPurchSaleModel", mPurchSaleModel);
            return "report_purchase";
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

    }

    public List<PurchaseSaleReport> listPurchase(PurchSaleModel mPurchSaleModel)throws Exception{
        //设置默认查询时间
        if (mPurchSaleModel.getStartDate() == null) {
            Calendar c = Calendar.getInstance();
            //过去15天
            c.setTime(new Date());
            c.add(Calendar.DATE, -7);
            Date start = c.getTime();
            String temp = DateFormatUtil.format(start, DateFormatUtil.FORMAT_FULL_DATE);
            mPurchSaleModel.setStartDate(temp);

        }

        if (mPurchSaleModel.getEndDate() == null) {
            mPurchSaleModel.setEndDate(DateFormatUtil.format(new Date(), DateFormatUtil.FORMAT_FULL_DATE));
        }

        List<PurchaseSaleReport> listData = mPurchasSaleService.purchaseList(mPurchSaleModel);

        return listData;
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

    @RequestMapping("/saleReport")
    public String saleReport(Model model, PurchSaleModel mPurchSaleModel){
        try {

            List<DishSaleReport> listDishSale = queryDishSale(mPurchSaleModel);
            model.addAttribute("listData", listDishSale);
            model.addAttribute("mPurchSaleModel", mPurchSaleModel);

            return "report_dish_sale";
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

    }

    public List<DishSaleReport> queryDishSale(PurchSaleModel mPurchSaleModel)throws Exception{
        //设置默认查询时间
        if (mPurchSaleModel.getStartDate() == null) {
            Calendar c = Calendar.getInstance();
            //过去15天
            c.setTime(new Date());
            c.add(Calendar.DATE, -7);
            Date start = c.getTime();
            String temp = DateFormatUtil.format(start, DateFormatUtil.FORMAT_FULL_DATE);
            mPurchSaleModel.setStartDate(temp);

        }

        if (mPurchSaleModel.getEndDate() == null) {
            mPurchSaleModel.setEndDate(DateFormatUtil.format(new Date(), DateFormatUtil.FORMAT_FULL_DATE));
        }

        TradeModel mTradeModel = new TradeModel();
        mTradeModel.setShopIdenty(mPurchSaleModel.getShopIdenty());
        mTradeModel.setBrandIdenty(mPurchSaleModel.getBrandIdenty());
        mTradeModel.setStartDate(mPurchSaleModel.getStartDate());
        mTradeModel.setEndDate(mPurchSaleModel.getEndDate());
        mTradeModel.setTradeType(mPurchSaleModel.getType());
        mTradeModel.setDishName(mPurchSaleModel.getName());
        mTradeModel.setTradeUser(mPurchSaleModel.getTradeUser());
        mTradeModel.setCustomerName(mPurchSaleModel.getCustomerName());

        List<DishSaleReport> listDishSale = new ArrayList<>();
        listDishSale.addAll(mTradeItemService.listSaleReport(mTradeModel));

        //获取所以的tradeCustomer
        mTradeModel.setCustomerType(3);
        List<TradeCustomerEntity> listCustomer = mTradeCustomerService.queryTradeCustomerList(mTradeModel);
        Map<Long,TradeCustomerEntity> customerMap = new HashMap<>();
        for(TradeCustomerEntity tradeCustomer : listCustomer){
            customerMap.put(tradeCustomer.getTradeId(),tradeCustomer);
        }

        //获取订单服务员信息
        List<TradeUserEntity> listUser = mTradeUserService.queryTradeUserList(mTradeModel);
        Map<Long,TradeUserEntity> userMap = new HashMap<>();
        for(TradeUserEntity tradeUser : listUser){
            userMap.put(tradeUser.getTradeItemId(),tradeUser);
        }

        for(DishSaleReport dishSale : listDishSale){
            TradeCustomerEntity mTradeCustomerEntity = customerMap.get(dishSale.getTradeId());

            if(mTradeCustomerEntity != null){
                dishSale.setCustomerName(mTradeCustomerEntity.getCustomerName());
            }

            TradeUserEntity mTradeUserEntity = userMap.get(dishSale.getTradeItemId());
            if(mTradeUserEntity != null){
                dishSale.setTradeUser(mTradeUserEntity.getUserName());
            }
        }

        if(mTradeModel.getTradeUser() != null && !mTradeModel.getTradeUser().equals("")){
            List<DishSaleReport> listTradeUser = new ArrayList<>();
            for(DishSaleReport dishSale : listDishSale){
                if(dishSale.getTradeUser() != null && dishSale.getTradeUser().indexOf(mTradeModel.getTradeUser()) >= 0){
                    listTradeUser.add(dishSale);
                }
            }

            listDishSale.clear();
            listDishSale.addAll(listTradeUser);

        }

        if(mTradeModel.getCustomerName() != null && !mTradeModel.getCustomerName().equals("")){
            List<DishSaleReport> listTradeCustomer = new ArrayList<>();
            for(DishSaleReport dishSale : listDishSale){
                if(dishSale.getCustomerName() != null && dishSale.getCustomerName().indexOf(mTradeModel.getCustomerName()) >= 0){
                    listTradeCustomer.add(dishSale);
                }
            }
            listDishSale.clear();
            listDishSale.addAll(listTradeCustomer);
        }

        return listDishSale;
    }

    @RequestMapping("/export/excel")
    public void exportExcel(HttpServletResponse response, PurchSaleModel mPurchSaleModel) throws Exception{

        List<PurchaseSaleReport> listPurchaseSaleReport = queryPurchaseSaleData(mPurchSaleModel);

        ExcelData data = new ExcelData();
        data.setSheetName("进销存报表");
        List<String> titles = new ArrayList();
        titles.add("序");
        titles.add("品项名称");
        titles.add("入库数量");
        titles.add("采购金额");
        titles.add("销货数量");
        titles.add("销货金额");
        titles.add("剩余库存");

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
                row.add(entity.getNumber());
                row.add(entity.getTotalPurchasePrice());
                row.add(entity.getSaleTotal());
                row.add(entity.getSalePrice());
                row.add(entity.getDishQty());
            }
        }

        SimpleDateFormat fdate = new SimpleDateFormat("yyyyMMdd");
        String fileName = String.format("进销存报表-%s.xls", fdate.format(new Date()));
        ExcelUtils.exportExcel(response, fileName, data);
    }
    @RequestMapping("/export/purchase")
    public void exportPurchase(HttpServletResponse response, PurchSaleModel mPurchSaleModel) throws Exception{

        List<PurchaseSaleReport> listData = listPurchase(mPurchSaleModel);

        ExcelData data = new ExcelData();
        data.setSheetName("品项入库报表");
        List<String> titles = new ArrayList();
        titles.add("序");
        titles.add("品项名称");
        titles.add("类型");
        titles.add("数量");
        titles.add("采购单价");
        titles.add("采购金额");
        titles.add("货源名称");
        titles.add("入库时间");

        data.setTitles(titles);

        List<List<Object>> rows = new ArrayList();
        data.setRows(rows);

        int i = 1;
        if(listData != null){
            for (PurchaseSaleReport entity : listData) {
                List<Object> row = new ArrayList();
                rows.add(row);
                row.add(i++);
                row.add(entity.getName());
                row.add(entity.getType());
                row.add(entity.getNumber());
                row.add(entity.getPurchasePrice());
                row.add(entity.getTotalPurchasePrice());
                row.add(entity.getSourceName());
                row.add(entity.getServerCreateTime());
            }
        }


        BigDecimal totalPurchaseCount = BigDecimal.ZERO;
        BigDecimal totalPurchaseAmount = BigDecimal.ZERO;
        BigDecimal totalSHCount = BigDecimal.ZERO;
        BigDecimal totalSHAmount = BigDecimal.ZERO;

        for(PurchaseSaleReport ps : listData){
            if(ps.getType() == 1){
                if(ps.getNumber() != null){
                    totalPurchaseCount = totalPurchaseCount.add(ps.getNumber());
                }
                if(ps.getTotalPurchasePrice() != null){
                    totalPurchaseAmount = totalPurchaseAmount.add(ps.getTotalPurchasePrice());
                }
            }else{
                if(ps.getNumber() != null){
                    totalSHCount = totalSHCount.add(ps.getNumber());
                }
//                if(ps.getTotalPurchasePrice() != null){
//                    totalSHAmount = totalSHAmount.add(ps.getTotalPurchasePrice());
//                }
            }
        }

        List<Object> row = new ArrayList();
        rows.add(row);
        row.add(i++);
        row.add("合计");
        row.add("采购总数量："+totalPurchaseCount);
        row.add("采购总总金额："+totalPurchaseAmount);
        row.add("损耗总数量："+totalSHCount);

        SimpleDateFormat fdate = new SimpleDateFormat("yyyyMMdd");
        String fileName = String.format("品项入库报表-%s.xls", fdate.format(new Date()));
        ExcelUtils.exportExcel(response, fileName, data);
    }

    @RequestMapping("/export/dishSale")
    public void exportDishSale(HttpServletResponse response, PurchSaleModel mPurchSaleModel) throws Exception{

        List<DishSaleReport> listDishSale = queryDishSale(mPurchSaleModel);

        ExcelData data = new ExcelData();
        data.setSheetName("品项销售详情报表");
        List<String> titles = new ArrayList();
        titles.add("序");
        titles.add("消耗类型");
        titles.add("订单号");
        titles.add("品项名称");
        titles.add("消耗数量");
        titles.add("销售金额");
        titles.add("服务员名称");
        titles.add("顾客名称");
        titles.add("消耗时间");

        data.setTitles(titles);

        List<List<Object>> rows = new ArrayList();
        data.setRows(rows);

        int i = 1;
        if(listDishSale != null){
            for (DishSaleReport entity : listDishSale) {
                List<Object> row = new ArrayList();
                rows.add(row);
                row.add(i++);
                row.add(entity.getType());
                row.add(entity.getTradeNo()
                );
                row.add(entity.getName());
                row.add(entity.getNumber());
                row.add(entity.getActualAmount());
                row.add(entity.getTradeUser());
                row.add(entity.getCustomerName());
                row.add(entity.getServerCreateTime());
            }
        }

        SimpleDateFormat fdate = new SimpleDateFormat("yyyyMMdd");
        String fileName = String.format("品项销售详情报表-%s.xls", fdate.format(new Date()));
        ExcelUtils.exportExcel(response, fileName, data);
    }
}
