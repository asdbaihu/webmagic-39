package com.config.init;

import com.myblog.service.InitService;
import com.myweixin.service.WeChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created by admin on 2017/6/14.
 */
@Component
public class InitConfig implements CommandLineRunner{

    private final static Logger logger = LoggerFactory.getLogger(InitConfig.class);

    @Autowired
    private InitService initService;
    @Autowired
    private WeChatService weChatService;

    @Override
    public void run(String... args) throws Exception {
        logger.info("初始化内存constant开始");
        initService.init();
        logger.info("初始化内存constant结束");

        logger.info("初始化微信菜单开始");
        weChatService.init();
        logger.info("初始化微信菜单结束");

    }
}
