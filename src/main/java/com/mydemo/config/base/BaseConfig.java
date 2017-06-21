package com.mydemo.config.base;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.DispatcherServlet;

import java.util.concurrent.TimeUnit;

/**
 * Created by admin on 2017/6/12.
 */
@Configuration
@EnableTransactionManagement
public class BaseConfig{


    @Bean(name = "tomcatEmbeddedServletContainerFactory")
    @Qualifier(value = "tomcatEmbeddedServletContainerFactory")
    @Primary
    public TomcatEmbeddedServletContainerFactory servletContainerFactory(){
        TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
        factory.setPort(8080);
        factory.setSessionTimeout(10, TimeUnit.MINUTES);
        //factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND,"/notfound.html"));
        return factory;
    }


}
