package com.mydemo.controller;

import com.mydemo.common.Constant;
import com.mydemo.common.Pager;
import com.mydemo.domain.City;
import com.mydemo.domain.User;
import com.mydemo.domain.bo.UserBo;
import com.mydemo.domain.enumtype.CardType;
import com.mydemo.domain.enumtype.CustomerType;
import com.mydemo.domain.vo.UserVo;
import com.mydemo.service.TicketService;
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
 * Created by admin on 2017/6/17.
 */
@Controller
@EnableAutoConfiguration
@RequestMapping("/ticket")
public class TicketController extends BaseController{

    @Autowired
    private TicketService ticketService;

    @RequestMapping("/toList")
    public String toList() {

        return "/user/userlist";
    }

    @RequestMapping("/list")
    @ResponseBody
    public Object list(UserBo bo, Pager<UserVo> page) {
        page = ticketService.getPage(bo, page);
        return page;
    }


    @RequestMapping("/save")
    @ResponseBody
    public Object save(User user) {
        Map<String,String> map = new HashMap<>();
        //验证这些东西延后处理
        boolean flag = ticketService.save(user);
        putStatus(flag,map);
        return map;
    }


    @RequestMapping("/update")
    public Object update(User user) {
        Map<String,String> map = new HashMap<>();
        //验证这些东西延后处理
        boolean flag = ticketService.update(user);
        putStatus(flag,map);
        return map;
    }
}
