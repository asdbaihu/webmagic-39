package com.mydemo.controller;

import com.mydemo.common.Pager;
import com.mydemo.domain.bo.UserBo;
import com.mydemo.domain.vo.UserVo;
import com.mydemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by admin on 2017/6/12.
 */
@RestController
@EnableAutoConfiguration
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping("/list")
    @ResponseBody
    public Object list(UserBo bo, Pager<UserVo> page){
        page = userService.getPage(bo,page);
        return page;
    }
}
