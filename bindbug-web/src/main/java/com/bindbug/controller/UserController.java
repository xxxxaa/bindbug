package com.bindbug.controller;

import com.bindbug.model.User;
import com.bindbug.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * Created by yan on 16/1/25.
 */
@Controller
@RequestMapping(value = "/admin")
public class UserController {


    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserService userService;

    @RequestMapping(value = "/")
    public String index(HttpSession httpSession){
        User dbUser = (User) httpSession.getAttribute("loginUser");
        if(dbUser == null){
            return "/login.html";
        }else{
            return "/admin/index";
        }
    }


    @RequestMapping(value = "/doLogin")
    public String login(String loginName, String password, HttpSession httpSession){
        User dbUser = userService.login(loginName, password);
        if(dbUser != null){
            httpSession.setAttribute("loginUser", dbUser);
            return "/admin/index";
        }else{
            return "/login.html";
        }
    }

    @RequestMapping(value = "edit")
    public String editArticle(){
        return "/admin/article/edit";
    }

}
