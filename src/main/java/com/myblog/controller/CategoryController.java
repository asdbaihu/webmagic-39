package com.myblog.controller;

import com.common.BaseController;
import com.common.Constant;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@EnableAutoConfiguration
@RequestMapping("/category")
public class CategoryController extends BaseController {

    @RequestMapping("/getAll")
    public String getCategories(ModelMap model) {
        model.put("categoryList", Constant.CATEGORY_LIST);
        return "views/comm/category";
    }
}
