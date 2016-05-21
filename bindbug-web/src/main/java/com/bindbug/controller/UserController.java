package com.bindbug.controller;

import com.bindbug.model.User;
import com.bindbug.service.UserService;
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
    public String login(User user, HttpSession httpSession){
        String loginName = user.getLoginName();
        String password = user.getPassword();
        User dbUser = userService.login(loginName, password);
        if(dbUser != null){
            httpSession.setAttribute("loginUser", dbUser);
//            model.addAttribute("user", dbUser);
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
