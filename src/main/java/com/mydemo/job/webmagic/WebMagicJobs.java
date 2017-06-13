package com.mydemo.job.webmagic;

import com.mydemo.job.common.GithubRepoPageProcessor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;

/**
 * Created by admin on 2017/6/12.
 */
@Component
public class WebMagicJobs {

    //@Scheduled(cron = "0 7 11 * * ? ")
    public void runJobs(){

        System.out.print("fajslkdfjaklsjf");

        Spider.create(new GithubRepoPageProcessor())
                //从https://github.com/code4craft开始抓
                .addUrl("https://github.com/code4craft")
                .addPipeline(new JsonFilePipeline("D:\\"))
                //启动爬虫
                .run();
    }
}
