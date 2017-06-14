package com.mydemo.service;

import com.mydemo.common.Constant;
import com.mydemo.common.Pager;
import com.mydemo.dao.UserMapper;
import com.mydemo.domain.User;
import com.mydemo.domain.bo.UserBo;
import com.mydemo.domain.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/6/12.
 */
@Service
public class UserService{

    @Autowired
    private UserMapper userMapper;


    public Pager<UserVo> getPage(UserBo bo, Pager<UserVo> page) {
        Pager<UserVo> pageInfo = new Pager<>();
        BeanUtils.copyProperties(page,pageInfo);
        Long total = userMapper.getCount(bo);
        pageInfo.setTotal(total);
        List<UserVo> list = userMapper.getList(bo,page);
        for(UserVo userVo :list){
            userVo.setCardTypeName(userVo.getCardType().getDescription());
            userVo.setCityName(Constant.CITY_MAP.get(userVo.getCity()).getCnName());
            userVo.setCustomerTypeName(userVo.getCustomerType().getDescription());
        }

        pageInfo.setRows(list);
        return pageInfo;
    }
}
