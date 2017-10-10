package com.mydemo.job.mafengwo;

import com.alibaba.fastjson.JSON;
import com.mydemo.domain.Article;
import com.mydemo.job.BaseTaskJobs;
import com.mydemo.job.cnblogs.CnblogsTaskJobs;
import com.mydemo.service.ArticleService;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
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
            "<link href=\"http://css.mafengwo.net/css/cv/css+jquery-ui-1.8.18.custom:css+new_notes+new_notes:css+new_notes+schedule_info:css+new_notes+step:css+new_notes+sideview:css+new_notes+notes_comments:css+mfw_comment^YlNV^1504674753.css\" rel=\"stylesheet\" type=\"text/css\"/>\n" +
            "<script language=\"javascript\" src=\"http://js.mafengwo.net/js/cv/js+jquery-1.8.1.min:js+global+json2:js+M+Module:js+M+M:js+M+Log:js+m.statistics:js+advert+inspector^alw^1505811641.js\" type=\"text/javascript\" crossorigin=\"anonymous\"></script>\n" +
            "<script language=\"javascript\" type=\"text/javascript\">\n" +
            "if (typeof M !== \"undefined\" && typeof M.loadResource === \"function\") {\n" +
            "M.loadResource(\"http://js.mafengwo.net/js/cv/js+Dropdown:js+pageletcommon+pageHeadUserInfoWWWNormal:js+jquery.tmpl:js+M+module+InputListener:js+M+module+SuggestionXHR:js+M+module+DropList:js+M+module+Suggestion:js+M+module+MesSearchEvent:js+SiteSearch:js+AHeader:js+jquery.jplayer:js+M+module+TopTip:js+M+module+dialog+Layer:js+M+module+dialog+DialogBase:js+M+module+dialog+Dialog:js+M+module+dialog+alert:js+M+module+dialog+confirm:js+M+module+ScrollObserver:js+note+ControllerHead:js+M+module+Storage:js+M+module+ClickToggle:js+EmotionsHd:js+module+app+Page:js+M+module+FrequencyVerifyControl:js+M+module+FrequencyAppVerify:js+note+GinfoReply:js+M+module+Caret:js+M+module+HoverTip:js+note+ControllerReply:js+M+module+Slider:js+note+ControllerRelate:js+note+ControllerCatalog:js+jquery.mousewheel.min:js+M+module+ScrollBar:js+M+module+StickyAndStayBlock:js+note+ASideSticky:js+note+ControllerExpand:js+purl:js+M+module+Movearound:js+note+ADetail:js+jquery.upnum:js+note+ADetailImageBar:js+jquery.easing.1.3:js+M+module+Toggle:js+M+module+CopyrightProtecte:js+jquery.lazyload:js+common+TextSelectionTip:js+note+ACommon:js+note+anotice:js+note+poiofnote:js+cvideo+swfobject:js+note+play.video:js+note+ALoadGoods:js+M+module+PageAdmin:js+M+module+Cookie:js+M+module+ResourceKeeper:js+jquery.jgrowl.min:js+AMessage:js+M+module+FrequencySystemVerify:js+ALogin:js+M+module+QRCode:js+AToolbar:js+ACnzzGaLog:js+ARecruit:js+ALazyLoad^YlZbQw^1502355328.js\");\n" +
            "}\n" +
            "</script>";

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

    @Scheduled(cron = "0 50 23 * * ? ")
    public void pullOnce(){
        logger.info("开始搞事.............");
        Map map = new HashMap();
        map.put("type","0");
        int pageNum = pagePull(pageurl,map);
        for(int i=2;i<=pageNum;i++){
            pageMap.put("page",String.valueOf(i));
            pagePull(pageurl,pageMap);
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
        int pageNum = pagePull(pageurl,map);
        for(int i=2;i<=pageNum;i++){
            pageMap.put("page",String.valueOf(i));
            pagePull(pageurl,pageMap,date);
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
                String body = get(baseurl+href);
                Document document = Jsoup.parseBodyFragment(body);
                Element articleEle = document.getElementsByClass("vc_article").get(0);
                Element title = document.getElementsByClass("vi_con").get(0);
                Article article = new Article();
                article.setContent(articleEle.toString()+expend);
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
        }
    }

    public int pagePull(String pageUrl,Map<String,String> dataMap){
        int pageNum = 0;
        try {
            String json = getForURL(pageUrl, JSON.toJSONString(dataMap));
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
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        return pageNum;
    }

    public void pagePull(String pageUrl,Map<String,String> dataMap,String yesDate){
        try {
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
                }
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
    }
}
