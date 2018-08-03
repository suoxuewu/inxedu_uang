package com.edusoho.vo;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/7/29.
 */
public class Term implements Serializable{
    private String userName;
    private String showName;
    private String email;
    private String mobile;
    private String isAvalible;
    private String startDateTime;
    private String endDateTime;
    private String pageNum;
    private String pageSize;

    public Term() {
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getPageNum() {
        return pageNum;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIsAvalible() {
        return isAvalible;
    }

    public void setIsAvalible(String isAvalible) {
        this.isAvalible = isAvalible;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }
}
