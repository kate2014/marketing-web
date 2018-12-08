package com.zhongmei.yunfu.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

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
