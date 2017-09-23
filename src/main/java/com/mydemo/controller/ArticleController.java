package com.mydemo.controller;

import com.mydemo.common.Constant;
import com.mydemo.common.Pager;
import com.mydemo.domain.Article;
import com.mydemo.domain.Category;
import com.mydemo.domain.bo.ArticleBo;
import com.mydemo.domain.bo.CityBo;
import com.mydemo.domain.vo.ArticleVo;
import com.mydemo.domain.vo.CityVo;
import com.mydemo.service.ArticleService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
@RequestMapping("/article")
public class ArticleController extends BaseController{

    @Resource
    private ArticleService articleService;

    @RequestMapping("/column/{id}")
    public String column(ModelMap model,@PathVariable("id") Long id) {
        List<Article> articles = articleService.getArticlesBycategoryId(id);
        model.put("articles", articles);
        return "views/index";
    }

    @RequestMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id, ModelMap model) {
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
        model.put("article", article);
        return "views/detail";
    }

    @RequestMapping("/toUpdate/{id}")
    public String toUpdate(ModelMap model,@PathVariable("id") Long id) {
        model.put("categories", Constant.CATEGORY_LIST);
        model.put("article",articleService.getArticleById(id));
        return "admin/update";
    }

    @RequestMapping("/toAdd")
    public String toAdd(ModelMap model) {
        model.put("categories", Constant.CATEGORY_LIST);
        return "admin/add";
    }

    @RequestMapping(value = "/save")
    public Object save(Article article) {
        articleService.addArticle(article);
        return "redirect:/user/admin/";
    }

    @RequestMapping(value = "/update")
    public Object update(Article article){
        articleService.updateArticle(article);
        return "redirect:/user/admin/";
    }

    @RequestMapping(value="/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        articleService.deleteArticleById(id);
    }
}
