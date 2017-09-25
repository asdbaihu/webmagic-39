package com.mydemo.domain;

import com.mydemo.domain.enumtype.TicketStatus;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by admin on 2017/6/13.
 */
public class Ticket implements Serializable {

    private static final long serialVersionUID = 9091733544850898810L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketId;
    //车次
    private String trainNumber;
    //始发站
    private Long originatStation;
    //终点站
    private Long terminalStation;
    //出发时间
    private Date departureTime;
    //到站时间
    private Date arrivalTime;
    //状态
    private TicketStatus ticketStatus;
    //备注
    private String remark;

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public Long getTerminalStation() {
        return terminalStation;
    }

    public void setTerminalStation(Long terminalStation) {
        this.terminalStation = terminalStation;
    }

    public Long getOriginatStation() {
        return originatStation;
    }

    public void setOriginatStation(Long originatStation) {
        this.originatStation = originatStation;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public TicketStatus getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(TicketStatus ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
