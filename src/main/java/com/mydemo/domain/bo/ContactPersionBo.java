package com.mydemo.domain.bo;

import com.mydemo.domain.enumtype.CustomerType;

/**
 * Created by admin on 2017/6/17.
 */
public class ContactPersionBo {

    private Long contactPersionId;

    private String name;

    private Long userId;

    private CustomerType customerType;

    public Long getContactPersionId() {
        return contactPersionId;
    }

    public void setContactPersionId(Long contactPersionId) {
        this.contactPersionId = contactPersionId;
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
