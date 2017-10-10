package com.mydemo.job.tripyum;

import com.mydemo.job.BaseTaskJobs;
import com.mydemo.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class TripyumTaskJobs extends BaseTaskJobs {

    private final static Logger logger = LoggerFactory.getLogger(TripyumTaskJobs.class);
    private final static String baseurl ="http://www.tripyum.com/Worldfood/index.html";

    @Resource
    private ArticleService articleService;
}
