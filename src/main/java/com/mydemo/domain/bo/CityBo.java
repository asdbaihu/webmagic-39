package com.mydemo.domain.bo;

import com.mydemo.domain.enumtype.CityType;

/**
 * Created by admin on 2017/6/14.
 */
public class CityBo{

    private String search;

    private CityType cityType;

    public CityType getCityType() {
        return cityType;
    }

    public void setCityType(CityType cityType) {
        this.cityType = cityType;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
