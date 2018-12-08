package com.zhongmei.yunfu;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.File;
import java.io.IOException;

@ServletComponentScan
@SpringBootApplication
//@EnableFeignClients
//@EnableDiscoveryClient
@EnableTransactionManagement
public class YunfuDemoApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(YunfuDemoApplication.class);
    }

    public static void main(String[] args) {
        //SpringApplication.run(YunfuDemoApplication.class, args);
        SpringApplication springApplication = new SpringApplication(YunfuDemoApplication.class);
        springApplication.setBannerMode(Banner.Mode.OFF);
        springApplication.run(args);
    }

    /*@Value("${https.port}")
    private Integer port;

    @Value("${https.ssl.key-store-password}")
    private String key_store_password;

    @Value("${https.ssl.key-password}")
    private String key_password;

    @Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        tomcat.addAdditionalTomcatConnectors(createSslConnector()); // 添加http
        return tomcat;
    }

    // 配置https
    private Connector createSslConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
        try {
            File keystore = new ClassPathResource("sample.jks").getFile();
            *//*File truststore = new ClassPathResource("sample.jks").getFile();*//*
            connector.setScheme("https");
            connector.setSecure(true);
            connector.setPort(port);
            protocol.setSSLEnabled(true);
            protocol.setKeystoreFile(keystore.getAbsolutePath());
            protocol.setKeystorePass(key_store_password);
            protocol.setKeyPass(key_password);
            return connector;
        } catch (IOException ex) {
            throw new IllegalStateException("can't access keystore: [" + "keystore"
                    + "] or truststore: [" + "keystore" + "]", ex);
        }
    }*/
}
