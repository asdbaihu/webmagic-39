package com.mydemo.service;

import com.github.pagehelper.PageHelper;
import com.mydemo.dao.CityMapper;
import com.mydemo.domain.City;
import com.mydemo.domain.bo.CityBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by admin on 2017/6/14.
 */
@Service
public class CityService{

    @Autowired
    private CityMapper cityMapper;


    public List<City> getAll() {
        return cityMapper.selectAll();
    }

    public City getById(Integer id) {
        return cityMapper.selectByPrimaryKey(id);
    }

    @Transactional
    public void deleteById(Integer id) {
        cityMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void save(City city) {
        if (city.getId() != null) {
            cityMapper.updateByPrimaryKey(city);
        } else {
            cityMapper.insert(city);
        }
    }

    public List<City> getAllList(CityBo bo){
        return cityMapper.getAllList(bo);
    }
}
