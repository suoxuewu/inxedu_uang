package com.edusoho.vo;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/7/29.
 */
public class TermTeacher implements Serializable{
    //讲师名称
    private String name;
    //头衔
    private String isStar;
    //开始添加时间
    private String startDateTime;
    //结束添加时间
    private String endDateTime;
    //当前第几页
    private String pageNum;
    //每页显示几条数据
    private String pageSize;

    public TermTeacher() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsStar() {
        return isStar;
    }

    public void setIsStar(String isStar) {
        this.isStar = isStar;
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

    public String getPageNum() {
        return pageNum;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }
}
