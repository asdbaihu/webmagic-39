package com.mydemo.job.tripyum;

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
import java.util.List;

@Component
public class TripyumTaskJobs extends BaseTaskJobs {

    private final static Logger logger = LoggerFactory.getLogger(TripyumTaskJobs.class);
    private final static String baseurl = "http://www.tripyum.com/";

    @Resource
    private ArticleService articleService;


    @Scheduled(cron = "0 48 23 * * ? ")
    public void pullOnce() {
        logger.info("开始搞事.............");
        for (int i = 1; i <= 29; i++) {
            pagePull(baseurl + "Worldfood/index/colum_id/0/p/" + i + ".html");
        }
        logger.info("搞事结束.............");
    }

//    @Scheduled(cron = "0 0 3 */3 * ? ")
    public void pullThreeDay() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -3);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleDateFormat.format(cal.getTime());
        logger.info("开始搞事.............");
        for (int i = 1; i <= 29; i++) {
            pagePull(baseurl + "Worldfood/index/colum_id/0/p/" + i + ".html", date);
        }
        logger.info("搞事结束.............");
    }


    public void pagePull(String pageUrl) {
        try {
            String body = get(pageUrl);
            Document doc = Jsoup.parseBodyFragment(body);
            Elements elements = doc.getElementsByClass("c-preview--full");
            for (Element element : elements) {
                Element span = element.getElementsByClass("browse").get(0);
                int value = Integer.valueOf(span.text());
                if (value >= 100) {
                    save(element);
                }
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
    }

    private void save(Element element) throws Exception {
        Element lnkElement = element.getElementsByClass("preview-image").get(0).child(0);
        String href = lnkElement.attr("href");
        String body = get(baseurl+href);
        Document document = Jsoup.parseBodyFragment(body);
        Element articleEle = document.getElementsByClass("article-content").get(0);
        Element title = document.getElementsByClass("article-title").get(0).child(0);
        Article article = new Article();
        article.setContent(articleEle.toString());
        article.setTitle(title.text());
        article.setCategoryId(3l);
        article.setViewCount(0);
        article.setCommentCount(0);
        List<Article> articleList = articleService.getArticles(title.text(), 3l);
        if (articleList != null && articleList.size() > 0) {
            return;
        }
        articleService.addArticle(article);
    }

    public void pagePull(String pageUrl, String date) {
        try {
            String bodyBase = get(pageUrl);
            Document doc = Jsoup.parseBodyFragment(bodyBase);
            Elements elements = doc.getElementsByClass("c-preview--full");
            for (Element element : elements) {
                String createDate = element.getElementsByClass("postDate").get(0).text();
                if (createDate.compareTo(date) >= 0) {
                    Element span = element.getElementsByClass("browse").get(0);
                    int value = Integer.valueOf(span.text());
                    if (value >= 100) {
                        save(element);
                    }
                }
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
    }

}
