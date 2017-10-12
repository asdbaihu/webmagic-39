package com.myblog.controller;

import com.config.init.InitConfig;
import com.domain.Article;
import com.myblog.service.ArticleService;
import com.myblog.service.InitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * Created by admin on 2017/6/13.
 */
@Controller
public class WebController {
    private final static Logger logger = LoggerFactory.getLogger(InitConfig.class);

    @Autowired
    private InitService initService;
    @Autowired
    private ArticleService articleService;

    @RequestMapping("/")
    public String web(ModelMap model){
        return "redirect:home";
    }

    @RequestMapping("/init")
    @ResponseBody
    public String init(String... args) throws Exception {
        logger.info("初始化内存constant开始");
        initService.init();
        logger.info("初始化内存constant结束");
        return "success";
    }

    @RequestMapping(value = "/home")
    public String doLogin(ModelMap model) {
        List<Article> articles = articleService.getArticlesBycategoryId(1l);
        model.put("articles", articles);
        return "views/index";
    }

}
