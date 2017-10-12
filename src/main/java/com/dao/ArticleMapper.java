package com.dao;

import com.common.MyMapper;
import com.common.Pager;
import com.domain.Article;
import com.domain.bo.ArticleBo;
import com.domain.vo.ArticleVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleMapper extends MyMapper<Article> {

    Long getCount(@Param("bo") ArticleBo bo);

    List<ArticleVo> getList(@Param("bo") ArticleBo bo, @Param("pager") Pager<ArticleVo> pager);

    List<Article> getArticlesBycategoryId(@Param("categoryId") Long categoryId);

    List<Article> getArticles(@Param("title")String title,@Param("categoryId")Long categoryId);
}
