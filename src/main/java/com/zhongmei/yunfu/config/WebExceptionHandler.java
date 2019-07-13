package com.zhongmei.yunfu.config;

import com.zhongmei.yunfu.AuthLoginException;
import com.zhongmei.yunfu.api.ApiRespStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

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
        log.error("got exception: ", ex);
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            if (handlerMethod.getBeanType().isAnnotationPresent(RestController.class)
                    || handlerMethod.hasMethodAnnotation(ResponseBody.class)) {
                int status = response.getStatus();
                String message = ex.getMessage();
                if (ex instanceof ApiRespStatusException) {
                    ApiRespStatusException statusException = (ApiRespStatusException) ex;
                    if (statusException != null) {
                        status = statusException.getStatus().getValue();
                    }
                    //message = statusException.getStatus().getReason();
                }

                //return JSON.toJSONString(ApiResult.newResult(status, message));
                //return ApiResult.newResult(status, message);
                ModelAndView mv = new ModelAndView();
                MappingJackson2JsonView view = new MappingJackson2JsonView();
            /*Map<String, Object> attributes = new HashMap<String, Object>();
            attributes.put("code", "1000001");
            attributes.put("msg", ex.getMessage());
            view.setAttributesMap(attributes);*/
                mv.setView(view);
                mv.addObject("status", status);
                mv.addObject("message", message);
                return mv;
            }
        }

        if (ex instanceof AuthLoginException) {
            //log.error("got exception: {}", ex.getMessage());
            /*AuthLoginException authLoginException = (AuthLoginException) ex;
            String url = "/login";
            if (StringUtils.isNotBlank(authLoginException.getUrl())) {
                url += "?href=" + authLoginException.getUrl();
            }*/
            return new ModelAndView(new RedirectView(request.getContextPath() + "/login"));
        }

        String requestURI = request.getRequestURI();

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
