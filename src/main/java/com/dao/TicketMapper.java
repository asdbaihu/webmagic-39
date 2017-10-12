package com.dao;

import com.common.MyMapper;
import com.common.Pager;
import com.domain.Ticket;
import com.domain.bo.TicketBo;
import com.domain.vo.TicketVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by admin on 2017/6/14.
 */
public interface TicketMapper extends MyMapper<Ticket> {

    Long getCount(@Param("bo") TicketBo bo);

    List<TicketVo> getList(@Param("bo") TicketBo bo, @Param("pager") Pager<TicketVo> pager);
}
