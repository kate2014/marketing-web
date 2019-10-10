package com.zhongmei.yunfu.controller.brand;


import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.BaseController;
import com.zhongmei.yunfu.controller.model.CustomerExtraModel;
import com.zhongmei.yunfu.controller.model.PurchSaleModel;
import com.zhongmei.yunfu.controller.model.ShopSearchModel;
import com.zhongmei.yunfu.controller.model.TradeModel;
import com.zhongmei.yunfu.controller.model.excel.ExcelData;
import com.zhongmei.yunfu.controller.model.excel.ExcelUtils;
import com.zhongmei.yunfu.domain.entity.BrandEntity;
import com.zhongmei.yunfu.domain.entity.CommercialEntity;
import com.zhongmei.yunfu.domain.entity.CustomerExtraEntity;
import com.zhongmei.yunfu.domain.entity.CustomerSaveReport;
import com.zhongmei.yunfu.domain.entity.bean.ShopSalesReport;
import com.zhongmei.yunfu.service.*;
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

@Controller
@RequestMapping("/internal/brand/report/")
public class BrandReportSaveController extends BaseController {

    @Autowired
    TradeService mTradeService;
    @Autowired
    TradeItemService mTradeItemService;
    @Autowired
    BrandService mBrandService;
    @Autowired
    CommercialService mCommercialService;
    @Autowired
    CustomerStoredService mCustomerStoredService;
    @Autowired
    CustomerService mCustomerService;

    @RequestMapping("/customerSave")
    public String cardTimeReport(Model model, TradeModel mTradeModel) {

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

            queryBrandSaveData(model,mTradeModel);

            queryBrandSaveDetail(model,mTradeModel);

            //获取各门店储值情况
            Page<CommercialEntity> listShopPage = queryCommerical(mTradeModel);
            List<CustomerExtraModel> listExtra = queryShopSaveData(listShopPage.getRecords(),mTradeModel,false);

            setWebPage(model, "/internal/brand/report/customerSave", listShopPage, mTradeModel);

            model.addAttribute("listExtra", listExtra);

            model.addAttribute("mTradeModel", mTradeModel);
        }catch (Exception e){
            e.printStackTrace();

        }


        return "brand_report_save";
    }


    /**
     * 获取品牌储值情况
     * @param model
     * @param mTradeModel
     */
    public void queryBrandSaveData(Model model,TradeModel mTradeModel)throws Exception{

        CustomerExtraEntity mCustomerSaveReport = mCustomerService.queryCustomerSaveReport(mTradeModel.getBrandIdenty(),null);
        BigDecimal saveBaseAmount = mCustomerSaveReport.getStoredAmount().subtract(mCustomerSaveReport.getStoredGive());
        model.addAttribute("saveBaseAmount", saveBaseAmount);
        model.addAttribute("mCustomerSaveReport", mCustomerSaveReport);
    }

    /**
     * 获取品牌储值曲线图数据
     * @param model
     * @param mTradeModel
     * @throws Exception
     */
    public void queryBrandSaveDetail(Model model,TradeModel mTradeModel)throws Exception {

        List<CustomerSaveReport> listSaveData = mCustomerStoredService.querySaveData(mTradeModel);

        Map<String,String> dateArray = new LinkedHashMap<>();
        Map<String,Integer> countArray = new LinkedHashMap<>();
        Map<String,BigDecimal> saveArray = new LinkedHashMap();
        Map<String,BigDecimal> giveArray = new LinkedHashMap<>();
        Map<String,BigDecimal> saleArray = new LinkedHashMap<>();

        Long maxCount = 0l;
        Long maxAmount = 0l;

        //交易总笔数
        int totalTrade = 0;
        //储值消费总额
        BigDecimal totalSale = BigDecimal.ZERO;
        //储值总额
        BigDecimal totalSave = BigDecimal.ZERO;
        //赠送总额
        BigDecimal totalGive = BigDecimal.ZERO;

        for(CustomerSaveReport entity : listSaveData){

            dateArray.put(entity.getCreateDate(),entity.getCreateDate());
            Integer count = countArray.get(entity.getCreateDate());

            totalTrade += entity.getTradeCount();

            if(count == null){
                countArray.put(entity.getCreateDate(),entity.getTradeCount());
            }else {
                countArray.put(entity.getCreateDate(),entity.getTradeCount()+count);
            }


            //交易类型：1 表示储值  2 表示消费
            if(entity.getRecordype() == 1){
                saveArray.put(entity.getCreateDate(),entity.getTradeAmount());
                BigDecimal giveAmount = entity.getGiveAmount();
                if(giveAmount == null){
                    giveAmount = BigDecimal.ZERO;
                }
                giveArray.put(entity.getCreateDate(),giveAmount);

                BigDecimal saleAmount = saleArray.get(entity.getCreateDate());
                if(saleAmount == null){
                    saleArray.put(entity.getCreateDate(),BigDecimal.ZERO);
                }

                totalGive = totalGive.add(giveAmount);

                totalSave = totalSave.add(entity.getTradeAmount());
            }
            if(entity.getRecordype() == 2){
                BigDecimal saveAmount = saveArray.get(entity.getCreateDate());
                if(saveAmount == null){
                    saveArray.put(entity.getCreateDate(),BigDecimal.ZERO);
                    giveArray.put(entity.getCreateDate(),BigDecimal.ZERO);
                }

                saleArray.put(entity.getCreateDate(),entity.getTradeAmount());

                totalSale = totalSale.add(entity.getTradeAmount());
            }

            //获取最大值，用来做图像展示刻度
            if(maxAmount < entity.getTradeAmount().add(entity.getGiveAmount()).longValue()){
                maxAmount = entity.getTradeAmount().add(entity.getGiveAmount()).longValue();
            }

        }

        List<Integer> listCount = new LinkedList<>();
        for(String key: countArray.keySet()){
            listCount.add(countArray.get(key));

            //获取最大值，用来做图像展示刻度
            if(maxCount < countArray.get(key)){
                maxCount = countArray.get(key).longValue();
            }
        }

        List<String> listDate = new LinkedList<>();
        for(String key: dateArray.keySet()){
            listDate.add(dateArray.get(key));
        }

        List<BigDecimal> listSave = new LinkedList<>();
        List<BigDecimal> listGive = new LinkedList<>();
        List<BigDecimal> listSale = new LinkedList<>();

        for(String save : saveArray.keySet()){
            listSave.add(saveArray.get(save));
        }

        for(String give : giveArray.keySet()){
            listGive.add(giveArray.get(give));
        }

        for(String sale : saleArray.keySet()){
            listSale.add(saleArray.get(sale));
        }

        model.addAttribute("listDate", listDate);
        model.addAttribute("listCount", listCount);
        model.addAttribute("saveArray", listSave);
        model.addAttribute("giveArray", listGive);
        model.addAttribute("saleArray", listSale);


        model.addAttribute("totalTrade", totalTrade);
        model.addAttribute("totalSale", totalSale);
        model.addAttribute("totalSave", totalSave);
        model.addAttribute("totalGive", totalGive);

        maxCount = ToolsUtil.getMaxData(maxCount);
        maxAmount = ToolsUtil.getMaxData(maxAmount);

        model.addAttribute("maxCount", maxCount);
        model.addAttribute("intervalCount", maxCount / 10);
        model.addAttribute("maxAmount", maxAmount);
        model.addAttribute("intervalAmount", maxAmount / 10);

    }


    public Page<CommercialEntity> queryCommerical(TradeModel mTradeModel) throws Exception{
        ShopSearchModel mShopSearchModel = new ShopSearchModel ();
        mShopSearchModel.setBrandIdenty(mTradeModel.getBrandIdenty());
        mShopSearchModel.setCommercialName(mTradeModel.getShopName());
        mShopSearchModel.setShopIdenty(mTradeModel.getShopIdenty());
        mShopSearchModel.setInvalidStatus(1);

        Page<CommercialEntity> listData = mCommercialService.queryCommercialList(mShopSearchModel,mTradeModel.getPageNo(),mTradeModel.getPageSize());

        return listData;
    }

    public List<CustomerExtraModel> queryShopSaveData(List<CommercialEntity> listData,TradeModel mTradeModel,boolean isExcel)throws Exception{

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
        List<CustomerExtraModel> listExtra = mCustomerService.queryShopsSaveReport(mTradeModel.getBrandIdenty(),shopIds);
        return listExtra;
    }


    public Model queryShopMessage(Model model, TradeModel mTradeModel) throws Exception {

        BrandEntity brand = mBrandService.queryBrandByAppId(mTradeModel.getBrandIdenty());

        List<CommercialEntity> listCommercial = mCommercialService.queryCommercialByBrandId(brand.getId());

        model.addAttribute("brand", brand);
        model.addAttribute("listCommercial", listCommercial);
        return model;
    }

    @RequestMapping("/save/excel")
    public void exportExcel(HttpServletResponse response, TradeModel mTradeModel) throws Exception{

        List<CommercialEntity> listCommercial = mCommercialService.queryCommercialByBrandId(mTradeModel.getBrandIdenty());
        //获取各门店储值情况
        List<CustomerExtraModel> listExtra = queryShopSaveData(listCommercial,mTradeModel,true);


        ExcelData data = new ExcelData();
        data.setSheetName("储值报表");
        List<String> titles = new ArrayList();
        titles.add("序");
        titles.add("门店名称");
        titles.add("累计储值总额");
        titles.add("累计储值本金总额");
        titles.add("累计储值赠送总额");
        titles.add("累计储值消费总额");
        titles.add("累计储值剩余总额");

        data.setTitles(titles);

        List<List<Object>> rows = new ArrayList();
        data.setRows(rows);

        int i = 1;
        if(listExtra != null){
            for (CustomerExtraModel entity : listExtra) {
                List<Object> row = new ArrayList();
                rows.add(row);
                row.add(i++);
                row.add(entity.getShopName());
                row.add(entity.getStoredAmount());
                row.add(entity.getSaveBaseAmount());
                row.add(entity.getStoredGive());
                row.add(entity.getStoredUsed());
                row.add(entity.getStoredBalance());
            }
        }

        SimpleDateFormat fdate = new SimpleDateFormat("yyyyMMdd");
        String fileName = String.format("储值报表-%s.xls", fdate.format(new Date()));
        ExcelUtils.exportExcel(response, fileName, data);
    }
}
