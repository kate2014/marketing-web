package com.zhongmei.yunfu.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhongmei.yunfu.controller.model.DishSearchModel;
import com.zhongmei.yunfu.domain.entity.*;
import com.zhongmei.yunfu.domain.entity.bean.DishShopResponse;
import com.zhongmei.yunfu.domain.entity.bean.DishType;
import com.zhongmei.yunfu.domain.entity.bean.DishTypeResponse;
import com.zhongmei.yunfu.service.LoginManager;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/dish")
public class SelectDishController extends BaseController {


    @RequestMapping("/selectShopPage")
    public String selectShopPage(Model model) {
        try {

            if(LoginManager.get().getUser()==null){
                return "select_shop";
            }

            String url = "http://47.105.100.99:8090/MeiYe/internal/api/dish/loadBrandTypes?brandId=%s&shopId=%s&userId=%s";
            url = String.format(url, LoginManager.get().getUser().getBrandIdenty()+"",LoginManager.get().getUser().getShopIdenty()+"",LoginManager.get().getUser().getId()+"");

            Map<String, String> map = new HashMap<>();
            RestTemplate template = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);


            String params = JSON.toJSONString(map);
            HttpEntity<String> formEntity = new HttpEntity<String>(params, headers);
            String jsonResult = template.exchange(url, HttpMethod.GET, formEntity, String.class).getBody().toString();

            if ("".equals(jsonResult)) {
                System.out.println("没有获取到数据！");
            }
            DishTypeResponse response = JSON.parseObject(jsonResult, DishTypeResponse.class);

            List<DishType> dishTypes=new ArrayList<>();
            if(response!=null){
                for (DishType dishType : response.getData()) {
                    if(dishType.getDishBrandTypeBoList()!=null && dishType.getDishBrandTypeBoList().size()>0){
                        dishTypes.addAll(dishType.getDishBrandTypeBoList());
                    }
                }
            }

            model.addAttribute("list", dishTypes);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return "select_shop";
    }

    @RequestMapping("/queryDish")
    public String queryDish(Model model, DishSearchModel searchModel) {
        try {

            Page<DishShopEntity> dishShopPage = new Page<>(searchModel.getPageNo(), searchModel.getPageSize());

            if(LoginManager.get().getUser()==null){
                setWebPage(model, "/pushPlanNewDish/list", null, searchModel);
                return "dish_list";
            }

            //172.31.144.129  内网地址
            //47.105.100.99 外网地址
            String url = "http://47.105.100.99:8090/MeiYe/internal/api/dish/getDishShopPageByCriteria?brandId=%s&shopId=%s&userId=%s";
            url = String.format(url, LoginManager.get().getUser().getBrandIdenty()+"",LoginManager.get().getUser().getShopIdenty()+"",LoginManager.get().getUser().getId()+"");

            RestTemplate template = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);


            Map<String, String> map = new HashMap<>();
            map.put("pageNum", searchModel.getPageNo() + "");
            map.put("pageSize", searchModel.getPageSize() + "");
            map.put("name", searchModel.getKeyWord());
            map.put("dishTypeId", searchModel.getDishTypeId() + "");
            map.put("type", "0");


            String params = JSON.toJSONString(map);

            HttpEntity<String> formEntity = new HttpEntity<String>(params, headers);
            String jsonResult = template.postForObject(url, formEntity, String.class).toString();
            System.out.println(jsonResult);


            if ("".equals(jsonResult)) {
                System.out.println("没有获取到数据！");
            }

            DishShopResponse response = JSON.parseObject(jsonResult, DishShopResponse.class);

            if (response.getStatusCode() == 200) {
                dishShopPage.setRecords(response.getData().getContent());

                model.addAttribute("list", response.getData().getContent());
                setWebPage(model, "/pushPlanNewDish/list", dishShopPage, searchModel);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return "dish_list";
    }
}
