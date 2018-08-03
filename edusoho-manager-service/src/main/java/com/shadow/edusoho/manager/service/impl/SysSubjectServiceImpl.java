package com.shadow.edusoho.manager.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.edusoho.bean.SysSubject;
import com.edusoho.service.SysSubjectService;
import com.shadow.edusoho.manager.mapper.SysSubjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Administrator on 2018/7/30.
 */
@Service
public class SysSubjectServiceImpl implements SysSubjectService{

    @Autowired
    private SysSubjectMapper sysSubjectMapper;

    public List<SysSubject> getAllSysSubject() {

        List<SysSubject> sysSubjects = sysSubjectMapper.selectAll();

        return sysSubjects;
    }
}
