package com.mydemo.domain;

import com.mydemo.domain.enumtype.CityType;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by admin on 2017/6/14.
 */
public class City implements Serializable{

    private static final long serialVersionUID = -2012397370872615507L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cnName;

    private String enName;

    private String countryCodeTwo;

    private String countryCodeThree;

    private CityType cityType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCountryCodeTwo() {
        return countryCodeTwo;
    }

    public void setCountryCodeTwo(String countryCodeTwo) {
        this.countryCodeTwo = countryCodeTwo;
    }

    public String getCountryCodeThree() {
        return countryCodeThree;
    }

    public void setCountryCodeThree(String countryCodeThree) {
        this.countryCodeThree = countryCodeThree;
    }

    public CityType getCityType() {
        return cityType;
    }

    public void setCityType(CityType cityType) {
        this.cityType = cityType;
    }
}
