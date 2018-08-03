package com.edusoho.service;

import com.edusoho.bean.EduCourse;
import com.edusoho.vo.TermCourse;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/7/30.
 */
public interface CourseService {



    /**
     * 获取分页数据
     * @param termCourse 封装了分页需要的信息以及一些过滤条件
     * @return
     */
    public Map<String,Object> getPage(TermCourse termCourse);

    /**
     * 保存课程信息
     * @param eduCourse
     * @return
     */
    public Boolean saveCourseInfo(EduCourse eduCourse);

    /**
     * 根据Id删除 课程信息   不删除数据库记录 只是更新表中记录
     * @param courseId
     * @return
     */
    public Boolean deleteCourseInfo(Integer courseId);

    /**
     * 根据Id获取课程信息
     * @param courseId
     * @return
     */
    public EduCourse getCourseInfoById(Integer courseId);

    /**
     * 更新课程表信息
     * @param eduCourse
     * @return
     */
    public Boolean updateCourseInfo(EduCourse eduCourse);

    /**
     * 根据专业Id获取课程
     * @param subjectId
     * @return
     */
    List<EduCourse> getAllEduCourseBySubjectId(String subjectId);

    /**
     * 批量保存
     * @param courseId
     * @param teachers
     * @return
     */
    Boolean saveTeacherAndCourse(Integer courseId,List<Integer> teachers);

    /**
     * 批量删除
     * @param courseId
     * @param teachers
     * @return
     */
    Boolean deleteTeacherAndCourse(Integer courseId,List<Integer> teachers);
}
