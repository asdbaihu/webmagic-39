package com.dao;

import com.common.MyMapper;
import com.common.Pager;
import com.domain.City;
import com.domain.bo.CityBo;
import com.domain.vo.CityVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by admin on 2017/6/14.
 */
public interface CityMapper extends MyMapper<City> {

    List<City> getSelect2List(@Param("bo") CityBo bo);

    Long getCount(@Param("bo") CityBo bo);

    List<CityVo> getList(@Param("bo") CityBo bo, @Param("pager") Pager<CityVo> pager);

    int deleteByIds(List<Long>ids);
}
