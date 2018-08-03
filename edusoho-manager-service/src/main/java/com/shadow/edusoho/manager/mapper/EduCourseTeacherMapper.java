package com.shadow.edusoho.manager.mapper;

import com.edusoho.bean.EduCourseTeacher;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by Administrator on 2018/8/1.
 */
public interface EduCourseTeacherMapper extends Mapper<EduCourseTeacher> {

    public Integer saveTeacherAndCourse(@Param("courseId") Integer courseId,@Param("teachers") List<Integer> teachers);
    public Integer deleteTeacherAndCourse(@Param("courseId") Integer courseId,@Param("teachers") List<Integer> teachers);
}
