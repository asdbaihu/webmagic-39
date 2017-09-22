package com.mydemo.dao;

import com.mydemo.common.MyMapper;
import com.mydemo.domain.Article;
import com.mydemo.domain.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleMapper extends MyMapper<Article> {
    Article getArticleById(@Param("id") Long id);

    List<Article> getFirst10Article();

    List<Article> getArticlesBycategoryId(Long categoryId);

    List<Category> getCategories();

    void writeBlog(Article article);

    Long getCategoryIdByName(String name);

    void deleteArticleById(Long id);

    void updateArticleById(Article article);

    Category getCategoryById(Long id);
}
