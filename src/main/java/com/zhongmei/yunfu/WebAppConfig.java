package com.zhongmei.yunfu;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.zhongmei.yunfu.service.LoginManager;
import com.zhongmei.yunfu.thymeleaf.ThymeleafRequestDataValueProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.support.RequestDataValueProcessor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/12.
 */
@Configuration   //标注此文件为一个配置项，spring boot才会扫描到该配置。该注解类似于之前使用xml进行配置
public class WebAppConfig implements WebMvcConfigurer {

    private static final Logger log = LoggerFactory.getLogger(WebAppConfig.class);

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .maxAge(3600);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }

    public static String getBasePath(HttpServletRequest request) {
        /*String basePath = request.getScheme() + "://";
        basePath += request.getServerName();
        if (request.getServerPort() != 80) {
            basePath += ":" + request.getServerPort();
        }*/
        //basePath += request.getContextPath(); /*+ "/"*/;
        //return basePath;
        return "";
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //对来自/user/** 这个链接来的请求进行拦截
        registry.addInterceptor(new HandlerInterceptor() {
            private long startTime = 0;

            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                startTime = System.currentTimeMillis();
                LoginManager.beginRequest(request);
                String basePath = getBasePath(request);
                request.setAttribute("basePath", basePath + request.getContextPath());
                //request.getSession().setAttribute("__base_path__", basePath);

                if (handler instanceof HandlerMethod) {
                    HandlerMethod h = (HandlerMethod) handler;
                    String controllerName = h.getBean().getClass().getName();
                    String controllerMethodName = h.getMethod().getName();
                }

                String url = request.getRequestURL() + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
                String params = getJsonBody(request);
                log.info(String.format("[%-4s %15s] %s -> %s", request.getMethod(), request.getRemoteHost(), url, params));
                return true;
            }

            private String getJsonBody(HttpServletRequest request) throws IOException {
                /*String contentType = request.getHeader("Content-Type");
                String[] contentTypeItems = contentType.split(";");
                for (String contentTypeItem : contentTypeItems) {
                    if (contentTypeItem.equalsIgnoreCase("application/json")) {
                        InputStream inputStream = request.getInputStream();

                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        byte[] buf = new byte[8 * 1024];
                        int readCount;
                        PushbackInputStream streamReader = new PushbackInputStream(inputStream, buf.length);
                        while ((readCount = streamReader.read(buf)) != -1) {
                            byteArrayOutputStream.write(buf, 0, readCount);
                        }

                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        for (int i = 0; i < byteArray.length; i += buf.length) {
                            streamReader.unread(byteArray, i, Math.min(i + buf.length, byteArray.length));
                        }

                        return new String(byteArray, StandardCharsets.UTF_8);
                    }
                }*/
                return "";
            }


            @Override
            public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
                //String basePath = getBasePath(request);
                //request.getSession().setAttribute("basePath", basePath);
                /*if (modelAndView != null && modelAndView.getModelMap() != null) {
                    modelAndView.getModelMap().addAttribute("__base_path__", basePath);
                }*/
            }

            @Override
            public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
                LoginManager.endRequest();
                log.info("time: {}", String.format("%.2fs", (System.currentTimeMillis() - startTime) / 1000f));
            }
        })
                .addPathPatterns("/**")
                .excludePathPatterns("/**/*.ico");
        registry.addInterceptor(new UserInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(MainsiteErrorController.ERROR_PATH)
                .excludePathPatterns("/demo/**")
                .excludePathPatterns("/internal/**")
                .excludePathPatterns("/pos/**")
                .excludePathPatterns("/wxapp/**")
                .excludePathPatterns("/login")
                .excludePathPatterns("/login/**")
                .excludePathPatterns("/**/token/*")
                // js css过滤
                //.excludePathPatterns("/static/**")
                .excludePathPatterns("/**/*.html")
                .excludePathPatterns("/**/fonts/*")
                .excludePathPatterns("/**/*.js")
                .excludePathPatterns("/**/*.css")
                .excludePathPatterns("/**/*.png")
                .excludePathPatterns("/**/*.gif")
                .excludePathPatterns("/**/*.jpg")
                .excludePathPatterns("/**/*.jpeg")
                .excludePathPatterns("/**/*.ico");
    }

    /*@Bean
    @Primary
    @ConditionalOnMissingBean(ObjectMapper.class)
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();

        // 通过该方法对mapper对象进行设置，所有序列化的对象都将按改规则进行系列化
        // Include.Include.ALWAYS 默认
        // Include.NON_DEFAULT 属性为默认值不序列化
        // Include.NON_EMPTY 属性为 空（""） 或者为 NULL 都不序列化，则返回的json是没有这个字段的。这样对移动端会更省流量
        // Include.NON_NULL 属性为NULL 不序列化,就是为null的字段不参加序列化
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

        // 禁用空对象转换json校验
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // 字段保留，将null值转为""
        *//*objectMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
            @Override
            public void serialize(Object o, JsonGenerator jsonGenerator,
                                  SerializerProvider serializerProvider) throws IOException {
                jsonGenerator.writeString("");
            }
        });*//*
        return objectMapper;
    }*/

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        for (HttpMessageConverter<?> converter : converters) {
            if (StringHttpMessageConverter.class.isAssignableFrom(converter.getClass())) {
                StringHttpMessageConverter stringHttpMessageConverter = (StringHttpMessageConverter) converter;
                stringHttpMessageConverter.setDefaultCharset(StandardCharsets.UTF_8);
                break;
            }
        }
    }

    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = getFastJsonHttpMessageConverter();
        return new HttpMessageConverters(fastJsonHttpMessageConverter);
    }

    private FastJsonHttpMessageConverter getFastJsonHttpMessageConverter() {
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter() {
            @Override
            public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
                Object readObject = super.read(type, contextClass, inputMessage);
                log.info("json read:  header -> {}, body -> {}", inputMessage.getHeaders(), JSON.toJSONString(readObject));
                return readObject;
            }

            @Override
            public void write(Object o, Type type, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
                super.write(o, type, contentType, outputMessage);
                log.info("json write:  header -> {}, body -> {}", outputMessage.getHeaders(), JSON.toJSONString(o));
            }
        };
        //2:添加fastJson的配置信息;
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        //3处理中文乱码问题
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        //4.在convert中添加配置信息.
        fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        return fastJsonHttpMessageConverter;
    }

    @Value("${remote.maxTotalConnect:5}")
    private int maxTotalConnect; //连接池的最大连接数默认为5
    @Value("${remote.maxConnectPerRoute:200}")
    private int maxConnectPerRoute; //单个主机的最大连接数
    @Value("${remote.connectTimeout:5000}")
    private int connectTimeout; //连接超时默认5s
    @Value("${remote.readTimeout:30000}")
    private int readTimeout; //读取超时默认30s

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        /*HttpClient httpClient = HttpClientBuilder.create()
                .setMaxConnTotal(maxTotalConnect)
                .setMaxConnPerRoute(maxConnectPerRoute)
                .build();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        factory.setConnectTimeout(connectTimeout);
        factory.setReadTimeout(readTimeout);
        return factory;*/

        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(connectTimeout);
        factory.setReadTimeout(readTimeout);
        RestTemplate restTemplate = new RestTemplate(factory);
        List<HttpMessageConverter<?>> converterList = restTemplate.getMessageConverters();
        configureMessageConverters(converterList);

        //加入FastJson转换器
        converterList.add(0, getFastJsonHttpMessageConverter());
        return restTemplate;
    }

    /*@Value("${application.cnode.url}")
    private String baseUrl;*/

    /*@Bean
    public RestService nodeClient() throws InterruptedException {
        return HystrixFeign.builder()
                //.decoder(new JacksonDecoder())
                //.encoder(new JacksonEncoder())
                .setterFactory((target, method) ->
                        new SetterFactory.Default().create(target, method).
                                andCommandPropertiesDefaults(HystrixCommandProperties.defaultSetter().
                                        withExecutionTimeoutInMilliseconds(10000)))
                .target(RestService.class, this.baseUrl);
    }*/

    public class UserInterceptor implements HandlerInterceptor {

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            //这里填写你允许进行跨域的主机
            //response.setHeader("Access-Control-Allow-Origin", "*");
           /* String url = request.getRequestURL().toString();
            String method = request.getMethod();
            String queryString = request.getQueryString();
            logger.info(String.format("[%s] %s -> %s", method, url, queryString));*/

            //LoginManager.beginRequest(request);
            String requestURI = request.getRequestURI();
            if (!excludePathPatterns(requestURI)) {
                if (!LoginManager.get().isLogin()) {
                    throw new AuthLoginException("没有登录", requestURI);
                }
                if (!LoginManager.get().authUri(requestURI)) {
                    throw new AuthLoginException("没有访问权限", requestURI);
                }
            }

            return true;
        }

        private boolean excludePathPatterns(String requestURI) {
            return requestURI.equals(MainsiteErrorController.ERROR_PATH)
                    || requestURI.equals("/login")
                    || requestURI.startsWith("/login/");
        }
    }

    /*@Bean
    public ThymeleafDialect thymeleafDialect(){
        return new ThymeleafDialect();
    }*/

    @Bean
    public RequestDataValueProcessor requestDataValueProcessor() {
        return new ThymeleafRequestDataValueProcessor();
    }
}
