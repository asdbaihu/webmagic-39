package com.mydemo.dao;

import com.mydemo.common.MyMapper;
import com.mydemo.common.Pager;
import com.mydemo.domain.ContactPersion;
import com.mydemo.domain.bo.CityBo;
import com.mydemo.domain.bo.ContactPersionBo;
import com.mydemo.domain.vo.CityVo;
import com.mydemo.domain.vo.ContactPersionVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by admin on 2017/6/14.
 */
public interface ContactPersionMapper extends MyMapper<ContactPersion> {

    Long getCount(@Param("bo") ContactPersionBo bo);

    List<ContactPersionVo> getList(@Param("bo") ContactPersionBo bo, @Param("pager") Pager<ContactPersionVo> pager);

    int deleteByIds(List<Long> ids);
}
