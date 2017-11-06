package com.myblog.job.mafengwo;

import com.alibaba.fastjson.JSON;
import com.domain.Article;
import com.myblog.job.BaseTaskJobs;
import com.myblog.service.ArticleService;
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
public class MafengwoTaskJobs extends BaseTaskJobs{

    private final static Logger logger = LoggerFactory.getLogger(MafengwoTaskJobs.class);
    private final static String baseurl = "http://www.mafengwo.cn/";
    private final static String pageurl = "http://pagelet.mafengwo.cn/note/pagelet/recommendNoteApi";


    private final static String expend = "<link href=\"http://css.mafengwo.net/css/cv/css+base:css+jquery.suggest:css+plugins:css+plugins+jquery.jgrowl:css+other+popup:css+mfw-header.2015^YlVS^1493708283.css\" rel=\"stylesheet\" type=\"text/css\"/>\n" +
            "<link href=\"http://css.mafengwo.net/css/cv/css+jquery-ui-1.8.18.custom:css+new_notes+new_notes:css+new_notes+schedule_info:css+new_notes+step:css+new_notes+sideview:css+new_notes+notes_comments:css+mfw_comment^YlNV^1504674753.css\" rel=\"stylesheet\" type=\"text/css\"/>";

    private static Map<String,String> pageMap = new HashMap<>();
    static{
        pageMap.put("type","0");
        pageMap.put("objid", "0");
        pageMap.put("page","1");
        pageMap.put("ajax","1");
        pageMap.put("retina","0");
    }

    @Resource
    private ArticleService articleService;

//    @Scheduled(cron = "0 58 17 * * ? ")
    public void pullOnce(){
        logger.info("开始搞事.............");
        Map map = new HashMap();
        map.put("type","0");
        try {
            int pageNum = pagePull(pageurl,map);
            for(int i=2;i<=pageNum;i++){
                pageMap.put("page",String.valueOf(i));
                pagePull(pageurl,pageMap);
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        logger.info("搞事结束.............");
    }


    @Scheduled(cron = "0 0 0 */3 * ? ")
    public void pullThreeDay(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE,-3);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String date = simpleDateFormat.format(cal.getTime());
        logger.info("开始搞事.............");
        Map map = new HashMap();
        map.put("type","0");
        try {
            int pageNum = pagePull(pageurl,map);
            for(int i=2;i<=pageNum;i++){
                pageMap.put("page",String.valueOf(i));
                pagePull(pageurl,pageMap,date);
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        logger.info("搞事结束.............");
    }



    private void save(Elements elements) throws Exception{
        for(Element element : elements){
            Element span = element.getElementsByClass("tn-ding").get(0);
            int value = Integer.valueOf(span.child(1).text());
            if(value>=500){
                Element lnkElement = element.child(0).child(0);
                String href = lnkElement.attr("href");
                String body = null;
                if(href.contains("mafengwo")){
                    body = get(href);
                }else{
                    body = get(baseurl+href);
                }

                if(StringUtils.isEmpty(body)||body.contains("您访问的页面不存在")){
                    continue;
                }
                Document document = Jsoup.parseBodyFragment(body);
                Element articleEle = document.getElementsByClass("vc_article").get(0);
                Element title = document.getElementsByClass("vi_con").get(0);
                Article article = new Article();
                Elements imgs = articleEle.getElementsByClass("_j_lazyload");
                for(Element img : imgs){
                    String imageUrl = img.attr("data-src");
                    img.attr("src",imageUrl);
                    img.removeAttr("data-src");
                }
                article.setContent(articleEle.toString()+expend);
                String titleStr = title.text().replaceAll("[^0-9\\u4e00-\\u9fa5]", "");
                article.setTitle(titleStr);
                article.setCategoryId(5L);
                article.setViewCount(0);
                article.setCommentCount(0);
                logger.info("title:" + titleStr);
                List<Article> articleList = articleService.getArticles(titleStr,5L);
                if(articleList!=null&&articleList.size()>0){
                    continue;
                }
                articleService.addArticle(article);
            }
        }
    }

    public int pagePull(String pageUrl,Map<String,String> dataMap) throws Exception{
        int pageNum = 0;
        String json = getForURL(pageUrl, JSON.toJSONString(dataMap));
        if(StringUtils.isEmpty(json)){
            logger.info("");
            return 0;
        }
        Map<String,Object> jsonMap = (Map<String,Object>)JSON.parse(json);
        Map<String,String> data = (Map<String,String>)jsonMap.get("data");
        Document doc = Jsoup.parseBodyFragment(data.get("html"));
        Element page = doc.getElementById("_j_tn_pagination");
        String value = page.child(0).text().split("\\/")[0];
        String regEx="[^0-9]";
        value = value.replaceAll(regEx,"");
        pageNum = Integer.parseInt(value);
        Element element = doc.getElementById("_j_tn_content");
        Elements elements = element.child(0).children();
        save(elements);
        return pageNum;
    }

    public void pagePull(String pageUrl,Map<String,String> dataMap,String yesDate) throws Exception{
        String json = getForURL(pageUrl, JSON.toJSONString(dataMap));
        Map<String,Object> jsonMap = (Map<String,Object>)JSON.parse(json);
        Map<String,String> data = (Map<String,String>)jsonMap.get("data");
        Document doc = Jsoup.parseBodyFragment(data.get("html"));
        Element content = doc.getElementById("_j_tn_content");
        Elements elements = content.child(0).children();
        for(Element element : elements) {
            String pullDate = element.getElementsByClass("vc_time").get(0).child(0).text();
            if (pullDate.compareTo(yesDate) > 0) {
                Element span = element.getElementsByClass("tn-ding").get(0);
                int value = Integer.valueOf(span.child(1).text());
                if (value >= 500) {
                    Element lnkElement = element.child(0).child(0);
                    String href = lnkElement.attr("href");
                    String body = get(baseurl + href);
                    Document document = Jsoup.parseBodyFragment(body);
                    Element articleEle = document.getElementsByClass("vc_article").get(0);
                    Element title = document.getElementsByClass("vi_con").get(0);
                    Article article = new Article();
                    article.setContent(articleEle.toString());
                    article.setTitle(title.text());
                    article.setCategoryId(5l);
                    article.setViewCount(0);
                    article.setCommentCount(0);
                    List<Article> articleList = articleService.getArticles(title.text(),5l);
                    if(articleList!=null&&articleList.size()>0){
                        continue;
                    }
                    articleService.addArticle(article);
                }
            }else{
                throw new RuntimeException("数据抓取完毕,抛出异常结束任务");
            }
        }
    }
}
