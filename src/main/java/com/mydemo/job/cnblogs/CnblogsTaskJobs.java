package com.mydemo.job.cnblogs;

import com.mydemo.domain.Article;
import com.mydemo.job.BaseTaskJobs;
import com.mydemo.service.ArticleService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class CnblogsTaskJobs extends BaseTaskJobs{

    private final static Logger logger = LoggerFactory.getLogger(CnblogsTaskJobs.class);
    private final static String baseurl = "https://www.cnblogs.com/cate/java/";
    private final static String pageurl = "https://www.cnblogs.com/mvc/AggSite/PostList.aspx";

    private static Map<String,String> pageMap = new HashMap<>();
    static{
        pageMap.put("CategoryType","SiteCategory");
        pageMap.put("ParentCategoryId", "2");
        pageMap.put("CategoryId","106876");
        pageMap.put("PageIndex","2");
        pageMap.put("TotalPostCount","4000");
        pageMap.put("ItemListActionName","PostList");
    }

    @Resource
    private ArticleService articleService;

//    @Scheduled(cron = "0 32 11 * * ? ")
    public void pullOnce(){
        logger.info("开始搞事.............");
        int pageNum = pull(baseurl);
        for(int i=2;i<=pageNum;i++){
            pageMap.put("PageIndex",String.valueOf(i));
            pagePull(pageurl,pageMap);
        }
        logger.info("搞事结束.............");
    }


    @Scheduled(cron = "0 0 3 */3 * ? ")
    public void pullThreeDay(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE,-3);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String date = simpleDateFormat.format(cal.getTime());
        logger.info("开始搞事.............");
        int pageNum = pull(baseurl);
        for(int i=2;i<=pageNum;i++){
            pageMap.put("PageIndex",String.valueOf(i));
            pagePull(pageurl,pageMap,date);
        }
        logger.info("搞事结束.............");
    }


    private int pull(String url){
        int pageNum = 0;
        try {
            String body = get(url);
            Document doc = Jsoup.parseBodyFragment(body);
            Element page = doc.getElementById("paging_block");
            Elements pages = page.child(0).children();
            pageNum = Integer.valueOf(pages.get(pages.size()-2).text());
            Elements elements = doc.getElementsByClass("post_item");
            save(elements);
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        return pageNum;
    }


    private void save(Elements elements) throws Exception{
        for(Element element : elements){
            Elements viewElement = element.getElementsByClass("article_view");
            String value = viewElement.get(0).child(0).text();
            String regEx="[^0-9]";
            value = value.replaceAll(regEx,"");
            if(Integer.valueOf(value)>=100){
                Elements lnkElement = element.getElementsByClass("titlelnk");
                String href = lnkElement.attr("href");
                String body = get(href);
                Document document = Jsoup.parseBodyFragment(body);
                Element articleEle = document.getElementById("cnblogs_post_body");
                Element title = document.getElementById("cb_post_title_url");
                Article article = new Article();
                article.setContent(articleEle.toString());
                article.setTitle(title.text());
                article.setCategoryId(2l);
                article.setViewCount(0);
                article.setCommentCount(0);
                List<Article> articleList = articleService.getArticles(title.text(),2l);
                if(articleList!=null&&articleList.size()>0){
                    continue;
                }
                articleService.addArticle(article);
            }
        }
    }

    public void pagePull(String pageUrl,Map<String,String> dataMap){
        try {
            String body = post(pageUrl,dataMap);
            Document doc = Jsoup.parseBodyFragment(body);
            Elements elements = doc.getElementsByClass("post_item");
            save(elements);
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
    }

    public void pagePull(String pageUrl,Map<String,String> dataMap,String yesDate){
        try {
            String body = post(pageUrl,dataMap);
            Document doc = Jsoup.parseBodyFragment(body);
            Elements elements = doc.getElementsByClass("post_item");
            for(Element element : elements) {
                Elements foot = element.getElementsByClass("post_item_foot");
                String pullDate = foot.get(0).child(0).text().substring(4);
                if (pullDate.compareTo(yesDate)>0) {
                    Elements viewElement = element.getElementsByClass("article_view");
                    String value = viewElement.get(0).child(0).text();
                    String regEx = "[^0-9]";
                    value = value.replaceAll(regEx, "");
                    if (Integer.valueOf(value) >= 100) {
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
                        article.setViewCount(0);
                        article.setCommentCount(0);
                        List<Article> articleList = articleService.getArticles(title.text(),2l);
                        if(articleList!=null&&articleList.size()>0){
                            continue;
                        }
                        articleService.addArticle(article);
                    }
                }
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
    }
}
