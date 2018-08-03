package com.shadow.edusoho.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.edusoho.bean.EduCourse;
import com.edusoho.bean.EduCourseTeacher;
import com.edusoho.bean.EduTeacher;
import com.edusoho.bean.SysSubject;
import com.edusoho.service.CourseService;
import com.edusoho.service.EduTeacherService;
import com.edusoho.service.SysSubjectService;
import com.edusoho.vo.TermCourse;
import com.shadow.edusoho.manager.vo.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by Administrator on 2018/7/30.
 */
@Controller
public class CourseController {

    @Reference
    private SysSubjectService sysSubjectService;

    @Reference
    private CourseService courseService;

    @Reference
    private EduTeacherService eduTeacherService;

    @RequestMapping("eduCourse")
    public String course(Model model){
        //获取所有的专业
        List<SysSubject> sysSubjectList = sysSubjectService.getAllSysSubject();
        model.addAttribute("sysSubjectList",sysSubjectList);
        return "course";
    }

    /**
     * 跳转到课程添加页面
     * @param model
     * @return
     */
    @RequestMapping("addCourse")
    public String addCourse(Model model){

        List<EduTeacher> allTeacherInfo = eduTeacherService.getAllTeacherInfo();
        List<Map<String,Object>> tearchs = new ArrayList<Map<String,Object>>();
        for (EduTeacher eduTeacher : allTeacherInfo) {
             Map<String, Object> maps = new HashMap<String, Object>();
             maps.put("teacherId",eduTeacher.getId());
             maps.put("teacherName",eduTeacher.getName());
            tearchs.add(maps);
        }
        model.addAttribute("teacherList",tearchs);
        model.addAttribute("filePath","http://192.168.25.38:8096/upload/9799a46213fa43059c8c0d74b5bb85da-u=2990890144,682958763&fm=27&gp=0.jpg");
        return "addCourse";
    }
    @RequestMapping(value = "getCoursePage",method = RequestMethod.POST)
    @ResponseBody
    public String getCoursePage(TermCourse termCourse){
        Map<String, Object> pages = courseService.getPage(termCourse);
        //获取所有的专业
        List<SysSubject> sysSubjectList = sysSubjectService.getAllSysSubject();
        pages.put("sysSubjectList",sysSubjectList);
        return JSON.toJSONString(pages);
    }

    /**
     * 获取所有专业信息
     * @return
     */
    @RequestMapping("getSubject")
    @ResponseBody
    public String getSubject(){
        List<SysSubject> allSysSubject = sysSubjectService.getAllSysSubject();
        if(allSysSubject == null || allSysSubject.size() ==0)
            throw new NullPointerException("allSysSubject == null");

        List<Map<String, Object>>  list = new ArrayList<Map<String, Object>>();
        for (SysSubject sysSubject : allSysSubject) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("subjectId",sysSubject.getSubjectId());
            map.put("subjectName",sysSubject.getSubjectName());
            list.add(map);
        }
        return JSON.toJSONString(list);
    }

    /**
     * 根据专业ID获取课程信息
     * @return
     */
    @RequestMapping(value = "getCourse",method = RequestMethod.GET)
    @ResponseBody
    public String getCourse(@RequestParam("subjectId") String subjectId){

        List<EduCourse> eduCourseList = courseService.getAllEduCourseBySubjectId(subjectId);
         if(eduCourseList == null || eduCourseList.size() ==0)
            throw new NullPointerException("eduCourseList == null");

        List<Map<String, Object>>  list = new ArrayList<Map<String, Object>>();
        for (EduCourse eduCourse : eduCourseList) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("courseId",eduCourse.getCourseId());
            map.put("courseName",eduCourse.getCourseName());
            list.add(map);
        }
        return JSON.toJSONString(list);
    }
    @RequestMapping(value = "getCourseInfoById",method = RequestMethod.GET)
    @ResponseBody
    public String getCourseInfoById(@RequestParam("courseId") String courseId){
        int courseid = Integer.parseInt(courseId);
        EduCourse courseInfo = courseService.getCourseInfoById(courseid);
        HashMap<String, Object> maps = new HashMap<String, Object>();
        maps.put("eduCourse",courseInfo);


        //1.获取所有教师
        List<EduTeacher> allTeacher = eduTeacherService.getAllTeacherInfo();

        //2.被分配了当前课时的老师
        List<EduTeacher> assignCourseTeacher = new ArrayList<EduTeacher>();
        courseid = Integer.parseInt(courseId);
        //获取当前分配给当前课时的教师
        List<EduCourseTeacher> allCourseTeacher = eduTeacherService.getAllCourseTeacher(courseid);
        if(allCourseTeacher == null){
            //如果当前课时 没有分配的老师
            //
            maps.put("assignCourseTeacher",assignCourseTeacher);
            //未分配的就是所有老师
            maps.put("unAssingnCourseTeacher",allTeacher);
        }else{
            List<String> temp = new ArrayList<String>();
            for (EduCourseTeacher eduCourseTeacher : allCourseTeacher) {
                temp.add(String.valueOf(eduCourseTeacher.getTeacherId()));
            }
            List<EduTeacher> unAssingnCourseTeacher = new ArrayList<EduTeacher>();
            for (EduTeacher eduTeacher : allTeacher) {
                if(temp.contains(eduTeacher.getId())){
                    assignCourseTeacher.add(eduTeacher);
                }else{
                    unAssingnCourseTeacher.add(eduTeacher);
                }
            }
            maps.put("assignCourseTeacher",assignCourseTeacher);
            maps.put("unAssingnCourseTeacher",unAssingnCourseTeacher);
        }
         return  JSON.toJSONString(maps);
    }

    /**
     * 批量保存
     * @param courseId
     * @param dataJson
     * @return
     */
    @RequestMapping(value = "courseAssingnTeacher",method = RequestMethod.POST)
    @ResponseBody
    public String courseAssingnTeacher( Integer courseId,Data dataJson){
        Map<String, Object> map = new HashMap<String, Object>();
        if(courseId == null || dataJson.getTeachers().size() ==0 ){
            map.put("flag",false);
        }else{
            Boolean aBoolean = courseService.saveTeacherAndCourse(courseId, dataJson.getTeachers());
            if(aBoolean){
                map.put("flag",true);
            }else{
                map.put("flag",false);
            }
        }
        return JSON.toJSONString(map);
    }

    /**
     * 批量删除
     * @param courseId
     * @param dataJson
     * @return
     */
    @RequestMapping(value = "deletecourseAssingnTeacher",method = RequestMethod.POST)
    @ResponseBody
    public String deletecourseAssingnTeacher( Integer courseId,Data dataJson){
        Map<String, Object> map = new HashMap<String, Object>();
        if(courseId == null || dataJson.getTeachers().size() ==0 ){
            map.put("flag",false);
        }else{

            Boolean aBoolean = courseService.deleteTeacherAndCourse(courseId, dataJson.getTeachers());
            if(aBoolean){
                map.put("flag",true);
            }else{
                map.put("flag",false);
            }
        }
        return JSON.toJSONString(map);
    }

    /**
     * 修改课程信息
     * @param eduCourse
     * @return
     */
    @RequestMapping(value = "updateEduCourseInfo")
    @ResponseBody
    public String updateCourseInfo(EduCourse eduCourse){
        Map<String, Object> map = new HashMap<String, Object>();
        if(eduCourse == null || eduCourse.getCourseId() == null){
            map.put("flag",false);
        }else{
            eduCourse.setUpdateTime(new Date());
            Boolean aBoolean = courseService.updateCourseInfo(eduCourse);
            if(aBoolean){
                map.put("flag",true);
            }else{
                map.put("flag",false);
            }
        }
        return JSON.toJSONString(map);
    }
    @RequestMapping(value = "deleteCourseInfo")
    @ResponseBody
    public String deleteCourseInfo(Integer courseId){
        Map<String, Object> map = new HashMap<String, Object>();
        if(courseId == null){
            map.put("flag",false);
        }else{
            Boolean aBoolean = courseService.deleteCourseInfo(courseId);
            if(aBoolean){
                map.put("flag",true);
            }else{
                map.put("flag",false);
            }
        }
        return JSON.toJSONString(map);
    }
}
