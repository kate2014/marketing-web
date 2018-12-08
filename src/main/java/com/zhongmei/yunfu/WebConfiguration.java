//package com.zhongmei.yunfu;
//
//import org.apache.catalina.filters.RemoteIpFilter;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.servlet.*;
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//
//@Configuration
//public class WebConfiguration {
//
//    private static final Logger logger = LoggerFactory.getLogger(WebConfiguration.class);
//
//    @Bean
//    public RemoteIpFilter remoteIpFilter() {
//        return new RemoteIpFilter();
//    }
//
//    @Bean
//    public FilterRegistrationBean testFilterRegistration() {
//        FilterRegistrationBean registration = new FilterRegistrationBean();
//        registration.setFilter(new MyFilter());
//        registration.addUrlPatterns("/*");
//        registration.addInitParameter("paramName", "paramValue");
//        registration.setName("MyFilter");
//        registration.setOrder(1);
//        return registration;
//    }
//
//    public class MyFilter implements Filter {
//        @Override
//        public void destroy() {
//            // TODO Auto-generated method stub
//        }
//
//        @Override
//        public void doFilter(ServletRequest srequest, ServletResponse sresponse, FilterChain filterChain)
//                throws IOException, ServletException {
//            HttpServletRequest request = (HttpServletRequest) srequest;
//            //System.out.println("this is MyFilter,url :"+request.getRequestURI());
//
//            String url = request.getRequestURL().toString();
//            String method = request.getMethod();
//            String queryString = request.getQueryString();
//            logger.info(String.format("[%s] %s -> %s", method, url, queryString));
//
//
//
//            filterChain.doFilter(srequest, sresponse);
//        }
//
//        @Override
//        public void init(FilterConfig arg0) throws ServletException {
//            // TODO Auto-generated method stub
//        }
//    }
//}
