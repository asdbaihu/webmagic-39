package com.mydemo.controller;

import com.mydemo.common.Constant;
import com.mydemo.common.Pager;
import com.mydemo.domain.City;
import com.mydemo.domain.ContactPersion;
import com.mydemo.domain.User;
import com.mydemo.domain.bo.ContactPersionBo;
import com.mydemo.domain.bo.UserBo;
import com.mydemo.domain.enumtype.CardType;
import com.mydemo.domain.enumtype.CustomerType;
import com.mydemo.domain.vo.ContactPersionVo;
import com.mydemo.service.ContactPersionService;
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
@RequestMapping("/contactPersion")
public class ContactPersionController extends BaseController{

    @Autowired
    private ContactPersionService contactPersionService;

    @RequestMapping("/toList")
    public String toList() {

        return "/contactPersion/userlist";
    }

    @RequestMapping("/list")
    @ResponseBody
    public Object list(ContactPersionBo bo, Pager<ContactPersionVo> page) {
        page = contactPersionService.getPage(bo, page);
        return page;
    }

    @RequestMapping("/toAdd")
    public String toAdd(ModelMap modelMap) {
        modelMap.put("cardTypes", CardType.values());
        modelMap.put("customerTypes", CustomerType.values());
        return "/contactPersion/toAdd";
    }

    @RequestMapping("/save")
    @ResponseBody
    public Object save(ContactPersion contactPersion) {
        Map<String,String> map = new HashMap<>();
        //验证这些东西延后处理
        boolean flag = contactPersionService.save(contactPersion);
        putStatus(flag,map);
        return map;
    }

    @RequestMapping("/toEdit")
    public String toEdit(ModelMap modelMap, Long id) {
        ContactPersion contactPersion =contactPersionService.getById(id);
        modelMap.put("user",contactPersion);
        return "contactPersion/toEdit";
    }

    @RequestMapping("/update")
    public Object update(ContactPersion contactPersion) {
        Map<String,String> map = new HashMap<>();
        //验证这些东西延后处理
        boolean flag = contactPersionService.update(contactPersion);
        putStatus(flag,map);
        return map;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Object delete(List<Long> ids){
        Map<String,String> map = new HashMap<>();
        if(ids==null||ids.isEmpty()){
            map.put("status","error");
            map.put("message","请选择一个用户删除!!!");
        }
        boolean flag = contactPersionService.deleteByIds(ids);
        putStatus(flag,map);
        return map;
    }
}
