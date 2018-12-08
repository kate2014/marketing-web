package com.zhongmei.yunfu.thymeleaf;

import com.zhongmei.yunfu.WebAppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.support.RequestDataValueProcessor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class ThymeleafRequestDataValueProcessor implements RequestDataValueProcessor {

    @Autowired
    HttpServletRequest request;

    @Override
    public String processAction(HttpServletRequest request, String action, String httpMethod) {
        return WebAppConfig.getBasePath(request) + action;
    }

    @Override
    public String processFormFieldValue(HttpServletRequest request, String name, String value, String type) {
        return value;
    }

    @Override
    public Map<String, String> getExtraHiddenFields(HttpServletRequest request) {
        return null;
    }

    @Override
    public String processUrl(HttpServletRequest request, String url) {
        return WebAppConfig.getBasePath(request) + url;
    }

}
