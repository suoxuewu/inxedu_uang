package com.edusoho.bean;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2018/7/27.
 * 学员表
 */
public class EduUser implements Serializable{

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String userId;
    @Column
    private String mobile;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private String userName;
    @Column
    private String showName;
    @Column
    private Integer sex;
    @Column
    private Integer age;
    @Column
    private Date createTime;
    @Column
    private Integer isAvalible;
    @Column
    private String picImg;
    @Column
    private String bannerUrl;
    @Column
    private String msgNum;
    @Column
    private String sysMsgNum;
    @Column
    private Date lastSystemTime;


    public EduUser() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getIsAvalible() {
        return isAvalible;
    }

    public void setIsAvalible(Integer isAvalible) {
        this.isAvalible = isAvalible;
    }

    public String getPicImg() {
        return picImg;
    }

    public void setPicImg(String picImg) {
        this.picImg = picImg;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public String getMsgNum() {
        return msgNum;
    }

    public void setMsgNum(String msgNum) {
        this.msgNum = msgNum;
    }

    public String getSysMsgNum() {
        return sysMsgNum;
    }

    public void setSysMsgNum(String sysMsgNum) {
        this.sysMsgNum = sysMsgNum;
    }

    public Date getLastSystemTime() {
        return lastSystemTime;
    }

    public void setLastSystemTime(Date lastSystemTime) {
        this.lastSystemTime = lastSystemTime;
    }

    @Override
    public String toString() {
        return "EduUser{" +
                "userId='" + userId + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", userName='" + userName + '\'' +
                ", showName='" + showName + '\'' +
                ", sex=" + sex +
                ", age=" + age +
                ", createTime=" + createTime +
                ", isAvalible=" + isAvalible +
                ", picImg='" + picImg + '\'' +
                ", bannerUrl='" + bannerUrl + '\'' +
                ", msgNum='" + msgNum + '\'' +
                ", sysMsgNum='" + sysMsgNum + '\'' +
                ", lastSystemTime=" + lastSystemTime +
                '}';
    }
}
