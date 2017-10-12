package com.myblog.controller;

import com.common.BaseController;
import com.common.Constant;
import com.domain.Article;
import com.myblog.service.ArticleService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

@Controller
@EnableAutoConfiguration
@RequestMapping("/article")
public class ArticleController extends BaseController {

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
//        Markdown markdown = new Markdown();
//        try {
//            StringWriter out = new StringWriter();
//            markdown.transform(new StringReader(article.getContent()), out);
//            out.flush();
//            article.setContent(out.toString());
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
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
