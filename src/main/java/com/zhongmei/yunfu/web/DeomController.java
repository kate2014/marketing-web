package com.zhongmei.yunfu.web;

import com.zhongmei.yunfu.api.ApiRespStatus;
import com.zhongmei.yunfu.api.ApiRespStatusException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class DeomController {

    @GetMapping("/demo")
    public String demo(Model modelMap) {
        modelMap.addAttribute("name", "张三");

        modelMap.addAttribute("checkbox", "Black");
        modelMap.addAttribute("select", "2222");

        List<Bean> beanList = new ArrayList<>();
        beanList.add(new Bean("aaa", "bbb", "ccc"));
        beanList.add(new Bean("aaa", "bbb", "ccc"));
        beanList.add(new Bean("aaa", "bbb", "ccc"));
        modelMap.addAttribute("list", beanList);
        return "demo";
    }

    @GetMapping("/demo2")
    public String demo2(Model modelMap) throws Exception {
        throw new ApiRespStatusException(ApiRespStatus.FOUND);
    }

    @GetMapping("/demo3")
    @ResponseBody
    public String demo3(Model modelMap) throws Exception {
        throw new ApiRespStatusException(ApiRespStatus.FOUND);
    }

    static public class Bean {
        public String name;
        public String sex;
        public String age;

        public Bean(String name, String sex, String age) {
            this.name = name;
            this.sex = sex;
            this.age = age;
        }
    }
}
