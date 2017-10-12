package com.myblog.service;

import com.common.Pager;
import com.dao.TicketCountMoneyMapper;
import com.dao.TicketMapper;
import com.domain.Ticket;
import com.domain.bo.TicketBo;
import com.domain.vo.TicketVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by admin on 2017/6/17.
 */
@Service
public class TicketService {

    @Autowired
    private TicketMapper ticketMapper;

    @Autowired
    private TicketCountMoneyMapper ticketCountMoneyMapper;


    @Transactional
    public Boolean save(Ticket ticket){
        return ticketMapper.insert(ticket)>0?true:false;
    }

    @Transactional
    public Boolean update(Ticket ticket){
        return ticketMapper.updateByPrimaryKey(ticket)>0?true:false;
    }

    public Pager<TicketVo> getPage(TicketBo bo, Pager<TicketVo> page) {
        Pager<TicketVo> pageInfo = new Pager<>();
        BeanUtils.copyProperties(page,pageInfo);
        Long total = ticketMapper.getCount(bo);
        pageInfo.setTotal(total);
        List<TicketVo> list = ticketMapper.getList(bo,page);
        pageInfo.setRows(list);
        return pageInfo;
    }
}
