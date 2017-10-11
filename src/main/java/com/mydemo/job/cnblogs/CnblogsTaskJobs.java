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
import org.springframework.util.StringUtils;

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
        try {
            int pageNum = pull(baseurl);
            for(int i=2;i<=pageNum;i++){
                pageMap.put("PageIndex",String.valueOf(i));
                pagePull(pageurl,pageMap);
            }
        }catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        logger.info("搞事结束.............");
    }


    @Scheduled(cron = "0 0 9 */3 * ? ")
    public void pullThreeDay(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE,-3);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String date = simpleDateFormat.format(cal.getTime());
        logger.info("开始搞事.............");
        try {
            int pageNum = pull(baseurl);
            for(int i=2;i<=pageNum;i++){
                pageMap.put("PageIndex",String.valueOf(i));
                pagePull(pageurl,pageMap,date);
            }
        }catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        logger.info("搞事结束.............");
    }


    private int pull(String url) throws Exception{
        int pageNum = 0;
        String body = get(url);
        if(StringUtils.isEmpty(body)){
            logger.info("cnblogs+抓取数据为空!");
            return 0;
        }
        Document doc = Jsoup.parseBodyFragment(body);
        Element page = doc.getElementById("paging_block");
        Elements pages = page.child(0).children();
        pageNum = Integer.valueOf(pages.get(pages.size()-2).text());
        Elements elements = doc.getElementsByClass("post_item");
        save(elements);
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
                if(StringUtils.isEmpty(body)){
                    continue;
                }
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

    public void pagePull(String pageUrl,Map<String,String> dataMap) throws Exception{
        String body = post(pageUrl,dataMap);
        if(StringUtils.isEmpty(body)){
            logger.info("cnblogs+抓取数据为空!");
        }
        Document doc = Jsoup.parseBodyFragment(body);
        Elements elements = doc.getElementsByClass("post_item");
        save(elements);
    }

    public void pagePull(String pageUrl,Map<String,String> dataMap,String yesDate)throws Exception{
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
                    if(StringUtils.isEmpty(body)){
                        continue;
                    }
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
            }else {
                throw new RuntimeException("数据抓取完毕,抛出异常结束任务");
            }
        }
    }
}
