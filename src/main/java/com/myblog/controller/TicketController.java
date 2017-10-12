package com.myblog.controller;

import com.common.BaseController;
import com.common.Pager;
import com.domain.Ticket;
import com.domain.bo.TicketBo;
import com.domain.vo.TicketVo;
import com.myblog.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2017/6/17.
 */
@Controller
@EnableAutoConfiguration
@RequestMapping("/ticket")
public class TicketController extends BaseController {

    @Autowired
    private TicketService ticketService;

    @RequestMapping("/toList")
    public String toList() {

        return "/user/userlist";
    }

    @RequestMapping("/list")
    @ResponseBody
    public Object list(TicketBo bo, Pager<TicketVo> page) {
        page = ticketService.getPage(bo, page);
        return page;
    }


    @RequestMapping("/save")
    @ResponseBody
    public Object save(Ticket ticket) {
        Map<String,String> map = new HashMap<>();
        //验证这些东西延后处理
        boolean flag = ticketService.save(ticket);
        putStatus(flag,map);
        return map;
    }


    @RequestMapping("/update")
    public Object update(Ticket ticket) {
        Map<String,String> map = new HashMap<>();
        //验证这些东西延后处理
        boolean flag = ticketService.update(ticket);
        putStatus(flag,map);
        return map;
    }
}
