package com.dao;

import com.common.MyMapper;
import com.common.Pager;
import com.domain.ContactPersion;
import com.domain.bo.ContactPersionBo;
import com.domain.vo.ContactPersionVo;
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
