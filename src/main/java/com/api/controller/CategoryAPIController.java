package com.api.controller;

import com.alibaba.fastjson.JSON;
import com.common.BaseController;
import com.common.Constant;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
@EnableAutoConfiguration
@RequestMapping("/api/category")
public class CategoryAPIController extends BaseController {

    @RequestMapping("/getAll")
    @ResponseBody
    public Object getCategories() {
        return new HashMap<String,Object>(){{put("categories", JSON.toJSON(Constant.CATEGORY_LIST));}};
    }
}