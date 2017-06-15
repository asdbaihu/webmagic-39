package com.mydemo.config.init;

import com.mydemo.common.Constant;
import com.mydemo.dao.CityMapper;
import com.mydemo.dao.UserMapper;
import com.mydemo.domain.City;
import com.mydemo.domain.User;
import com.mydemo.domain.enumtype.CityType;
import com.mydemo.service.InitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/6/14.
 */
@Component
public class InitConfig implements CommandLineRunner{

    private final static Logger logger = LoggerFactory.getLogger(InitConfig.class);

    @Autowired
    private InitService initService;

    @Override
    public void run(String... args) throws Exception {
        logger.info("初始化内存constant开始");
        initService.init();
        logger.info("初始化内存constant结束");
    }
}
