package com.myresource.codesky;


import com.myblog.job.itxinwen.ItxinwenTaskJobs;
import com.myresource.BaseTaskJobs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author admin
 */
@Component
public class CodeskyTaskJobs extends BaseTaskJobs {

    private final static Logger logger = LoggerFactory.getLogger(CodeskyTaskJobs.class);
    private final static String baseurl ="http://www.codesky.net/";

    @Scheduled(cron = "0 0 6 * * ? ")
    public void pullCodeskyResource(){

    }
}
