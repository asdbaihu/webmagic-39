package com.mydemo.dao;

import com.mydemo.common.MyMapper;
import com.mydemo.common.Pager;
import com.mydemo.domain.Article;
import com.mydemo.domain.bo.ArticleBo;
import com.mydemo.domain.vo.ArticleVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleMapper extends MyMapper<Article> {

    Long getCount(@Param("bo") ArticleBo bo);

    List<ArticleVo> getList(@Param("bo") ArticleBo bo, @Param("pager") Pager<ArticleVo> pager);

    List<Article> getArticlesBycategoryId(@Param("categoryId") Long categoryId);

}
