package com.myweixin.common;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TulingService {

    //免费版接口
    private static final String apiUrl = "http://www.tuling123.com/openapi/api";
    private static final String token = "3dbc64804aa04c4aa24379df31e43784";
    private static final String userId = "155267";


    public String postMessage(String message) throws Exception{
        String body = null;
        CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(apiUrl);
        httpPost.addHeader("Content-type","application/json; charset=UTF-8");
        httpPost.addHeader("Accept", "application/json");
        Map<String,String> map = new HashMap<>();
        map.put("key",token);
        map.put("info",message);
        map.put("userid",userId);
        httpPost.setEntity(new StringEntity(JSON.toJSONString(map), "UTF-8"));
        CloseableHttpResponse response = closeableHttpClient.execute(httpPost);
        HttpEntity httpEntity = response.getEntity();
        if (httpEntity != null) {
            body = EntityUtils.toString(httpEntity, "UTF-8");
        }
        closeableHttpClient.close();
        return body;
    }


    private static final String apiUrl2 = "http://openapi.tuling123.com/openapi/api/v2";

    public String postMessageV2() throws Exception{
        String body = null;
        CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(apiUrl);
        httpPost.addHeader("Content-type","application/json; charset=UTF-8");
        httpPost.addHeader("Accept", "application/json");
        Map<String,Object> map = new HashMap<>();
        map.put("reqType",1);
        map.put("perception",new HashMap<String,String>(){{put("inputText","老子无所畏惧");put("inputImage","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1508148370699&di=7bc98cc8cffaba0b664a7758a5abc7e8&imgtype=0&src=http%3A%2F%2Fimg3.duitang.com%2Fuploads%2Fitem%2F201608%2F16%2F20160816051459_U25BF.thumb.700_0.jpeg");}});
        map.put("userInfo",new HashMap<String,String>(){{put("apiKey",token);put("userId",userId);}});
        httpPost.setEntity(new StringEntity(JSON.toJSONString(map), "UTF-8"));
        CloseableHttpResponse response = closeableHttpClient.execute(httpPost);
        HttpEntity httpEntity = response.getEntity();
        if (httpEntity != null) {
            body = EntityUtils.toString(httpEntity, "UTF-8");
        }
        closeableHttpClient.close();
        return body;
    }
}
