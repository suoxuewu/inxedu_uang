package com.shadow.edusoho.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2018/7/27.
 */
@Controller
public class ManagerController {
    @RequestMapping("main")
    public String login(){
         return "main";
    }

    @RequestMapping("hello")
    @ResponseBody
    public String hello(){
        return "Hello";
    }
}
