package com.domain.vo;

import com.domain.User;

/**
 * Created by admin on 2017/6/12.
 */
public class UserVo extends User{

    private static final long serialVersionUID = -8870488343773817074L;

    private String cityName;

    private String cardTypeName;

    private String customerTypeName;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCardTypeName() {
        return cardTypeName;
    }

    public void setCardTypeName(String cardTypeName) {
        this.cardTypeName = cardTypeName;
    }

    public String getCustomerTypeName() {
        return customerTypeName;
    }

    public void setCustomerTypeName(String customerTypeName) {
        this.customerTypeName = customerTypeName;
    }
}
