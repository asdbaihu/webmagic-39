package com.myblog.service;

import com.common.Pager;
import com.dao.ContactPersionMapper;
import com.domain.ContactPersion;
import com.domain.bo.ContactPersionBo;
import com.domain.vo.ContactPersionVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by admin on 2017/6/17.
 */
@Service
public class ContactPersionService {

    @Autowired
    private ContactPersionMapper ContactPersionMapper;


    public Pager<ContactPersionVo> getPage(ContactPersionBo bo, Pager<ContactPersionVo> page) {
        Pager<ContactPersionVo> pageInfo = new Pager<>();
        BeanUtils.copyProperties(page,pageInfo);
        Long total = ContactPersionMapper.getCount(bo);
        pageInfo.setTotal(total);
        List<ContactPersionVo> list = ContactPersionMapper.getList(bo,page);
        pageInfo.setRows(list);
        return pageInfo;
    }

    @Transactional
    public Boolean deleteByIds(List<Long> ids){
        return ContactPersionMapper.deleteByIds(ids)>0?true:false;
    }

    @Transactional
    public Boolean save(ContactPersion contactPersion){
        return ContactPersionMapper.insert(contactPersion)>0?true:false;
    }

    @Transactional
    public Boolean update(ContactPersion contactPersion){
        return ContactPersionMapper.updateByPrimaryKey(contactPersion)>0?true:false;
    }

    public ContactPersion getById(Long id){
        return ContactPersionMapper.selectByPrimaryKey(id);
    }
}
