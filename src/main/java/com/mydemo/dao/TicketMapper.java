package com.mydemo.dao;

import com.mydemo.common.MyMapper;
import com.mydemo.common.Pager;
import com.mydemo.domain.Ticket;
import com.mydemo.domain.bo.TicketBo;
import com.mydemo.domain.bo.UserBo;
import com.mydemo.domain.vo.TicketVo;
import com.mydemo.domain.vo.UserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by admin on 2017/6/14.
 */
public interface TicketMapper extends MyMapper<Ticket> {

    Long getCount(@Param("bo") TicketBo bo);

    List<TicketVo> getList(@Param("bo") TicketBo bo, @Param("pager") Pager<TicketVo> pager);
}
