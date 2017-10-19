package com.myweixin.service;

import com.alibaba.fastjson.JSON;
import com.common.Constant;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WeChatService {

    private static final String appId = "wx9186cd4679b66eba";
    private static final String appSecret = "bcbf699202b5457713e24188aa486d2e";
    private static final String baseUrl = "https://api.weixin.qq.com/cgi-bin/";

    public void init() throws Exception{
        Constant.WE_CHAT_TOKEN = refreshToken();
        deleteMenu();
        Map<String,Object> dataMap = new HashMap<>();
        List<Map<String,Object>> dataList = new ArrayList<>();
        Map<String,Object> button1 = new HashMap<>();
        button1.put("name","扫码1");
        button1.put("type","view");
        button1.put("url","http://www.baidu.com");
        dataList.add(button1);

        Map<String,Object> button2 = new HashMap<>();
        button2.put("name","扫码2");
        button2.put("type","view");
        button2.put("url","http://www.baidu.com");
        dataList.add(button2);

        Map<String,Object> button3 = new HashMap<>();
        button3.put("name","扫码3");
        button3.put("type","view");
        button3.put("url","http://www.baidu.com");
        dataList.add(button3);
        dataMap.put("button",dataList);

        createMenu(dataMap);
    }

    public boolean createMenu(Map<String,Object> dataMap) throws Exception{
        String url = baseUrl+"menu/create?access_token="+ Constant.WE_CHAT_TOKEN;
        String res = null;
        CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url);
        HttpEntity entity = new StringEntity(JSON.toJSONString(dataMap),"UTF-8");
        httpPost.setEntity(entity);
        CloseableHttpResponse response = closeableHttpClient.execute(httpPost);
        HttpEntity httpEntity = response.getEntity();
        if (httpEntity != null) {
            res = EntityUtils.toString(httpEntity, "UTF-8");
        }
        closeableHttpClient.close();
        Map<String,String> data = (Map<String,String>) JSON.parse(res);
        return ("0".equals(data.get("errcode")));
    }


    public boolean deleteMenu() throws Exception{
        String url = baseUrl+"menu/delete?access_token="+ Constant.WE_CHAT_TOKEN;
        String res = null;
        CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = closeableHttpClient.execute(httpGet);
        HttpEntity httpEntity = response.getEntity();
        if (httpEntity != null) {
            res = EntityUtils.toString(httpEntity, "UTF-8");
        }
        closeableHttpClient.close();
        Map<String,String> data = (Map<String,String>) JSON.parse(res);
        return ("0".equals(data.get("errcode")));
    }

    public String refreshToken() throws Exception{
        String url = baseUrl+"token?grant_type=client_credential&appid="+appId+"&secret="+appSecret;
        String res = null;
        CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = closeableHttpClient.execute(httpGet);
        HttpEntity httpEntity = response.getEntity();
        if (httpEntity != null) {
            res = EntityUtils.toString(httpEntity, "UTF-8");
        }
        closeableHttpClient.close();
        Map<String,String> data = (Map<String,String>) JSON.parse(res);
        return data.get("access_token");
    }
}
