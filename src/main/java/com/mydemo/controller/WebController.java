package com.mydemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Created by admin on 2017/6/13.
 */
@Controller
public class WebController {

    @RequestMapping("/")
    public String web(){
        return "index";
    }
}
