package com.mydemo.dao;

import com.mydemo.common.Pager;
import com.mydemo.domain.User;
import com.mydemo.domain.bo.UserBo;
import com.mydemo.domain.vo.UserVo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by admin on 2017/6/12.
 */
public interface UserMapper extends Mapper<User>{

    Long getCount(@Param("bo") UserBo bo, @Param("pager") Pager<UserVo> pager);

    List<UserVo> getList(UserBo bo, @Param("pager") Pager<UserVo> pager);
}
