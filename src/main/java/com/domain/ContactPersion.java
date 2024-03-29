package com.domain;

import com.domain.enumtype.CardType;
import com.domain.enumtype.CustomerType;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by admin on 2017/6/13.
 */
public class ContactPersion implements Serializable{

    private static final long serialVersionUID = -426977597882022656L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contactPersionId;
    //名称
    private String name;
    //性别
    private String sex;
    //城市
    private Long city;
    //证件类型
    private CardType cardType;
    //证件编号
    private String card;
    //邮箱
    private String email;
    //电话
    private String phone;
    //手机
    private String tel;
    //旅客类型
    private CustomerType customerType;
    //地址
    private String address;
    //邮编
    private String zipCode;
    //用户
    private Long userId;
    //创建时间
    private Date createTime;
    //最近一次修改时间
    private Date updateTime;

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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Long getCity() {
        return city;
    }

    public void setCity(Long city) {
        this.city = city;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
