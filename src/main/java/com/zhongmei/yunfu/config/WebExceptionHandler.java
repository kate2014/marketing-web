package com.zhongmei.yunfu.config;

import com.zhongmei.yunfu.AuthLoginException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2017/7/12.
 */
@Component
public class WebExceptionHandler implements HandlerExceptionResolver {

    private static Logger log = LoggerFactory.getLogger(WebExceptionHandler.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (ex instanceof AuthLoginException) {
            log.error("got exception: {}", ex.getMessage());
            /*AuthLoginException authLoginException = (AuthLoginException) ex;
            String url = "/login";
            if (StringUtils.isNotBlank(authLoginException.getUrl())) {
                url += "?href=" + authLoginException.getUrl();
            }*/
            return new ModelAndView(new RedirectView(request.getContextPath() + "/login"));
        }

        String requestURI = request.getRequestURI();
        log.error("got exception: ", ex);
        //统一处理api异常
        /*if (requestURI.startsWith("/internal/") || requestURI.startsWith("/pos/") || requestURI.startsWith("/wxapp/") || requestURI.startsWith("/web/")) {
            int status = response.getStatus();
            String message = ex.getMessage();
            if (ex instanceof ApiResponseStatusException) {
                ApiResponseStatusException statusException = (ApiResponseStatusException) ex;
                status = statusException.getStatus().getValue();
                message = statusException.getStatus().getReason();
            }
            ModelAndView mv = new ModelAndView();
            FastJsonJsonView view = new FastJsonJsonView();
            //ApiResult.newResult(response.getStatus(), ex.getMessage());
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("status", status);
            attributes.put("message", message);
            //attributes.put("content", ex.getMessage());
            view.setAttributesMap(attributes);
            mv.setView(view);
            return mv;
        }*/

        return new ModelAndView("error500");
    }
}
