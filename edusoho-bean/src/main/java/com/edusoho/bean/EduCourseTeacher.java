package com.edusoho.bean;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * Created by Administrator on 2018/7/31.
 */
public class EduCourseTeacher implements Serializable{

    @Column
    private Integer courseId;

    @Column
    private Integer teacherId;


    public EduCourseTeacher() {
    }


    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }
}
