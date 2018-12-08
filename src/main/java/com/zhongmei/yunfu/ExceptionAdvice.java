/*
package com.zhongmei.yunfu;

import com.alibaba.fastjson.JSON;
import com.zhongmei.yunfu.api.ApiResponseStatusException;
import com.zhongmei.yunfu.api.ApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ExceptionAdvice {

    private static Logger log = LoggerFactory.getLogger(ExceptionAdvice.class);

    @ExceptionHandler({Exception.class})
    public String handException(HttpServletRequest request, Exception e) throws Exception {
        log.error("handException: {}", e.getMessage());

        int status = 200;
        String message = e.getMessage();
        if (e instanceof ApiResponseStatusException) {
            ApiResponseStatusException statusException = (ApiResponseStatusException) e;
            status = statusException.getStatus().getValue();
            message = statusException.getStatus().getReason();
        }

        return JSON.toJSONString(ApiResult.newResult(status, message));
    }
}
*/
