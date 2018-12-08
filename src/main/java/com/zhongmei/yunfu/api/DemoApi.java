package com.zhongmei.yunfu.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhongmei.yunfu.controller.weixinPay.UnifiedorderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@RestController
@RequestMapping("/demo")
public class DemoApi {

    //@Autowired
    //RestService restService;

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/json")
    public HashMap login(ModelMap model) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("name", "111");
        //hashMap.put("query", null);
        hashMap.put("submit", "222");
        return hashMap;
    }

    @RequestMapping("/json/text")
    public String jsonText(ModelMap model) throws JsonProcessingException {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("name", "111");
        //hashMap.put("query", null);
        hashMap.put("submit", "222");
        return new ObjectMapper().writeValueAsString(hashMap);
    }

    @RequestMapping("/json/http")
    public String jsonHttp() {
        //String byId = restService.findById();
        //return byId;
        return restTemplate.getForObject("http://www.baidu.com", String.class);
    }

    /*@RequestMapping("/json/feign")
    public String jsonFeign() {
        return restService.findById();
    }*/

    @RequestMapping(value = "/xml")
    public UnifiedorderEntity XML(@RequestBody UnifiedorderEntity entity){
        UnifiedorderEntity user = new UnifiedorderEntity();
        user.setAppid("12121");
        user.setMch_id("6565");
        return user;
    }

}
