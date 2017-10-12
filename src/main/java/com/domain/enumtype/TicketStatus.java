package com.domain.enumtype;

/**
 * Created by admin on 2017/6/13.
 */
public enum TicketStatus {

    START,STARTED;


    public String getDescription(){
        String description = null;
        switch (this){
            case START:
                description = "未发车";
                break;
            case STARTED:
                description = "已发车";
                break;
        }
        return description;
    }
}
