package com.domain;

import com.domain.enumtype.TicketType;

import java.io.Serializable;

/**
 * Created by admin on 2017/6/13.
 */
public class TicketCountMoney implements Serializable{


    private static final long serialVersionUID = 3927904062928937083L;

    private Long ticketId;

    private Integer total;

    private Integer remain;

    private Double money;

    private TicketType ticketType;

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getRemain() {
        return remain;
    }

    public void setRemain(Integer remain) {
        this.remain = remain;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

}
