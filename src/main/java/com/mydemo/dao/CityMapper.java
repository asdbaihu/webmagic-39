package com.mydemo.dao;

import com.mydemo.common.MyMapper;
import com.mydemo.domain.City;
import com.mydemo.domain.bo.CityBo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by admin on 2017/6/14.
 */
public interface CityMapper extends MyMapper<City> {

    List<City> getSelect2List(@Param("bo") CityBo bo);
}
