package com.mydemo.service;

import com.mydemo.common.Pager;
import com.mydemo.dao.UserMapper;
import com.mydemo.domain.User;
import com.mydemo.domain.bo.UserBo;
import com.mydemo.domain.vo.UserVo;
import com.mydemo.service.BaseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2017/6/12.
 */
@Service
public class UserService extends BaseService<User>{

    @Autowired
    private UserMapper userMapper;


    public Pager<UserVo> getPage(UserBo bo, Pager<UserVo> page) {
        Pager<UserVo> pageInfo = new Pager<>();
        BeanUtils.copyProperties(page,pageInfo);
        Long total = userMapper.getCount(bo,page);
        pageInfo.setTotal(total);
        List<UserVo> list = userMapper.getList(bo,page);
        pageInfo.setRows(list);
        return pageInfo;
    }
}
