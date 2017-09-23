package com.mydemo.controller;

import com.mydemo.common.Constant;
import com.mydemo.common.Pager;
import com.mydemo.domain.City;
import com.mydemo.domain.User;
import com.mydemo.domain.bo.ArticleBo;
import com.mydemo.domain.bo.CityBo;
import com.mydemo.domain.bo.UserBo;
import com.mydemo.domain.enumtype.CardType;
import com.mydemo.domain.enumtype.CityType;
import com.mydemo.domain.enumtype.CustomerType;
import com.mydemo.domain.vo.ArticleVo;
import com.mydemo.domain.vo.UserVo;
import com.mydemo.service.ArticleService;
import com.mydemo.service.CityService;
import com.mydemo.service.UserService;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by admin on 2017/6/12.
 */
@Controller
@EnableAutoConfiguration
@RequestMapping("/user")
public class UserController extends BaseController{

    @Autowired
    private UserService userService;
    @Autowired
    private ArticleService articleService;

    @RequestMapping("/toList")
    public String toList() {
        return "/user/userlist";
    }

    @RequestMapping(value="/list")
    @ResponseBody
    public Object list(UserBo bo, Pager<UserVo> page) {
        page = userService.getPage(bo, page);
        return page;
    }

    @RequestMapping("/toAdd")
    public String toAdd(ModelMap modelMap) {
        modelMap.put("cardTypes", CardType.values());
        modelMap.put("customerTypes", CustomerType.values());
        return "/user/add";
    }

    @RequestMapping("/save")
    @ResponseBody
    public Object save(User user) {
        Map<String,String> map = new HashMap<>();
        //验证这些东西延后处理
        boolean flag = userService.save(user);
        putStatus(flag,map);
        return map;
    }

    @RequestMapping("/toEdit")
    public String toEdit(ModelMap modelMap,Long id) {
        UserVo user = (UserVo) userService.getById(id);
        City city = Constant.CITY_MAP.get(user.getCity());
        if(city!=null){
            user.setCityName(city.getCnName());
        }
        modelMap.put("cardTypes", CardType.values());
        modelMap.put("customerTypes", CustomerType.values());
        modelMap.put("user",user);
        return "user/update";
    }

    @RequestMapping("/update")
    public Object update(User user) {
        Map<String,String> map = new HashMap<>();
        //验证这些东西延后处理
        boolean flag = userService.update(user);
        putStatus(flag,map);
        return map;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Object delete(List<Long>ids){
        Map<String,String> map = new HashMap<>();
        if(ids==null||ids.isEmpty()){
            map.put("status","error");
            map.put("message","请选择一个用户删除!!!");
        }
        boolean flag = userService.deleteByIds(ids);
        putStatus(flag,map);
        return map;
    }

    @RequestMapping("/admin")
    public String admin(ModelMap model, ArticleBo bo, Pager<ArticleVo> page) {
        page = articleService.getPage(bo, page);
        model.put("page", page);
        return "admin/index";
    }

    @RequestMapping("/login")
    public String toLogin() {
        return "admin/login";
    }

    @RequestMapping(value = "/home")
    public String doLogin(HttpServletRequest request, User user, ModelMap model) {
        if (userService.login(user.getUserName(), user.getPassword())) {
            request.getSession().setAttribute("user", user);
            model.put("user", user);
            return "redirect:/user/admin";
        } else {
            model.addAttribute("error", "用户名或密码错误");
            return "admin/login";
        }
    }
}
