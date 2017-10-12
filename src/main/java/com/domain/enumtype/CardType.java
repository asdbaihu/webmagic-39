package com.domain.enumtype;

/**
 * Created by admin on 2017/6/13.
 */
public enum CardType {


    IDCARD,HKMTRAVELLING,TWTRAVELLING,PASSPORT;


    public String getDescription(){
        String description = null;
        switch (this){
            case IDCARD:
                description = "二代身份证";
                break;
            case HKMTRAVELLING:
                description = "港澳通行证";
                break;
            case TWTRAVELLING:
                description = "台湾通行证";
                break;
            case PASSPORT:
                description = "护照";
                break;
        }
        return description;
    }
}
