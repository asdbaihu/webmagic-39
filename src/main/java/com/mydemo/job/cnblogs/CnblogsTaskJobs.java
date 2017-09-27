package com.mydemo.job.cnblogs;

import com.mydemo.common.Constant;
import com.mydemo.domain.Article;
import com.mydemo.service.ArticleService;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class CnblogsTaskJobs {

    private final static Logger logger = LoggerFactory.getLogger(CnblogsTaskJobs.class);
    private static final String url = "https://www.cnblogs.com/cate/java/";

    @Resource
    private ArticleService articleService;

    @Scheduled(cron = "0 40 13 * * ? ")
    public void pullOnce(){
        logger.info("开始搞事.............");
        int pageNum = pull(url);
        for(int i=2;i<=pageNum;i++){
            pull(url+"#p"+i);
        }
        logger.info("搞事结束.............");
    }


//    @Scheduled(cron = "0 7 11 * * ? ")
//    public void pullEveryDay(){
//
//    }


    private int pull(String url){
        int pageNum = 0;
        try {
            String body = get(url);
            Document doc = Jsoup.parseBodyFragment(body);
            Element page = doc.getElementById("paging_block");
            Elements pages = page.child(0).children();
            pageNum = Integer.valueOf(pages.get(pages.size()-2).text());
            Elements elements = doc.getElementsByClass("post_item");
            for(Element element : elements){
                Elements viewElement = element.getElementsByClass("article_view");
                String value = viewElement.get(0).child(0).text();
                String regEx="[^0-9]";
                value = value.replaceAll(regEx,"");
                if(Integer.valueOf(value)>=100){
                    Elements lnkElement = element.getElementsByClass("titlelnk");
                    String href = lnkElement.attr("href");
                    body = get(href);
                    Document document = Jsoup.parseBodyFragment(body);
                    Element articleEle = document.getElementById("cnblogs_post_body");
                    Element title = document.getElementById("cb_post_title_url");
                    Article article = new Article();
                    article.setContent(articleEle.toString());
                    article.setTitle(title.text());
                    article.setCategoryId(2l);
                    article.setUserId(1l);
                    article.setViewCount(0);
                    article.setCommentCount(0);
                    articleService.addArticle(article);
                }
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        return pageNum;
    }

    private String get(String url) throws Exception{
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
}
