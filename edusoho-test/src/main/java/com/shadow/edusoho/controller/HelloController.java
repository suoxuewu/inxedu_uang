package com.shadow.edusoho.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Administrator on 2018/7/27.
 */
@Controller
public class HelloController {
    @RequestMapping("hello")
    public String hello(Model model){
        model.addAttribute("fileName","http://localhost:8080/upload/39fe939166774121a246cff16be1b755thymeleaf取掉错误.jpg");
        return "hello";


    }

    @RequestMapping("uploadPicImage")
    @ResponseBody
    public String upload(HttpServletRequest request){

        HashMap maps = new HashMap<String,Object>();

        //把带二进制表单数据的request对象交给spring转换,得到一个文本和普通数据分开的request对象
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
       MultipartFile picImage = multipartRequest.getFile("file");
        if(!picImage.isEmpty()) try {
            String realPath = request.getServletContext().getRealPath("/upload");

            //获取文件名
            String name = picImage.getOriginalFilename();
            //生成一个唯一的标识
            String s = UUID.randomUUID().toString().replaceAll("-", "");
            //新的文件名
            String fileName =  s+name;
            File file = new File("C:\\Users\\Administrator\\Desktop\\Portrait-master\\WEB-INF\\upload\\"+fileName);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
            bufferedOutputStream.write(picImage.getBytes());


            //获取上下文路径
            String contextPath = request.getContextPath();
            String basePath = "";
            if(contextPath!= null && contextPath.length()>0){
             basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/"+contextPath+"/upload/";
            }else{
               basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/upload/";
            }
              maps.put("filePath",basePath+fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}


