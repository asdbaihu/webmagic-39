package com.mydemo.domain.bo;

import com.mydemo.domain.enumtype.CityType;

/**
 * Created by admin on 2017/6/14.
 */
public class CityBo{

    private String search;

    private String cnName;

    private String enName;

    private CityType cityType;

    private String countryCode;

    private String countryCodeMore;

    private String remark;

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

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryCodeMore() {
        return countryCodeMore;
    }

    public void setCountryCodeMore(String countryCodeMore) {
        this.countryCodeMore = countryCodeMore;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
