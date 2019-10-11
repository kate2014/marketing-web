package com.zhongmei.yunfu.controller.brand;


import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.BaseController;
import com.zhongmei.yunfu.controller.model.ShopSearchModel;
import com.zhongmei.yunfu.controller.model.TradeModel;
import com.zhongmei.yunfu.controller.model.excel.ExcelData;
import com.zhongmei.yunfu.controller.model.excel.ExcelUtils;
import com.zhongmei.yunfu.domain.entity.BrandEntity;
import com.zhongmei.yunfu.domain.entity.CommercialEntity;
import com.zhongmei.yunfu.domain.entity.DishReport;
import com.zhongmei.yunfu.service.BrandService;
import com.zhongmei.yunfu.service.CommercialService;
import com.zhongmei.yunfu.service.TradeItemService;
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

@Controller
@RequestMapping("/internal/brand/cardTimeReport")
public class BrandReportCardTimeController extends BaseController {

    @Autowired
    TradeService mTradeService;
    @Autowired
    TradeItemService mTradeItemService;
    @Autowired
    BrandService mBrandService;
    @Autowired
    CommercialService mCommercialService;

    @RequestMapping("/cardTime")
    public String cardTimeReport(Model model, TradeModel mTradeModel) {

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

            List<DishReport> listData = mTradeItemService.selectCardTimeReport(mTradeModel.getBrandIdenty(), mTradeModel.getShopIdenty(), start, end);

            List<String> listDishName = new LinkedList<>();
            List<BigDecimal> listSalesAmount = new LinkedList<>();
            List<Long> listSalesCount = new LinkedList<>();

            Long maxCount = 0l;
            Long maxAmount = 0l;

            BigDecimal totalCount = BigDecimal.ZERO;
            BigDecimal totalAmount = BigDecimal.ZERO;

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

                totalCount = totalCount.add(new BigDecimal(dp.getSalseCount()));
                totalAmount = totalAmount.add(dp.getSalesAmount());
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


            Page<CommercialEntity> listShopPage = queryCommerical(mTradeModel);
            List<DishReport> listCardData = queryCardGroupShop(mTradeModel,listShopPage.getRecords(),start,end,false);

            setWebPage(model, "/internal/brand/cardTimeReport/cardTime", listShopPage, mTradeModel);
            model.addAttribute("listCardData", listCardData);

        }catch (Exception e){
            e.printStackTrace();

        }


        return "brand_report_card_time";
    }

    /**
     * 获取各门店次卡销售情况--获取分页门店数据
     * @param mTradeModel
     * @return
     * @throws Exception
     */
    public Page<CommercialEntity> queryCommerical(TradeModel mTradeModel) throws Exception{
        ShopSearchModel mShopSearchModel = new ShopSearchModel ();
        mShopSearchModel.setBrandIdenty(mTradeModel.getBrandIdenty());
        mShopSearchModel.setShopIdenty(mTradeModel.getShopIdenty());
        mShopSearchModel.setInvalidStatus(1);

        Page<CommercialEntity> listData = mCommercialService.queryCommercialList(mShopSearchModel,mTradeModel.getPageNo(),mTradeModel.getPageSize());

        return listData;
    }

    /**
     * 获取各门店次卡销售情况
     * @param mTradeModel
     * @param listShop
     * @return
     */
    public List<DishReport> queryCardGroupShop(TradeModel mTradeModel,List<CommercialEntity> listShop,Date startDate,Date endDate,boolean isExcel)throws Exception{

        String shopIds = null;
        if(!isExcel){
            for(CommercialEntity entity : listShop){
                if(shopIds == null){
                    shopIds = entity.getCommercialId().toString();
                }else{
                    shopIds = shopIds+","+entity.getCommercialId();
                }
            }
        }

        List<DishReport> listData = mTradeItemService.selectCardGroupShop(mTradeModel.getBrandIdenty(),shopIds, startDate, endDate);

        return listData;
    }

    public Model queryShopMessage(Model model, TradeModel mTradeModel) throws Exception {

        List<CommercialEntity> listCommercial = mCommercialService.queryCommercialByBrandId(mTradeModel.getBrandIdenty());

        model.addAttribute("listCommercial", listCommercial);
        return model;
    }

    @RequestMapping("/export/excel")
    public void exportExcel(HttpServletResponse response, TradeModel mTradeModel) throws Exception{

        Date startDate = DateFormatUtil.parseDate(mTradeModel.getStartDate(), DateFormatUtil.FORMAT_FULL_DATE);
        Date endDate = DateFormatUtil.parseDate(mTradeModel.getEndDate(), DateFormatUtil.FORMAT_FULL_DATE);

        List<DishReport> listData = queryCardGroupShop(mTradeModel,null,startDate,endDate,true);

        ExcelData data = new ExcelData();
        data.setSheetName("次卡服务销售数据");
        List<String> titles = new ArrayList();
        titles.add("序");
        titles.add("门店名称");
        titles.add("销售数量");
        titles.add("销售金额");

        data.setTitles(titles);

        List<List<Object>> rows = new ArrayList();
        data.setRows(rows);

        int i = 1;
        if(listData != null){
            for (DishReport entity : listData) {
                List<Object> row = new ArrayList();
                rows.add(row);
                row.add(i++);
                row.add(entity.getShopName());
                row.add(entity.getSalseCount());
                row.add(entity.getSalesAmount());
            }
        }

        SimpleDateFormat fdate = new SimpleDateFormat("yyyyMMdd");
        String fileName = String.format("次卡服务销售报表-%s.xls", fdate.format(new Date()));
        ExcelUtils.exportExcel(response, fileName, data);
    }

}
