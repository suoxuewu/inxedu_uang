package com.edusoho.service;

import com.edusoho.bean.EduCourse;
import com.edusoho.bean.SysSubject;

import java.util.List;

/**
 * Created by Administrator on 2018/7/30.
 */
public interface SysSubjectService {
    /**
     * 获取所有的专业信息
     * @return
     */
    List<SysSubject> getAllSysSubject();



}
