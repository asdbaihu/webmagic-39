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
import org.tautua.markdownpapers.Markdown;
import org.tautua.markdownpapers.parser.ParseException;

import javax.annotation.Resource;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

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
        return "views/index";
    }

    @RequestMapping("/column")
    public String column(ModelMap model,Long id) {
        List<Article> articles = articleService.getArticlesBycategoryId(id);
        model.put("articles", articles);
        return "views/columnPage";
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



    @RequestMapping("/write")
    public String write(ModelMap model) {
        model.put("categories", Constant.CATEGORY_LIST);
        return "admin/write";
    }

    @RequestMapping(value = "/sang/write", method = RequestMethod.POST)
    public String write(Article article) {
        if (article.getId() == 0l) {
            articleService.writeBlog(article);
        } else {
            articleService.updateBlog(article);
        }
        return "redirect:/sang";
    }

    @RequestMapping("/sang/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        articleService.deleteArticleById(id);
        return "redirect:/sang";
    }

    @RequestMapping("/sang/update/{id}")
    public String update(@PathVariable("id") Long id, Model model) {
        Article article = articleService.getArticleById(id);
        model.addAttribute("article", article);
        return "admin/write";
    }
}
