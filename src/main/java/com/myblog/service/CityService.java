package com.myblog.service;

import com.common.Pager;
import com.dao.CityMapper;
import com.domain.City;
import com.domain.bo.CityBo;
import com.domain.vo.CityVo;
import org.springframework.beans.BeanUtils;
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


    @Transactional
    public boolean deleteByIds(List<Long> ids) {
        return cityMapper.deleteByIds(ids)>0?true:false;
    }

    @Transactional
    public boolean save(City city) {
        return cityMapper.insert(city)>0?true:false;
    }

    @Transactional
    public boolean update(City city){
        return cityMapper.updateByPrimaryKey(city)>0?true:false;
    }

    public List<City> getSelect2List(CityBo bo){
        return cityMapper.getSelect2List(bo);
    }

    public Pager<CityVo> getPage(CityBo bo, Pager<CityVo> page) {
        Pager<CityVo> pageInfo = new Pager<>();
        BeanUtils.copyProperties(page,pageInfo);
        Long total = cityMapper.getCount(bo);
        pageInfo.setTotal(total);
        List<CityVo> list = cityMapper.getList(bo,page);
        pageInfo.setRows(list);
        return pageInfo;
    }
}
