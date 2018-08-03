package com.edusoho.service;

import com.edusoho.bean.EduCourseTeacher;
import com.edusoho.bean.EduTeacher;
import com.edusoho.vo.Term;
import com.edusoho.vo.TermTeacher;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/7/29.
 */
public interface EduTeacherService {
    /**
     * 分页数据
     * @param termParams
     * @return
     */
    public Map<String, Object> getPage(TermTeacher termParams);

    /**
     * 保存教师信息
     * @param eduTeacher
     * @return
     */
    public Boolean saveEduTeacher(EduTeacher eduTeacher);

    /**
     * 更新教师信息
     * @param eduTeacher
     * @return
     */
    public Boolean updateEduTeacher(EduTeacher eduTeacher);

    /**
     * 删除教师信息
     * @param id
     * @return
     */
    public Boolean deleteEduTeacher(String id);

    /**
     * 根据Id或
     * @param id
     * @return
     */
    public EduTeacher getEduTeacherById(String id);

    /**
     * 获取所有教师信息
     * @return
     */
    public List<EduTeacher> getAllTeacherInfo();

    /**
     * 根据课时id获取分配给当前课时的教师
     * @param courseId
     * @return
     */
    public List<EduCourseTeacher>  getAllCourseTeacher(Integer courseId);

}
