package com.mydemo.controller;

import com.mydemo.domain.bo.CityBo;
import com.mydemo.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by admin on 2017/6/15.
 */
@Controller
@RequestMapping("/city")
public class CityController {

    @Autowired
    private CityService cityService;

    @RequestMapping("/citySelect2")
    @ResponseBody
    public Object citySelect2(CityBo bo){
        if(bo==null){
            return null;
        }
        return cityService.getAllList(bo);
    }
}
