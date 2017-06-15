package com.mydemo.controller;

import com.mydemo.common.Pager;
import com.mydemo.domain.City;
import com.mydemo.domain.User;
import com.mydemo.domain.bo.CityBo;
import com.mydemo.domain.bo.UserBo;
import com.mydemo.domain.enumtype.CityType;
import com.mydemo.domain.vo.UserVo;
import com.mydemo.service.CityService;
import com.mydemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by admin on 2017/6/12.
 */
@Controller
@EnableAutoConfiguration
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/toList")
    public String toList() {

        return "/user/userlist";
    }

    @RequestMapping("/list")
    @ResponseBody
    public Object list(UserBo bo, Pager<UserVo> page) {
        page = userService.getPage(bo, page);
        return page;
    }

    @RequestMapping("/toAdd")
    public String toAdd() {

        return "/user/toAdd";
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
        User user = userService.getById(id);
        modelMap.put("user",user);
        return "user/toEdit";
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

    private void putStatus(boolean flag,Map<String,String> map){
        if (flag){
            map.put("status","success");
            map.put("message","操作成功");
        }else{
            map.put("status","error");
            map.put("message","删除错误,请联系管理员。");
        }
    }
}
