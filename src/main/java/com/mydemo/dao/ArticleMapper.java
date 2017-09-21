package com.mydemo.dao;

import com.mydemo.common.MyMapper;
import com.mydemo.domain.Article;
import com.mydemo.domain.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleMapper extends MyMapper<Article> {
    public Article getArticleById(@Param("id") Long id);

    public List<Article> getFirst10Article();

    public List<Article> getArticlesByCategoryName(Long categoryId);

    public List<Category> getCategories();

    public void writeBlog(Article article);

    public Long getCategoryIdByName(String name);

    public void deleteArticleById(Long id);

    public void updateArticleById(Article article);

    public Category getCategoryById(Long id);
}
