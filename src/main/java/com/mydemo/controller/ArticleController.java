package com.mydemo.controller;

import com.mydemo.common.Constant;
import com.mydemo.domain.Article;
import com.mydemo.domain.Category;
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

    @RequestMapping("/")
    public String index(ModelMap model) {
        List<Article> articles = articleService.getFirst10Article();
        model.put("articles", articles);
        return "admin/index";
    }

    @RequestMapping("/column")
    public String column(ModelMap model,Long id) {
        List<Article> articles = articleService.getArticlesBycategoryId(id);
        model.put("articles", articles);
        return "views/index";
    }

    @RequestMapping("/detail")
    public String detail(Long id, ModelMap model) {
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

    @RequestMapping("/toUpdate")
    public String toUpdate(ModelMap model,Long id) {
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
        return "admin/index";
    }

    @RequestMapping(value = "/update")
    public Object update(Article article){
        articleService.updateArticle(article);
        return "admin/index";
    }

    @RequestMapping(value="/delete")
    public Object delete(Long id) {
        articleService.deleteArticleById(id);
        return "admin/index";
    }
}
