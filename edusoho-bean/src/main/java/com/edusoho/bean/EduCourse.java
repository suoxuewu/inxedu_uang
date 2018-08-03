package com.edusoho.bean;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2018/7/30.
 * 课程表
 */
public class EduCourse implements Serializable {
    //课程编号
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer courseId;
    //课程状态
    @Column
    private Integer isAvaliable;//1 正常　2　下架   3删除
    //课程名称
    @Column
    private String courseName;
    //课程专业ID
    @Column
    private String subjectId;
    //课程专业链
    @Column
    private String subjectLink;
    //课程添加时间
    @Column
    private Date addTime;
    //课程原价格(只显示)
    @Column
    private BigDecimal sourcePrice;
    //课程销售价格(实际支付价格) 设置为0则可以免费观看
    @Column
    private BigDecimal currentPrice;
    //课程简介
    @Column
    private  String title;
    //课程详情
    @Column
    private String context;
    //课时
    @Column
    private Integer lessionNum;
    //课程图片
    @Column
    private String logo;
    //更新时间
    @Column
    private Date updateTime;
    //销售数量
    @Column
    private Integer pageBuycount;
    //浏览数量
    @Column
    private Integer pageViewcount;
    //有效结束时间
    @Column
    private Date endTime;
    //有效期类型  0到期时间, 1 按天数
    @Column
    private Integer  losetype;
    //有效期:商品订单过期时间点
    @Column
    private String loseTime;
    //课程学习进度百分比
    @Transient
    private String studyPercent;

    public EduCourse() {
    }

    public Integer getIsAvaliable() {
        return isAvaliable;
    }

    public void setIsAvaliable(Integer isAvaliable) {
        this.isAvaliable = isAvaliable;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectLink() {
        return subjectLink;
    }

    public void setSubjectLink(String subjectLink) {
        this.subjectLink = subjectLink;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public BigDecimal getSourcePrice() {
        return sourcePrice;
    }

    public void setSourcePrice(BigDecimal sourcePrice) {
        this.sourcePrice = sourcePrice;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Integer getLessionNum() {
        return lessionNum;
    }

    public void setLessionNum(Integer lessionNum) {
        this.lessionNum = lessionNum;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getPageBuycount() {
        return pageBuycount;
    }

    public void setPageBuycount(Integer pageBuycount) {
        this.pageBuycount = pageBuycount;
    }

    public Integer getPageViewcount() {
        return pageViewcount;
    }

    public void setPageViewcount(Integer pageViewcount) {
        this.pageViewcount = pageViewcount;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getLosetype() {
        return  losetype;
    }

    public void setLosetype(Integer losetype) {
        this.losetype = losetype;
    }

    public String getLoseTime() {
        return loseTime;
    }

    public void setLoseTime(String loseTime) {
        this.loseTime = loseTime;
    }

    public String getStudyPercent() {
        return studyPercent;
    }

    public void setStudyPercent(String studyPercent) {
        this.studyPercent = studyPercent;
    }
}
