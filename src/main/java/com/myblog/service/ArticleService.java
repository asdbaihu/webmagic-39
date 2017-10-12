package com.myblog.service;

import com.common.Pager;
import com.dao.ArticleMapper;
import com.domain.Article;
import com.domain.bo.ArticleBo;
import com.domain.vo.ArticleVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleMapper articleDao;

    public Article getArticleById(Long id) {
        return articleDao.selectByPrimaryKey(id);
    }

    public Pager<ArticleVo> getPage(ArticleBo bo, Pager<ArticleVo> page) {
        Pager<ArticleVo> pageInfo = new Pager<>();
        BeanUtils.copyProperties(page,pageInfo);
        Long total = articleDao.getCount(bo);
        pageInfo.setTotal(total);
        List<ArticleVo> list = articleDao.getList(bo,page);
        pageInfo.setRows(list);
        return pageInfo;
    }

    public void addArticle(Article article) {
        article.setCreateTime(new Date());
        article.setUpdateTime(new Date());
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
        Article old = articleDao.selectByPrimaryKey(article.getArticleId());

        article.setCommentCount(old.getCommentCount());
        article.setViewCount(old.getViewCount());
        article.setUpdateTime(new Date());
        if (article.getSummary() == null || "".equals(article.getSummary())) {
            if (article.getContent().length() > 20) {
                article.setSummary(article.getContent().substring(0, 20));
            } else {
                article.setSummary(article.getContent().substring(0, article.getContent().length()));
            }
        }
        articleDao.updateByPrimaryKeySelective(article);
    }

    public void deleteArticleById(Long id) {
        articleDao.deleteByPrimaryKey(id);
    }

    public List<Article> getArticlesBycategoryId(Long categoryId) {
        List<Article> articles = articleDao.getArticlesBycategoryId(categoryId);
        return articles;
    }

    public List<Article> getArticles(String title,Long categoryId){
        List<Article> articles = articleDao.getArticles(title,categoryId);
        return articles;
    }
}
