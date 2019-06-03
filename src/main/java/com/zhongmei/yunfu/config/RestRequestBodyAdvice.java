package com.zhongmei.yunfu.config;

import com.alibaba.fastjson.JSON;
import com.zhongmei.yunfu.api.ApiRespStatusException;
import com.zhongmei.yunfu.api.ApiResult;
import com.zhongmei.yunfu.api.PosHeader;
import com.zhongmei.yunfu.api.PosReq;
import com.zhongmei.yunfu.controller.model.base.WebBaseModel;
import com.zhongmei.yunfu.service.LoginManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

@RestControllerAdvice(/*basePackages = "com.zhongmei.yunfu.api",*/ annotations = {RestController.class, RequestBody.class, ResponseBody.class})
public class RestRequestBodyAdvice implements RequestBodyAdvice/*, ResponseBodyAdvice<Object>*/ {

    private static Logger log = LoggerFactory.getLogger(RestRequestBodyAdvice.class);

    @Autowired
    HttpServletRequest request;

    @ExceptionHandler({Exception.class})
    @ResponseBody
    public ApiResult handException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) throws Exception {
        log.error("handException: ", e);

        int status = response.getStatus();
        String message = e.getMessage();
        if (e instanceof ApiRespStatusException) {
            ApiRespStatusException statusException = (ApiRespStatusException) e;
            status = statusException.getStatus().getValue();
            message = statusException.getStatus().getReason();
        }

        //return JSON.toJSONString(ApiResult.newResult(status, message));
        return ApiResult.newResult(status, message);
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        return inputMessage;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        log.info("request: header -> {}, body -> {}", inputMessage.getHeaders(), JSON.toJSONString(body));
        if (body instanceof WebBaseModel) {
            WebBaseModel baseModel = (WebBaseModel) body;
            baseModel.setUser(LoginManager.get().getUser());
        }
        if (body instanceof PosReq) {
            PosReq posReq = (PosReq) body;
            PosHeader posHeader = new PosHeader();
            posHeader.setMsgId(getHeader(inputMessage, "yf-api-msgid"));
            posHeader.setDeviceId(request.getHeader("yf-api-device-id"));
            posHeader.setBrandId(Long.parseLong(request.getHeader("yf-api-brand-id")));
            posHeader.setShopId(Long.parseLong(request.getHeader("yf-api-shop-id")));
            posReq.setHeader(posHeader);
        }
        return body;
    }

    private String getHeader(HttpInputMessage inputMessage, String key) {
        List<String> strings = inputMessage.getHeaders().get(key);
        return strings != null ? strings.get(0) : null;
    }

    /*@Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        log.info("response:  header -> {}, body -> {}", "", JSON.toJSONString(body));
        return body;
    }*/
}
