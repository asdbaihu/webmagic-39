package com.mydemo.service;

import com.mydemo.dao.CityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by admin on 2017/6/14.
 */
@Service
public class CityService{

    @Autowired
    private CityMapper cityMapper;
}
