package com.myweixin.job;

import com.common.Constant;
import com.myweixin.service.WeChatService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class WeChatTokenTaskJob {

    @Resource
    private WeChatService weChatService;

    @Scheduled(cron = "0 0 */2 * * ? ")
    public void refreshToken(){
        try {
            String token = weChatService.refreshToken();
            Constant.WE_CHAT_TOKEN = token;
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
