package com.config.base;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;

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
