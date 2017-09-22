package com.mydemo.dao;

import com.mydemo.common.MyMapper;
import com.mydemo.domain.Article;
import com.mydemo.domain.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleMapper extends MyMapper<Article> {

    List<Article> getFirst10Article();

    List<Article> getArticlesBycategoryId(Long categoryId);

    void deleteArticleById(Long id);

}
