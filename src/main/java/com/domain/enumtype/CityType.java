package com.domain.enumtype;

/**
 * Created by admin on 2017/6/14.
 */
public enum CityType {

    COUNTRY,CITY;

    public String getDescription(){
        String description = null;
        switch (this){
            case CITY:
                description = "城市";
                break;
            case COUNTRY:
                description = "国家";
                break;
        }
        return description;
    }
}
