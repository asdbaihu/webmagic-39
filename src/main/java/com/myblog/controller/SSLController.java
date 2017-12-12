package com.myblog.controller;

import com.common.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/.well-known")
public class SSLController extends BaseController {

    @RequestMapping("/acme-challenge/{id}")
    @ResponseBody
    public String acme(@PathVariable("id") String id){
        System.out.println("试试看"+id);
        return "NsO1Owe78mtNnkuHizxmyqK7_86K7GBKY17G-kcfSGs.0_Pe-xGjTW4bQpCcY5o7cWY-N994Gm4z9tAEDkH5Fg0";
    }
}
