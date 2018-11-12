package com.vissun.BackEnd_vissun.Bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Customer {
    @Id
    @GeneratedValue
    private Integer id;
    private String custname;
    private String contact;
    private String tel;
    private String address;
    private String email;
    private String wechar;
    private String qq;
    private String mark;
    private Integer userId;
    private String progress;
    private String progresstime;
    private String createtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWechar() {
        return wechar;
    }

    public void setWechar(String wechar) {
        this.wechar = wechar;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userid) {
        this.userId = userid;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getProgresstime() {
        return progresstime;
    }

    public void setProgresstime(String progresstime) {
        this.progresstime = progresstime;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String  createtime) {
        this.createtime = createtime;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", custname='" + custname + '\'' +
                ", contact='" + contact + '\'' +
                ", tel='" + tel + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", wechar='" + wechar + '\'' +
                ", qq='" + qq + '\'' +
                ", mark='" + mark + '\'' +
                ", userid=" + userId +
                ", progress='" + progress + '\'' +
                ", progresstime='" + progresstime + '\'' +
                ", createtime='" + createtime + '\'' +
                '}';
    }
}
