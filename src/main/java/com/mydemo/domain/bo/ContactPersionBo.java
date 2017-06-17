package com.mydemo.domain.bo;

import com.mydemo.domain.enumtype.CustomerType;

/**
 * Created by admin on 2017/6/17.
 */
public class ContactPersionBo {

    private Long id;

    private String name;

    private Long userId;

    private CustomerType customerType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }
}
