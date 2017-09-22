package com.mydemo.service;

import com.mydemo.dao.ArticleMapper;
import com.mydemo.domain.Article;
import com.mydemo.domain.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleMapper articleDao;

    public Article getArticleById(Long id) {
        return articleDao.selectByPrimaryKey(id);
    }

    public List<Article> getFirst10Article() {
        return articleDao.getFirst10Article();
    }

    public void addArticle(Article article) {
        article.setDate(new Date());
        if (article.getSummary() == null || "".equals(article.getSummary())) {
            if (article.getContent().length() > 20) {
                article.setSummary(article.getContent().substring(0, 20));
            } else {
                article.setSummary(article.getContent().substring(0, article.getContent().length()));
            }
        }
        articleDao.insert(article);
    }

    public void updateArticle(Article article){
        article.setDate(new Date());
        if (article.getSummary() == null || "".equals(article.getSummary())) {
            if (article.getContent().length() > 20) {
                article.setSummary(article.getContent().substring(0, 20));
            } else {
                article.setSummary(article.getContent().substring(0, article.getContent().length()));
            }
        }
        articleDao.updateByPrimaryKey(article);
    }

    public void deleteArticleById(Long id) {
        articleDao.deleteArticleById(id);
    }

    public List<Article> getArticlesBycategoryId(Long categoryId) {
        List<Article> articles = articleDao.getArticlesBycategoryId(categoryId);
        return articles;
    }
}
