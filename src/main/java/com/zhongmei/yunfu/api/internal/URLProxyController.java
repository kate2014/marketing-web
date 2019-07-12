package com.zhongmei.yunfu.api.internal;

import com.zhongmei.yunfu.controller.model.base.InternalBaseModel;
import com.zhongmei.yunfu.core.security.Token;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 页面代理
 */
@Controller
@RequestMapping("/internal")
public class URLProxyController {

    @RequestMapping({"/", "/home", "/customer", "/commercial/settingPage","/dishShop"})
    public String proxy(HttpServletRequest request, InternalBaseModel baseModel) {
        String token = Token.encode("", "", baseModel.getCreatorId(), baseModel.getCreatorName(), baseModel.getShopIdenty(), baseModel.getBrandIdenty());
        String href = request.getRequestURI().replaceFirst(request.getContextPath(), "").replace("/internal/", "/") + "/token/" + token;
        if (request.getQueryString() != null) {
            href = href + "?" + request.getQueryString();
        }
        return String.format("redirect:%s", href);
    }
}
