package com.mydemo.common;

import com.mydemo.domain.Category;
import com.mydemo.domain.City;
import com.mydemo.domain.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/6/14.
 */
@Component
public class Constant {

    public static List<User> USER_LIST = new ArrayList<>();

    public static Map<Long,User> USER_MAP = new HashMap<>();

    public static List<City> CITY_LIST = new ArrayList<>();

    public static Map<Long,City> CITY_MAP = new HashMap<>();

    public static Map<String,City> COUNTRY_MAP_TWO = new HashMap<>();

    public static List<Category> CATEGORY_LIST = new ArrayList<>();

    public static Map<Long,Category> CATEGORY_MAP = new HashMap<>();
}
