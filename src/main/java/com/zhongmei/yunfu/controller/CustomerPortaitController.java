package com.zhongmei.yunfu.controller;


import com.zhongmei.yunfu.controller.model.CommissionSearchModel;
import com.zhongmei.yunfu.controller.model.CustomerModel;
import com.zhongmei.yunfu.controller.model.CustomerSaleModel;
import com.zhongmei.yunfu.controller.model.TradeModel;
import com.zhongmei.yunfu.domain.entity.CustomerEntity;
import com.zhongmei.yunfu.service.CustomerService;
import com.zhongmei.yunfu.service.LoginManager;
import com.zhongmei.yunfu.service.TradeService;
import com.zhongmei.yunfu.util.DateFormatUtil;
import com.zhongmei.yunfu.util.ToolsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 顾客画像
 */
@Controller
@RequestMapping("/customer")
public class CustomerPortaitController extends BaseController {

    @Autowired
    CustomerService mCustomerService;

    @Autowired
    TradeService mTradeService;

    @RequestMapping("/agePortait")
    public String gotoExpandedMainPage(Model model, CustomerModel mCustomerModel){

        try {
            Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
            Long shopIdentity = LoginManager.get().getUser().getShopIdenty();

            List<CustomerEntity> listCustomer = mCustomerService.queryAllCustomer(brandIdentity,shopIdentity);
            customerDistribution(model,listCustomer);


        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

        return "customer_portrait";
    }

    private void customerDistribution(Model model,List<CustomerEntity> listCustomer){
        int age20 = 0;
        int age25 = 0;
        int age30 = 0;
        int age35 = 0;
        int age40 = 0;
        int age45 = 0;
        int age45m = 0;
        int unknowAge = 0;

        int man = 0;
        int woman = 0;
        int unknowGender = 0;

        Date nowDate = new Date();
        for(CustomerEntity entity : listCustomer){
            //统计性别
            if(entity.getGender() != null){
                if(entity.getGender() == 1){
                    man++;
                }else if(entity.getGender() == 2){
                    woman ++;
                }else {
                    unknowGender++;
                }
            }else{
                unknowGender++;
            }

            //统计年龄段
            Date birthday = entity.getBirthday();
            if(birthday != null){
                double age = ToolsUtil.divide2(ToolsUtil.differentDays(birthday,nowDate),365);
                if(age <= 20){
                    age20 ++;
                }else if(age >20 && age<=25){
                    age25 ++;
                }else if(age >25 && age<=30){
                    age30 ++;
                }else if(age >30 && age<=35){
                    age35 ++;
                }else if(age >35 && age<=40){
                    age40 ++;
                }else if(age >40 && age<=45){
                    age45 ++;
                }else if(age >45){
                    age45m ++;
                }else {
                    unknowAge ++;
                }

            }else{
                unknowAge ++;
            }


        }
        int customerCount = listCustomer.size();
        model.addAttribute("customerCount", customerCount);
        model.addAttribute("age20", ToolsUtil.divide2(age20,customerCount)*100);
        model.addAttribute("age25", ToolsUtil.divide2(age25,customerCount)*100);
        model.addAttribute("age30", ToolsUtil.divide2(age30,customerCount)*100);
        model.addAttribute("age35", ToolsUtil.divide2(age35,customerCount)*100);
        model.addAttribute("age40", ToolsUtil.divide2(age40,customerCount)*100);
        model.addAttribute("age45", ToolsUtil.divide2(age45,customerCount)*100);
        model.addAttribute("age45m", ToolsUtil.divide2(age45m,customerCount)*100);
        model.addAttribute("unknowAge", ToolsUtil.divide2(unknowAge,customerCount)*100);

        model.addAttribute("age20Count", age20);
        model.addAttribute("age25Count", age25);
        model.addAttribute("age30Count", age30);
        model.addAttribute("age35Count", age35);
        model.addAttribute("age40Count", age40);
        model.addAttribute("age45Count", age45);
        model.addAttribute("age45mCount", age45m);
        model.addAttribute("unknowAgeCount", unknowAge);

        model.addAttribute("man", man);
        model.addAttribute("woman", woman);
        model.addAttribute("unknowGender", unknowGender);


    }

    @RequestMapping("/salePortait")
    public String salePortrait(Model model, CustomerModel mCustomerModel){

        Integer searchData = mCustomerModel.getSearchData();
        if(mCustomerModel.getSearchData() == null){
            searchData = -7;
        }

        try {
            Long brandIdentity = LoginManager.get().getUser().getBrandIdenty();
            Long shopIdentity = LoginManager.get().getUser().getShopIdenty();

            TradeModel mTradeModel = new TradeModel();

            if (mCustomerModel.getStartDate() == null) {
                Calendar c = Calendar.getInstance();
                //过去15天
                c.setTime(new Date());
                c.add(Calendar.DATE, searchData);
                Date start = c.getTime();
                String temp = DateFormatUtil.format(start, DateFormatUtil.FORMAT_FULL_DATE);
                mTradeModel.setStartDate(temp);

            }

            if (mCustomerModel.getEndDate() == null) {
                mTradeModel.setEndDate(DateFormatUtil.format(new Date(), DateFormatUtil.FORMAT_FULL_DATE));
            }

            mTradeModel.setShopIdenty(shopIdentity);
            mTradeModel.setBrandIdenty(brandIdentity);
            List<CustomerSaleModel> listData = mTradeService.queryCustomerSale(mTradeModel);

            BigDecimal age20sale = BigDecimal.ZERO;
            BigDecimal age25sale = BigDecimal.ZERO;
            BigDecimal age30sale = BigDecimal.ZERO;
            BigDecimal age35sale = BigDecimal.ZERO;
            BigDecimal age40sale = BigDecimal.ZERO;
            BigDecimal age45sale = BigDecimal.ZERO;
            BigDecimal age45msale = BigDecimal.ZERO;
            BigDecimal unknowAgesale = BigDecimal.ZERO;

            BigDecimal mansale = BigDecimal.ZERO;
            BigDecimal womansale = BigDecimal.ZERO;
            BigDecimal unknowGendersale = BigDecimal.ZERO;

            Date nowDate = new Date();
            for(CustomerSaleModel entity : listData){
                //统计性别
                if(entity.getGender() != null){
                    if(entity.getGender() == 1){
                        mansale = mansale.add(entity.getSaleAmount());
                    }else if(entity.getGender() == 2){
                        womansale = womansale.add(entity.getSaleAmount());
                    }else {
                        unknowGendersale = unknowGendersale.add(entity.getSaleAmount());
                    }
                }else{
                    unknowGendersale = unknowGendersale.add(entity.getSaleAmount());
                }

                //统计年龄段
                Date birthday = entity.getBirthday();
                if(birthday != null){
                    double age = ToolsUtil.divide2(ToolsUtil.differentDays(birthday,nowDate),365);
                    if(age <= 20){
                        age20sale = age20sale.add(entity.getSaleAmount());
                    }else if(age >20 && age<=25){
                        age25sale = age25sale.add(entity.getSaleAmount());
                    }else if(age >25 && age<=30){
                        age30sale = age30sale.add(entity.getSaleAmount());
                    }else if(age >30 && age<=35){
                        age35sale = age35sale.add(entity.getSaleAmount());
                    }else if(age >35 && age<=40){
                        age40sale = age40sale.add(entity.getSaleAmount());
                    }else if(age >40 && age<=45){
                        age45sale = age45sale.add(entity.getSaleAmount());
                    }else if(age >45){
                        age45msale = age45msale.add(entity.getSaleAmount());
                    }else {
                        unknowAgesale = unknowAgesale.add(entity.getSaleAmount());
                    }

                }else{
                    unknowAgesale = unknowAgesale.add(entity.getSaleAmount());
                }


            }

            model.addAttribute("searchData", searchData);

            model.addAttribute("age20sale", age20sale);
            model.addAttribute("age25sale", age25sale);
            model.addAttribute("age30sale", age30sale);
            model.addAttribute("age35sale", age35sale);
            model.addAttribute("age40sale", age40sale);
            model.addAttribute("age45sale", age45sale);
            model.addAttribute("age45msale", age45msale);
            model.addAttribute("unknowAgesale", unknowAgesale);

            model.addAttribute("mansale", mansale);
            model.addAttribute("womansale", womansale);
            model.addAttribute("unknowGendersale", unknowGendersale);

            List<BigDecimal> listSalesCount = new LinkedList<>();
            listSalesCount.add(age20sale);
            listSalesCount.add(age25sale);
            listSalesCount.add(age30sale);
            listSalesCount.add(age35sale);
            listSalesCount.add(age40sale);
            listSalesCount.add(age45sale);
            listSalesCount.add(age45msale);
            listSalesCount.add(unknowAgesale);
            model.addAttribute("listSalesCount", listSalesCount);


        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

        return "customer_sale_portrait";
    }

}
