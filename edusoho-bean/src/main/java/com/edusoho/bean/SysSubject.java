package com.edusoho.bean;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2018/7/30.
 * 专业表
 */
public class SysSubject implements Serializable {
    /**
     * 专业ID
     */
    @Id
    @Column
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Integer subjectId;
    /**
     * 专业名称
     */
    @Column
    private String subjectName;
    /**
     * 状态  0默认 1删除
     */
    @Column
    private Integer status;
    /**
     * 创建时间
     */
    @Column
    private Date createTime;

    /**
     * 父节点
     */
    @Column
    private Integer parentId;
     /**
     * 排序字段
     */
    @Column
    private Integer sort;

    public SysSubject() {
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
