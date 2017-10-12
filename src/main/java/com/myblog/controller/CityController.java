package com.myblog.controller;

import com.common.BaseController;
import com.common.Constant;
import com.common.Pager;
import com.domain.City;
import com.domain.bo.CityBo;
import com.domain.enumtype.CityType;
import com.domain.vo.CityVo;
import com.myblog.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
@EnableAutoConfiguration
@RequestMapping("/city")
public class CityController extends BaseController {

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
                map.put("id",String.valueOf(city.getCityId()));
                map.put("text",city.getCnName());
                cityDate.add(map);
            }
        }
        return cityDate;
    }

    @RequestMapping("/toList")
    public String toList(){

        return "/city/citylist";
    }


    @RequestMapping("/list")
    @ResponseBody
    public Object list(CityBo bo, Pager<CityVo> page) {
        page = cityService.getPage(bo, page);
        return page;
    }

    @RequestMapping("/toAdd")
    public String toAdd(ModelMap modelMap) {

        return "/city/toAdd";
    }

    @RequestMapping("/save")
    @ResponseBody
    public Object save(City city){
        Map<String,String> map = new HashMap<>();
        //验证这些东西延后处理
        boolean flag = cityService.save(city);
        putStatus(flag,map);
        return map;
    }

    @RequestMapping("/toEdit")
    public String toEdit(ModelMap modelMap,Long id) {
        City city = Constant.CITY_MAP.get(id);
        modelMap.put("city",city);
        return "city/toEdit";
    }

    @RequestMapping("/update")
    public Object update(City city) {
        Map<String,String> map = new HashMap<>();
        //验证这些东西延后处理
        boolean flag = cityService.update(city);
        putStatus(flag,map);
        return map;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Object delete(List<Long>ids){
        Map<String,String> map = new HashMap<>();
        if(ids==null||ids.isEmpty()){
            map.put("status","error");
            map.put("message","请选择一个城市删除!!!");
        }
        boolean flag = cityService.deleteByIds(ids);
        putStatus(flag,map);
        return map;
    }
}
