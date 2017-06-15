package com.mydemo.controller;

import com.mydemo.config.init.InitConfig;
import com.mydemo.service.InitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created by admin on 2017/6/13.
 */
@Controller
public class WebController {
    private final static Logger logger = LoggerFactory.getLogger(InitConfig.class);

    @Autowired
    private InitService initService;


    @RequestMapping("/")
    public String web(){
        return "index";
    }

    @RequestMapping("/init")
    @ResponseBody
    public String init(String... args) throws Exception {
        logger.info("初始化内存constant开始");
        initService.init();
        logger.info("初始化内存constant结束");
        return "success";
    }
}
