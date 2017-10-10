package com.mydemo.job.itxinwen;

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
import java.util.List;

@Component
public class ItxinwenTaskJobs extends BaseTaskJobs{

    private final static Logger logger = LoggerFactory.getLogger(ItxinwenTaskJobs.class);
    private final static String baseurl ="http://www.ittime.com.cn/";

    @Resource
    private ArticleService articleService;

//    @Scheduled(cron = "0 0 6 * * ? ")
//    @Scheduled(cron = "0 41 17 * * ? ")
    public void pullEveryDay(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE,-5);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String date = simpleDateFormat.format(cal.getTime());
        logger.info("开始搞事.............");
        int pageNum = pagePull(baseurl+"newslist.shtml",date);
        for(int i=2;i<=pageNum;i++){
            pagePull(baseurl+"newslist_"+i+".shtml",date);
        }
        logger.info("搞事结束.............");
    }

    public int pagePull(String pageUrl,String date){
        int pageNum = 0;
        try {
            String body = get(pageUrl);
            if(StringUtils.isEmpty(body)){
                return 0;
            }
            Document doc = Jsoup.parseBodyFragment(body);
            Elements pages = doc.getElementsByClass("page").get(0).children();
            pageNum = Integer.valueOf(pages.get(pages.size()-2).text());
            Elements elements = doc.getElementsByClass("newsList");
            for(Element element : elements) {
                Elements foot = element.getElementsByClass("year");
                String pullDate = foot.get(0).text().trim();
                if (pullDate.compareTo(date)>0) {
                    Element lnkElement = element.child(0).child(0);
                    String href = lnkElement.attr("href");
                    body = get(baseurl+href);
                    Document document = Jsoup.parseBodyFragment(body);
                    Element ele = document.getElementsByClass("left_main").first();
                    Elements children = ele.children();
                    String title = null;
                    StringBuilder content = new StringBuilder();
                    for(Element child : children){
                        if("h2".equalsIgnoreCase(child.nodeName())){
                            title = child.text();
                        }
                        if("p".equalsIgnoreCase(child.nodeName())&&!"toolbp".equals(child.attr("class"))){
                            content.append(child.toString());
                        }
                    }
                    Article article = new Article();
                    String newContent = content.toString().replace("/uploadimage","http://www.ittime.com.cn/uploadimage");
                    article.setContent(newContent);
                    article.setTitle(title);
                    article.setCategoryId(4l);
                    article.setViewCount(0);
                    article.setCommentCount(0);
                    List<Article> articleList = articleService.getArticles(title,4l);
                    if(articleList!=null&&articleList.size()>0){
                        continue;
                    }
                    articleService.addArticle(article);
                }
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        return pageNum;
    }
}
