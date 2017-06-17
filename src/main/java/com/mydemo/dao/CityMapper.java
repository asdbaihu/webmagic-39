package com.mydemo.dao;

import com.mydemo.common.MyMapper;
import com.mydemo.common.Pager;
import com.mydemo.domain.City;
import com.mydemo.domain.bo.CityBo;
import com.mydemo.domain.bo.UserBo;
import com.mydemo.domain.vo.CityVo;
import com.mydemo.domain.vo.UserVo;
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
