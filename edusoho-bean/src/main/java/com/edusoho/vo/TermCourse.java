package com.edusoho.vo;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/7/30.
 */
public class TermCourse implements Serializable {
    //课程名称
    private String courseName;
    //课程状态 1正常 2下架 3删除
    private Integer isAvaliable;
    //专业Id
    private String subjectId;
    //开始添加时间
    private String startDateTime;
    //结束添加时间
    private String endDateTime;
    //当前第几页
    private String pageNum;
    //每页显示几条数据
    private String pageSize;
    public TermCourse() {

    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Integer getIsAvaliable() {
        return isAvaliable;
    }

    public void setIsAvaliable(Integer isAvaliable) {
        this.isAvaliable = isAvaliable;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
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
