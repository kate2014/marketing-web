package com.zhongmei.yunfu.api.web;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.BaseController;
import com.zhongmei.yunfu.controller.model.ReportMarketingModel;
import com.zhongmei.yunfu.controller.model.excel.ExcelData;
import com.zhongmei.yunfu.controller.model.excel.ExcelUtils;
import com.zhongmei.yunfu.domain.entity.WxTradeCustomerEntity;
import com.zhongmei.yunfu.service.*;
import com.zhongmei.yunfu.util.DateFormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/internal/activitySalesReport")
public class ReportActivitySalesController extends BaseController {

    @Autowired
    ActivitySalesService mActivitySalesService;
    @Autowired
    WxTradeCustomerService mWxTradeCustomerService;

    @RequestMapping("/activitySales")
    public String activitySalesReport(Model model, ReportMarketingModel mReportMarketingModel) {

        try{



            if(mReportMarketingModel.getValidityPeriod() == null || mReportMarketingModel.getValidityPeriod().equals("")){
                mReportMarketingModel.setValidityPeriod(DateFormatUtil.format(new Date(),DateFormatUtil.FORMAT_FULL_DATE));
            }
            mReportMarketingModel.setType(4);

            //获取活动详情列表
            Page<WxTradeCustomerEntity> listData = mWxTradeCustomerService.queryAllMarketing(mReportMarketingModel);

            setWebPage(model, "/internal/activitySalesReport/activitySales", listData, mReportMarketingModel);
            model.addAttribute("listData", listData.getRecords());
            model.addAttribute("mReportMarketingModel", mReportMarketingModel);

            //获取活动售卖情况，用于柱状图
            List<WxTradeCustomerEntity> listAllReport = mWxTradeCustomerService.queryReport(mReportMarketingModel,null);

            List<String> allName = new LinkedList<>();
            List<Long> allData = new LinkedList<>();
            List<Long> usedData = new LinkedList<>();


            Map<Long,Long> tempMap = new LinkedHashMap<>();

            for(WxTradeCustomerEntity entity : listAllReport){

                allData.add(entity.getId());
                allName.add(entity.getMarketingName());
                tempMap.put(entity.getMarketingId(),0l);
            }

            //获取活动使用情况，用于柱状图
            List<WxTradeCustomerEntity> listUsedReport = mWxTradeCustomerService.queryReport(mReportMarketingModel,2);

            for(WxTradeCustomerEntity entity : listUsedReport){
                tempMap.put(entity.getMarketingId(),entity.getId());
            }

            for(Long key:tempMap.keySet()){
                usedData.add(tempMap.get(key));
            }

            model.addAttribute("allName", allName);
            model.addAttribute("allData", allData);
            model.addAttribute("usedData", usedData);


        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }


        return "report_activity_sales";
    }

    @RequestMapping("/export/activitySale")
    public void exportActivitySale(HttpServletResponse response, ReportMarketingModel mReportMarketingModel) throws Exception{

        if(mReportMarketingModel.getValidityPeriod() == null || mReportMarketingModel.getValidityPeriod().equals("")){
            mReportMarketingModel.setValidityPeriod(DateFormatUtil.format(new Date(),DateFormatUtil.FORMAT_FULL_DATE));
        }
        mReportMarketingModel.setType(4);

        List<WxTradeCustomerEntity> listDishSale = mWxTradeCustomerService.queryAllData(mReportMarketingModel);

        ExcelData data = new ExcelData();
        data.setSheetName("特价活动销售详情");
        List<String> titles = new ArrayList();
        titles.add("序");
        titles.add("活动名称");
        titles.add("购买顾客");
        titles.add("购买时间");
        titles.add("使用状态");
        titles.add("使用时间");
        titles.add("到期时间");

        data.setTitles(titles);

        List<List<Object>> rows = new ArrayList();
        data.setRows(rows);

        int i = 1;
        if(listDishSale != null){
            for (WxTradeCustomerEntity entity : listDishSale) {
                List<Object> row = new ArrayList();
                rows.add(row);
                row.add(i++);
                row.add(entity.getMarketingName());
                row.add(entity.getCustomerName());
                row.add(DateFormatUtil.format(entity.getServerCreateTime(),DateFormatUtil.FORMAT_FULL_DATE));


                if(entity.getStatus() == 1){
                    row.add("未使用");
                    row.add("/");
                }else{
                    row.add("已使用");
                    row.add(DateFormatUtil.format(entity.getServerUpdateTime(),DateFormatUtil.FORMAT_FULL_DATE));
                }

                row.add(DateFormatUtil.format(entity.getValidityPeriod(),DateFormatUtil.FORMAT_FULL_DATE));
            }
        }

        SimpleDateFormat fdate = new SimpleDateFormat("yyyyMMdd");
        String fileName = String.format("特价活动销售报表-%s.xls", fdate.format(new Date()));
        ExcelUtils.exportExcel(response, fileName, data);
    }

}
