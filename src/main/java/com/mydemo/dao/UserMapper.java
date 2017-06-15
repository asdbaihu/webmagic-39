package com.mydemo.dao;

import com.mydemo.common.MyMapper;
import com.mydemo.common.Pager;
import com.mydemo.domain.User;
import com.mydemo.domain.bo.UserBo;
import com.mydemo.domain.vo.UserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by admin on 2017/6/12.
 */
public interface UserMapper extends MyMapper<User> {

    Long getCount(@Param("bo") UserBo bo);

    List<UserVo> getList(@Param("bo") UserBo bo, @Param("pager") Pager<UserVo> pager);

    Long deleteByIds(@Param("ids") List<Long> ids);
}
