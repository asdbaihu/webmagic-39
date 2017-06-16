package com.mydemo.controller;

import com.mydemo.domain.City;
import com.mydemo.domain.bo.CityBo;
import com.mydemo.domain.enumtype.CityType;
import com.mydemo.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        List<Map<String,String>> cityDate = new ArrayList<>();
        if(bo!=null&&bo.getSearch()!=null&&!bo.getSearch().isEmpty()){
            bo.setCityType(CityType.COUNTRY);
            List<City> list = cityService.getSelect2List(bo);
            for(City city : list){
                Map<String,String> map = new HashMap<>();
                map.put("id",String.valueOf(city.getId()));
                map.put("text",city.getCnName());
                cityDate.add(map);
            }
        }
        return cityDate;
    }


}
