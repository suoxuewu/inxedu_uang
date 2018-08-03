package com.shadow.edusoho.manager.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.edusoho.bean.EduCourse;
import com.edusoho.service.CourseService;
import com.edusoho.vo.TermCourse;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.shadow.edusoho.manager.mapper.CourseMapper;
import com.shadow.edusoho.manager.mapper.EduCourseTeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/7/30.
 */
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private EduCourseTeacherMapper eduCourseTeacherMapper;

    /**
     * 获取分页数据
     *
     * @param termCourse 封装了分页需要的信息以及一些过滤条件
     * @return
     */
    public Map<String, Object> getPage(TermCourse termCourse) {
        //获取pageNum显示第几页数据
        String num = termCourse.getPageNum();
        int pageNum = 1;//默认显示第一页
        if (num != null || num.length() > 0) {
            pageNum = Integer.parseInt(num);
        }
        //获取每页显示的
        String size = termCourse.getPageSize();
        int pageSize = 5;//默认每页显示5条数据
        if (size != null || size.length() > 0) {
            pageSize = Integer.parseInt(size);
        }
        //使用mybatis插件pageHeler插件进行分页
        Page<Object> pages = PageHelper.startPage(pageNum, pageSize);

        List<EduCourse> eduCourseList = null;
        Example example = new Example(EduCourse.class);
        Example.Criteria criteria = example.createCriteria();

        //  课程状态  1 正常　2　下架   3删除
        //criteria.orEqualTo("isavaliable","1").orEqualTo("isavaliable",2);
        //1.条件一:课程名称
        String courseName = termCourse.getCourseName();
        if (courseName != null && courseName.length() > 0) {
            //组装条件
            criteria.andEqualTo("courseName", courseName);
        }

        //2.条件二:状态  1 正常　2　下架   3删除
        Integer isAvaliable = termCourse.getIsAvaliable();
        if (isAvaliable != null && isAvaliable > 0 && isAvaliable < 4) {
            //组装条件
            criteria.andEqualTo("isAvaliable", isAvaliable);
        }

        //3.条件三:专业Id
        String subjectId = termCourse.getSubjectId();
        if (subjectId != null && subjectId.length() > 0 && subjectId.length() < 5) {
            //组装条件
            criteria.andEqualTo("subjectId", subjectId);
        }
        //4.条件四:注册时间 [开始注册时间，结束注册时间]这个范围之间的
        String startDateTime = termCourse.getStartDateTime();
        String endDateTime = termCourse.getEndDateTime();
        if (startDateTime != null && startDateTime.length() > 0 && endDateTime != null && endDateTime.length() > 0) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                //将字符串类型的日期转换成Date类型
                Date startDate, endDate;
                startDate = format.parse(startDateTime);
                endDate = format.parse(endDateTime);
                //组装条件
                criteria.andBetween("addTime", startDate, endDate);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        eduCourseList = courseMapper.selectByExample(example);

        HashMap<String, Object> maps = new HashMap<String, Object>();
        //当前第几页
        maps.put("pageNum", pages.getPageNum());
        //总共多少页
        maps.put("totalPageCount", pages.getPages());
        //每页显示多少跳数据
        maps.put("pageSize", pages.getPageSize());
        //总共多少条
        maps.put("totalCount", pages.getTotal());
        //要显示数据
        maps.put("eduCourseList", eduCourseList);
        return maps;
    }

    /**
     * 保存课程信息
     *
     * @param eduCourse
     * @return
     */
    public Boolean saveCourseInfo(EduCourse eduCourse) {
        return null;
    }

    /**
     * 根据Id删除 课程信息   不删除数据库记录 只是更新表中记录
     *
     * @param courseId
     * @return
     */
    public Boolean deleteCourseInfo(Integer courseId) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setCourseId(courseId);
        eduCourse.setIsAvaliable(3);
        int flag = courseMapper.updateByPrimaryKeySelective(eduCourse);
        return flag >0;
    }

    /**
     * 根据Id获取课程信息
     *
     * @param courseId
     * @return
     */
    public EduCourse getCourseInfoById(Integer courseId) {
        return courseMapper.selectByPrimaryKey(courseId);
    }

    /**
     * 更新课程表信息
     *
     * @param eduCourse
     * @return
     */
    public Boolean updateCourseInfo(EduCourse eduCourse) {
        int flag = courseMapper.updateByPrimaryKeySelective(eduCourse);
        return  flag>0 ;
    }

    /**
     * 根据专业Id获取对应课程信息
     * @param subjectId
     * @return
     */
    public List<EduCourse> getAllEduCourseBySubjectId(String subjectId) {
        if(subjectId == null || subjectId.length() == 0){
            return null;
        }
        Example example = new Example(EduCourse.class);
        example.createCriteria().andEqualTo("subjectId",subjectId);
        List<EduCourse> eduCourseList = courseMapper.selectByExample(example);
        return eduCourseList;
    }

    /**
     * 批量保存
     * @param courseId
     * @param teachers
     * @return
     */
    public Boolean saveTeacherAndCourse(Integer courseId, List<Integer> teachers) {
        Integer integer = eduCourseTeacherMapper.saveTeacherAndCourse(courseId, teachers);
        if(integer > 0){
            return true;
        }

        return false;
    }

    /**
     * 批量删除
     * @param courseId
     * @param teachers
     * @return
     */
    public Boolean deleteTeacherAndCourse(Integer courseId, List<Integer> teachers) {
        Integer integer = eduCourseTeacherMapper.deleteTeacherAndCourse(courseId, teachers);
        if(integer > 0){
            return true;
        }
      return false;
    }
}
