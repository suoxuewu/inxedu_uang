package com.shadow.edusoho.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.edusoho.bean.EduTeacher;
import com.edusoho.bean.SysSubject;
import com.edusoho.service.EduTeacherService;
import com.edusoho.service.SysSubjectService;
import com.edusoho.vo.TermTeacher;
import org.mockito.Mock;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.*;

/**
 * Created by Administrator on 2018/7/29.
 */
@Controller
public class EduTeacherController {

    @Reference
    private EduTeacherService eduTeacherService;

    @Reference
    private SysSubjectService sysSubjectService;

    @RequestMapping("/teacher")
    public String teacher(Model model){
        List<SysSubject> allSysSubject = sysSubjectService.getAllSysSubject();
        model.addAttribute("allSysSubjectList",allSysSubject);
        model.addAttribute("eduTeacher",new EduTeacher());
        model.addAttribute("filePath","http://192.168.25.38:8096/upload/4e3fa2bf6dd34bacbca1b1cc763bddb8-01218f554229710000019ae9631440.jpg");
        return "teacher";
    }
    @RequestMapping("/addTeacher")
    public String addTeacher(Model model){
        List<SysSubject> allSysSubject = sysSubjectService.getAllSysSubject();
        model.addAttribute("allSysSubjectList",allSysSubject);
        model.addAttribute("filePath","http://192.168.25.38:8096/upload/4e3fa2bf6dd34bacbca1b1cc763bddb8-01218f554229710000019ae9631440.jpg");
        model.addAttribute("eduTeacher",new EduTeacher());
        return "addTeacher";
    }

    @RequestMapping("/getTeacherPage")
    @ResponseBody
    public String getTeacherPage(TermTeacher termParams){
        Map<String, Object> pages = eduTeacherService.getPage(termParams);
        return JSON.toJSONString(pages);
    }

    /**
     * 上传头像,保存到本地
     * @param request
     * @return
     */
    @RequestMapping(value = "uploadPicImage",method = RequestMethod.POST)
    @ResponseBody
    public String uploadPicImage(HttpServletRequest request){
        Map<String, Object> maps = new HashMap<String, Object>();
        String contextPath = request.getContextPath();
        String bastPath = "";
        if(contextPath.length()>0){
            bastPath = request.getScheme()+"://192.168.25.38:"+request.getServerPort()+"/"+contextPath+"/upload/";
        }else{
            bastPath = request.getScheme()+"://192.168.25.38:"+request.getServerPort()+"/upload/";
        }

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile picImage = multipartRequest.getFile("picImage");
        if(!picImage.isEmpty()){
            //获取上传图片的图片名称
            String originalFilename = picImage.getOriginalFilename();
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            //生成新的文件名
            String newFileName = uuid+"-"+originalFilename;
            //文件保存的路径
            File file = new File("C:\\Users\\Administrator\\Desktop\\Portrait-master\\WEB-INF\\upload\\"+newFileName);
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                bufferedOutputStream.write(picImage.getBytes());

                maps.put("filePath",bastPath+newFileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return JSON.toJSONString(maps);
    }

    /**
     * 保存教师信息
     * @return
     */
    @RequestMapping("saveEduTeacher")
    public String saveEduTeacher(@ModelAttribute("eduTeacher") EduTeacher eduTeacher){
        //设置初始化默认值 0 为正常  1 为删除
        eduTeacher.setStatus(0);
        //设置创建时间
        eduTeacher.setCreateTime(new Date());
        //设置更新时间
        eduTeacher.setUpdateTime(new Date());
        //保存教师信息
        Boolean flag = eduTeacherService.saveEduTeacher(eduTeacher);


        return "redirect://192.168.25.38:8096/teacher";
    }

    /**
     * 根据id获取教师信息
     * @param id
     * @return
     */
    @RequestMapping("getEduTeacherInfoById")
    @ResponseBody
    public String getEduTeacherInfoById(@RequestParam("id") String id){
         Map<String, Object> maps = new HashMap<String, Object>();
         EduTeacher teacher = eduTeacherService.getEduTeacherById(id);
         maps.put("teacher",teacher);
        return JSON.toJSONString(maps);
    }

    /**
     * 更新用户信息
     * @param eduTeacher
     * @return
     */
    @RequestMapping(value = "updateEduTeacherInfo",method = RequestMethod.POST)
    @ResponseBody
    public String updateEduTeacherInfo(EduTeacher eduTeacher){
        Map<String, Object> maps = new HashMap<String, Object>();
        eduTeacher.setUpdateTime(new Date());
        Boolean flag = eduTeacherService.updateEduTeacher(eduTeacher);
        if(!flag){
            maps.put("flag",false);
        }else{
            maps.put("flag",true);
        }
        return JSON.toJSONString(maps);

    }
    @RequestMapping(value = "deleteTeacherInfo",method = RequestMethod.POST)
    @ResponseBody
    public String deleteTeacherInfo(String id){
        Map<String, Object> maps = new HashMap<String, Object>();

        Boolean flag = eduTeacherService.deleteEduTeacher(id);
        if(!flag){
            maps.put("flag",false);
        }else{
            maps.put("flag",true);
        }
        return JSON.toJSONString(maps);
    }


}
