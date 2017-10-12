package com.domain.enumtype;

/**
 * Created by admin on 2017/6/13.
 */
public enum TicketType {

    NORMALSEAT,SPECIALSEAT,FIRSTCLASSSEAT,SECONDCLASSSEAT,ADVANCEDSOFTSLEEPER,SOFTSLEEPER,HARDSLEEPER,SOFTSEAT,HARDSEAT,NOSEAT,OTHER;

    public String getDescription(){
        String description = null;
        switch (this){
            case NORMALSEAT:
                description = "商务座";
                break;
            case SPECIALSEAT:
                description = "特等座";
                break;
            case FIRSTCLASSSEAT:
                description = "一等座";
                break;
            case SECONDCLASSSEAT:
                description = "二等座";
                break;
            case ADVANCEDSOFTSLEEPER:
                description = "高级软卧";
                break;
            case SOFTSLEEPER:
                description = "软卧";
                break;
            case HARDSLEEPER:
                description = "硬卧";
                break;
            case SOFTSEAT:
                description = "软座";
                break;
            case HARDSEAT:
                description = "硬座";
                break;
            case NOSEAT:
                description = "无座";
                break;
            case OTHER:
                description = "无座";
                break;
        }
        return description;
    }
}
