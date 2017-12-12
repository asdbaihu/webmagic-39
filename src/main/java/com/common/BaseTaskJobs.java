package com.common;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.net.URI;
import java.util.Map;

@Component
public class BaseTaskJobs {

    protected String get(String url) throws Exception{
        String body = null;
        CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = closeableHttpClient.execute(httpGet);
        HttpEntity httpEntity = response.getEntity();
        if (httpEntity != null) {
            body = EntityUtils.toString(httpEntity, "UTF-8");
        }
        closeableHttpClient.close();
        return body;
    }

    protected boolean testProxy(String url,String host,int port){
        boolean  flag = false;
        CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(5000)
                .setConnectTimeout(5000)
                .setConnectionRequestTimeout(5000)
                .setStaleConnectionCheckEnabled(true)
                .setProxy(new HttpHost(host, port))
                .build();
        httpGet.setConfig(requestConfig);
        try {
            CloseableHttpResponse response = closeableHttpClient.execute(httpGet);
            if(response.getStatusLine().getStatusCode()==200){
                flag = true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    protected InputStream getFile(String url, Map<String,String> headers,String host,int port) {
        CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(50000)
                .setConnectTimeout(50000)
                .setConnectionRequestTimeout(50000)
                .setStaleConnectionCheckEnabled(true)
                .setProxy(new HttpHost(host, port))
                .build();
        httpGet.setConfig(requestConfig);
        headers.forEach((k,v)->{
            httpGet.setHeader(k,v);
        });

        try {
            CloseableHttpResponse response = closeableHttpClient.execute(httpGet);
            if(response.getStatusLine().getStatusCode()==200){
                HttpEntity httpEntity = response.getEntity();
                if (httpEntity != null) {
                    return httpEntity.getContent();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    protected String getForURL(String url,String json) throws Exception{
        String body = null;
        CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(url);
        URI uri = new URIBuilder(httpGet.getURI())
                .addParameter("params",json)
                .build();
        ((HttpRequestBase) httpGet).setURI(uri);
        CloseableHttpResponse response = closeableHttpClient.execute(httpGet);
        HttpEntity httpEntity = response.getEntity();
        if (httpEntity != null) {
            body = EntityUtils.toString(httpEntity, "UTF-8");
        }
        closeableHttpClient.close();
        return body;
    }

    protected String post(String url,Map<String,String> data)throws Exception{
        String body = null;
        CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url);
        MultipartEntityBuilder multipartEntity = MultipartEntityBuilder.create();
        data.forEach((k,v)->{
            multipartEntity.addPart(k, new StringBody(v, ContentType.create("text/plain", Consts.UTF_8)));
        });
        httpPost.setEntity(multipartEntity.build());
        CloseableHttpResponse response = closeableHttpClient.execute(httpPost);
        HttpEntity httpEntity = response.getEntity();
        if (httpEntity != null) {
            body = EntityUtils.toString(httpEntity, "UTF-8");
        }
        closeableHttpClient.close();
        return body;
    }
}
