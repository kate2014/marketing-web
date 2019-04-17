package com.zhongmei.yunfu.api.web;

import com.zhongmei.yunfu.controller.model.AuthUserModel;
import com.zhongmei.yunfu.controller.model.CustomerModel;
import com.zhongmei.yunfu.controller.model.excel.ExcelData;
import com.zhongmei.yunfu.controller.model.excel.ExcelUtils;
import com.zhongmei.yunfu.domain.entity.BrandEntity;
import com.zhongmei.yunfu.domain.entity.CommercialEntity;
import com.zhongmei.yunfu.domain.entity.CustomerReport;
import com.zhongmei.yunfu.domain.entity.UserSalaryReport;
import com.zhongmei.yunfu.service.AuthUserService;
import com.zhongmei.yunfu.service.BrandService;
import com.zhongmei.yunfu.service.CommercialService;
import com.zhongmei.yunfu.service.CustomerService;
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
@RequestMapping("/internal/report")
public class ReportUserController {

    @Autowired
    AuthUserService mAuthUserService;

    /**
     * 员工业绩报表
     *
     * @return
     */
    @RequestMapping("/userReport")
    public String reportAddCustomer(Model model, AuthUserModel mAuthUserModel) {
        try {

            Date start = null;
            Date end = null;
            //设置默认查询时间
            if (mAuthUserModel.getStartDate() == null) {
                Calendar c = Calendar.getInstance();
                //过去15天
                c.setTime(new Date());
                c.add(Calendar.DATE, -15);
                start = c.getTime();
                String temp = DateFormatUtil.format(start, DateFormatUtil.FORMAT_FULL_DATE);
                start = DateFormatUtil.parseDate(temp, DateFormatUtil.FORMAT_FULL_DATE);

                mAuthUserModel.setStartDate(temp);
            }
            if (mAuthUserModel.getEndDate() == null) {
                end = new Date();
                mAuthUserModel.setEndDate(DateFormatUtil.format(end, DateFormatUtil.FORMAT_FULL_DATE));
            }

            //员工销售排行
            List<UserSalaryReport> listData = mAuthUserService.querUserSaleryReport(mAuthUserModel);

            List<String> listUserName = new LinkedList<>();
            List<BigDecimal> listCount = new LinkedList<>();
            List<BigDecimal> listAmount = new LinkedList<>();

            Long maxCount = 0l;
            Long maxAmount = 0l;

            for(UserSalaryReport entity : listData){

                listUserName.add(entity.getUserName());
                listCount.add(entity.getCount());
                listAmount.add(entity.getAmount());

                if(maxCount < entity.getCount().longValue()){
                    maxCount = entity.getCount().longValue();
                }
                if(maxAmount < entity.getAmount().longValue()){
                    maxAmount = entity.getAmount().longValue();
                }
            }

            maxCount = ToolsUtil.getMaxData(maxCount);
            maxAmount = ToolsUtil.getMaxData(maxAmount);

            model.addAttribute("maxCount", maxCount);
            model.addAttribute("intervalCount", maxCount / 10);
            model.addAttribute("maxAmount", maxAmount);
            model.addAttribute("intervalAmount", maxAmount / 10);

            model.addAttribute("mAuthUserModel", mAuthUserModel);

            model.addAttribute("listUserName", listUserName);
            model.addAttribute("listCount", listCount);
            model.addAttribute("listAmount", listAmount);

            //员工业绩详情
            List<UserSalaryReport> listDetailData = mAuthUserService.querUserSaleryDetailReport(mAuthUserModel);
            model.addAttribute("listDetailData", listDetailData);

        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

        return "report_user_salary";
    }


    @RequestMapping("/userSalary/excel")
    public void exportExcel(HttpServletResponse response, AuthUserModel mAuthUserModel) throws Exception{

        List<UserSalaryReport> listDetailData = mAuthUserService.querUserSaleryDetailReport(mAuthUserModel);

        ExcelData data = new ExcelData();
        data.setSheetName("员工绩效报表");
        List<String> titles = new ArrayList();
        titles.add("序");
        titles.add("员工名称");
        titles.add("消费类型");
        titles.add("服务项目名称");
        titles.add("成交金额");
        titles.add("服务时间");

        data.setTitles(titles);

        List<List<Object>> rows = new ArrayList();
        data.setRows(rows);

        int i = 1;
        if(listDetailData != null){
            for (UserSalaryReport entity : listDetailData) {
                List<Object> row = new ArrayList();
                rows.add(row);
                row.add(i++);
                row.add(entity.getUserName());
                if(entity.getBusinessType() == 1){
                    row.add("服务消费");
                }else if(entity.getBusinessType() == 2){
                    row.add("余额充值");
                }else if(entity.getBusinessType() == 3){
                    row.add("服务次卡购买");
                }else if(entity.getBusinessType() == 3){
                    row.add("小程序服务购买");
                }
                row.add(entity.getDishName());
                row.add(entity.getAmount());
                row.add(entity.getTradeDate());
            }
        }

        SimpleDateFormat fdate = new SimpleDateFormat("yyyyMMdd");
        String fileName = String.format("员工绩效报表-%s.xls", fdate.format(new Date()));
        ExcelUtils.exportExcel(response, fileName, data);
    }

}
