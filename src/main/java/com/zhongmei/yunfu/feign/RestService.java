package com.zhongmei.yunfu.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("client1")
public interface RestService {

    @RequestMapping("/user/findById")
    String findById();
}
