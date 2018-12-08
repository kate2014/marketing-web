package com.zhongmei.yunfu.controller;

import com.alibaba.fastjson.JSON;
import com.zhongmei.yunfu.controller.model.CouponSearchModel;
import com.zhongmei.yunfu.controller.model.SalaryDetailModel;
import com.zhongmei.yunfu.controller.model.SalaryListDetailModel;
import com.zhongmei.yunfu.controller.model.SalaryModel;
import com.zhongmei.yunfu.domain.entity.bean.DishTypeResponse;
import com.zhongmei.yunfu.domain.entity.bean.SalaryCommions;
import com.zhongmei.yunfu.domain.entity.bean.SalaryCommiosResponse;
import com.zhongmei.yunfu.domain.entity.bean.SalaryResponse;
import com.zhongmei.yunfu.service.LoginManager;
import com.zhongmei.yunfu.util.DateFormatUtil;
import com.zhongmei.yunfu.util.ServerAddress;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping("/internal/salary")
public class SalaryController {

    @RequestMapping("/userSalary")
    public String querySalaryList(Model model, SalaryModel salaryModel) {

        try{
            Date start = null;
            Date end = null;
            //设置默认查询时间
            if (salaryModel.getStartDate() == null) {
                Calendar c = Calendar.getInstance();
                //过去15天
                c.setTime(new Date());
                c.add(Calendar.DATE, -7);
                start = c.getTime();
                String temp = DateFormatUtil.format(start, DateFormatUtil.FORMAT_FULL_DATE);
                start = DateFormatUtil.parseDate(temp, DateFormatUtil.FORMAT_FULL_DATE);

            } else {
                start = DateFormatUtil.parseDate(salaryModel.getStartDate(), DateFormatUtil.FORMAT_FULL_DATE);
            }
            if (salaryModel.getEndDate() == null) {
                end = new Date();
            } else {
                end = DateFormatUtil.parseDate(salaryModel.getEndDate(), DateFormatUtil.FORMAT_FULL_DATE);
            }

            salaryModel.setStartDate(DateFormatUtil.format(start, DateFormatUtil.FORMAT_FULL_DATE));

            salaryModel.setEndDate(DateFormatUtil.format(end, DateFormatUtil.FORMAT_FULL_DATE));


            String url = "shopId=%s&brandId=%s&userId=%s&startDate=%s&endDate=%s";
            url = String.format(url, salaryModel.getShopIdenty()+"",salaryModel.getBrandIdenty()+"",salaryModel.getCreatorId()+"",start.getTime(),end.getTime());

            url = ServerAddress.QUERYSALARY+"?"+ url;
            Map<String, String> map = new HashMap<>();
            RestTemplate template = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            String params = JSON.toJSONString(map);
            HttpEntity<String> formEntity = new HttpEntity<String>(params, headers);
            String jsonResult = template.exchange(url, HttpMethod.GET, formEntity, String.class).getBody().toString();
            SalaryResponse response = JSON.parseObject(jsonResult, SalaryResponse.class);

            model.addAttribute("salaryModel", salaryModel);
            model.addAttribute("list", response.getData());
            return "salary_list";
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

    }

    /**
     * 查看人效提成详情
     * @param model
     * @param salaryModel
     * @return
     */
    @RequestMapping("/querySalaryDetail")
    public String querySalaryDetail(Model model, SalaryModel salaryModel){

        try {
            String url = "shopId=%s&brandId=%s&userId=%s&startDate=%s&endDate=%s";


            Long startTime = DateFormatUtil.parseDate(salaryModel.getStartDate(),DateFormatUtil.FORMAT_FULL_DATE).getTime();
            Long endTime = DateFormatUtil.parseDate(salaryModel.getEndDate(),DateFormatUtil.FORMAT_FULL_DATE).getTime();

            url = String.format(url, salaryModel.getShopIdenty()+"",salaryModel.getBrandIdenty()+"",salaryModel.getUserId()+"",startTime+"",endTime+"");

            url = ServerAddress.SALARY_DETAIL+"?"+ url;

            Map<String, String> map = new HashMap<>();
            RestTemplate template = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            String params = JSON.toJSONString(map);
            HttpEntity<String> formEntity = new HttpEntity<String>(params, headers);
            String jsonResult = template.exchange(url, HttpMethod.GET, formEntity, String.class).getBody().toString();

            SalaryCommiosResponse mSalaryCommiosResponse = JSON.parseObject(jsonResult, SalaryCommiosResponse.class);

            SalaryCommions mSalaryCommions = mSalaryCommiosResponse.getData();

            String salesDetail = mSalaryCommions.getSalesCommissionsDetail();
            String saveDetail = mSalaryCommions.getSaveCommissionsDetail();

            //消费提成明细解析
            List<SalaryListDetailModel> listSalesSalary = new ArrayList<>();

            String[] salesArray = salesDetail.split("/and/");
            for (int i = 0; i < salesArray.length; i++) {
                String commions = salesArray[i];
                String[] temp = commions.split("/plan/;");
                if(temp == null || temp.length < 2){
                    break;
                }

                SalaryListDetailModel mSalaryListDetailModel = new SalaryListDetailModel();
                mSalaryListDetailModel.setCommissionsName("提成方案："+temp[0]);
                List<SalaryDetailModel> listSalaryDetailModel = new ArrayList<>();
                String detail = temp[1];
                String[] array = detail.split(";");
                for(int j = 0; j < array.length; j++){
                    String values= array[j];
                    //判断是比例还是固定值
                    if(values.indexOf("*") == -1){ //表示不包含* 为固定值
                        String[] amounts = values.split(":");
                        SalaryDetailModel mSalaryDetailModel = new SalaryDetailModel();
                        mSalaryDetailModel.setType(2);
                        mSalaryDetailModel.setBaseAmount(amounts[0]);
                        mSalaryDetailModel.setCommissionsValue(amounts[1]);
                        mSalaryDetailModel.setCommissionsAmount(amounts[1]);
                        listSalaryDetailModel.add(mSalaryDetailModel);
                    }else{
                        String[] amounts = values.split("\\*");
                        SalaryDetailModel mSalaryDetailModel = new SalaryDetailModel();
                        mSalaryDetailModel.setType(1);
                        mSalaryDetailModel.setBaseAmount(amounts[0]);
                        String[] value = amounts[1].split("=");
                        mSalaryDetailModel.setCommissionsValue(value[0]);
                        mSalaryDetailModel.setCommissionsAmount(value[1]);
                        listSalaryDetailModel.add(mSalaryDetailModel);
                    }

                }
                mSalaryListDetailModel.setListSalaryDetailModel(listSalaryDetailModel);
                listSalesSalary.add(mSalaryListDetailModel);
            }


            //储值提成明细解析
            List<SalaryListDetailModel> listSaveSalary = new ArrayList<>();

            System.out.println("=========saveDetail:"+saveDetail);
            String[] saveArray = saveDetail.split("/and/");

            for (int i = 0; i < saveArray.length; i++) {
                String commions = saveArray[i];
                System.out.println("=========commions:"+commions);
                String[] temp = commions.split("/plan/;");
                if(temp == null || temp.length < 2){
                    break;
                }
                SalaryListDetailModel mSalaryListDetailModel = new SalaryListDetailModel();
                mSalaryListDetailModel.setCommissionsName("提成方案："+temp[0]);
                List<SalaryDetailModel> listSalaryDetailModel = new ArrayList<>();
                String detail = temp[1];
                String[] array = detail.split(";");
                for(int j = 0; j < array.length; j++){
                    String values= array[j];
                    //判断是比例还是固定值
                    if(values.indexOf("*") == -1){ //表示不包含* 为固定值
                        String[] amounts = values.split(":");
                        SalaryDetailModel mSalaryDetailModel = new SalaryDetailModel();
                        mSalaryDetailModel.setType(2);
                        mSalaryDetailModel.setBaseAmount(amounts[0]);
                        mSalaryDetailModel.setCommissionsValue(amounts[1]);
                        mSalaryDetailModel.setCommissionsAmount(amounts[1]);
                        listSalaryDetailModel.add(mSalaryDetailModel);
                    }else{
                        String[] amounts = values.split("\\*");
                        SalaryDetailModel mSalaryDetailModel = new SalaryDetailModel();
                        mSalaryDetailModel.setType(1);
                        mSalaryDetailModel.setBaseAmount(amounts[0]);
                        String[] value = amounts[1].split("=");
                        mSalaryDetailModel.setCommissionsValue(value[0]);
                        mSalaryDetailModel.setCommissionsAmount(value[1]);
                        listSalaryDetailModel.add(mSalaryDetailModel);
                    }

                }
                mSalaryListDetailModel.setListSalaryDetailModel(listSalaryDetailModel);
                listSaveSalary.add(mSalaryListDetailModel);
            }


            model.addAttribute("listSalesSalary", listSalesSalary);

            model.addAttribute("listSaveSalary", listSaveSalary);

            model.addAttribute("salaryCommions", mSalaryCommions);

            BigDecimal salarySum = new BigDecimal(mSalaryCommions.getSalarySum());

            model.addAttribute("totalSalary", salarySum);
        }catch (Exception e){
            e.printStackTrace();

        }
        return "salary_detail";
    }
}
