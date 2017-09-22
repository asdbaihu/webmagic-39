package com.mydemo.service;

import com.mydemo.common.Constant;
import com.mydemo.dao.CategoryMapper;
import com.mydemo.dao.CityMapper;
import com.mydemo.dao.UserMapper;
import com.mydemo.domain.Category;
import com.mydemo.domain.City;
import com.mydemo.domain.User;
import com.mydemo.domain.enumtype.CityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/6/15.
 */
@Service
public class InitService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CityMapper cityMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    public void init(){

        List<User> userList = userMapper.selectAll();
        Constant.USER_LIST = userList;
        Map<Long,User> userMap = new HashMap<>();
        for (User user : userList){
            userMap.put(user.getId(),user);
        }
        Constant.USER_MAP = userMap;

        List<City> cityList = cityMapper.selectAll();
        Constant.CITY_LIST = cityList;
        Map<Long,City> cityMap = new HashMap<>();
        Map<String,City> countryMapTwo = new HashMap<>();
        for(City city : cityList){
            cityMap.put(city.getId(),city);
            if(CityType.COUNTRY.equals(city.getCityType())){
                countryMapTwo.put(city.getCountryCode(),city);
            }
        }
        Constant.COUNTRY_MAP_TWO = countryMapTwo;
        Constant.CITY_MAP = cityMap;

        List<Category> categoryList = categoryMapper.selectAll();
        Constant.CATEGORY_LIST = categoryList;
        Map<Long,Category> categoryMap = new HashMap<>();
        for(Category category : categoryList){
            categoryMap.put(category.getId(),category);
        }
        Constant.CATEGORY_MAP = categoryMap;
    }
}
