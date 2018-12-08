package com.zhongmei.yunfu;


import com.zhongmei.yunfu.service.LoginManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = {"/*"})
public class WebAppFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(WebAppFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request1, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) request1;
        String basePath = WebAppConfig.getBasePath(request);
        request.setAttribute("basePath", basePath + request.getContextPath());

        LoginManager.beginRequest(request);
        chain.doFilter(request, response);
        LoginManager.endRequest();
    }

    @Override
    public void destroy() {

    }
}
