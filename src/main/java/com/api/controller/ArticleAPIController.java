package com.api.controller;

import com.alibaba.fastjson.JSON;
import com.common.BaseController;
import com.common.Pager;
import com.domain.Article;
import com.domain.bo.ArticleBo;
import com.domain.vo.ArticleVo;
import com.myblog.service.ArticleService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tautua.markdownpapers.Markdown;
import org.tautua.markdownpapers.parser.ParseException;

import javax.annotation.Resource;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@EnableAutoConfiguration
@RequestMapping("/api/article")
public class ArticleAPIController extends BaseController{

    @Resource
    private ArticleService articleService;

    @RequestMapping(value = "/column")
    @ResponseBody
    public Object column(ArticleBo bo, Pager<ArticleVo> pager) {
        List<Article> articles = articleService.getArticlesBycategoryId(bo.getCategoryId());
        Map<String,Object> map = new HashMap<>();
        map.put("code",200);
        map.put("articleLists", JSON.toJSON(articles));
        map.put("hasNext",false);
        map.put("hasPrev",false);
        return map;
    }

    @RequestMapping("/detail")
    @ResponseBody
    public Object detail(Long id) {
        Article article = articleService.getArticleById(id);
        Markdown markdown = new Markdown();
        try {
            StringWriter out = new StringWriter();
            markdown.transform(new StringReader(article.getContent()), out);
            out.flush();
            article.setContent(out.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Map<String,Object> map = new HashMap<>();
        map.put("code",200);
        map.put("oneArticle", JSON.toJSON(article));
        return map;
    }
}
