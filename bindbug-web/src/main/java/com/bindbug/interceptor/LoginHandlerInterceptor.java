package com.bindbug.interceptor;

import com.bindbug.model.User;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by yan on 16/1/27.
 */
public class LoginHandlerInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String loginUrl = "/login.html";
        if(request.getServletPath().toLowerCase().indexOf("login") > -1){
            return true;
        }
        if(request.getServletPath().indexOf("admin") == -1){
            return true;
        }
        HttpSession httpSession = request.getSession();
        User user = (User)httpSession.getAttribute("loginUser");
        if(user == null){
            response.sendRedirect(request.getContextPath() + loginUrl);
            return false;
        }else{
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }
}
