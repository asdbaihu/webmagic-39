package com.dao;

import com.common.MyMapper;
import com.common.Pager;
import com.domain.User;
import com.domain.bo.UserBo;
import com.domain.vo.UserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by admin on 2017/6/12.
 */
public interface UserMapper extends MyMapper<User> {

    Long getCount(@Param("bo") UserBo bo);

    List<UserVo> getList(@Param("bo") UserBo bo, @Param("pager") Pager<UserVo> pager);

    Long deleteByIds(@Param("ids") List<Long> ids);

    User getUser(@Param("username") String username, @Param("password") String password);
}
