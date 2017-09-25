package com.mydemo.service;

import com.mydemo.common.Constant;
import com.mydemo.common.Pager;
import com.mydemo.dao.UserMapper;
import com.mydemo.domain.City;
import com.mydemo.domain.User;
import com.mydemo.domain.bo.UserBo;
import com.mydemo.domain.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

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


    public Pager<UserVo> getPage(@RequestParam()UserBo bo, @RequestParam()Pager<UserVo> page) {
        Pager<UserVo> pageInfo = new Pager<>();
        BeanUtils.copyProperties(page,pageInfo);
        Long total = userMapper.getCount(bo);
        pageInfo.setTotal(total);
        List<UserVo> list = userMapper.getList(bo,page);
        for(UserVo userVo :list){
            userVo.setCardTypeName(userVo.getCardType().getDescription());
            City city = Constant.CITY_MAP.get(userVo.getCity());
            if (city!=null&&city.getCityId()>0) {
                userVo.setCityName(city.getCnName());
            }
            userVo.setCustomerTypeName(userVo.getCustomerType().getDescription());
        }

        pageInfo.setRows(list);
        return pageInfo;
    }

    @Transactional
    public Boolean deleteByIds(List<Long> ids){
        return userMapper.deleteByIds(ids)>0?true:false;
    }

    @Transactional
    public Boolean save(User user){
        return userMapper.insert(user)>0?true:false;
    }

    @Transactional
    public Boolean update(User user){
        return userMapper.updateByPrimaryKey(user)>0?true:false;
    }

    public User getById(Long id){
        return userMapper.selectByPrimaryKey(id);
    }

    public boolean login(String username, String password) {
        User user = userMapper.getUser(username, password);
        if (user == null) {
            return false;
        }else{
            return true;
        }
    }
}
