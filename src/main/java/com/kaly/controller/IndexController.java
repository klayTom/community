package com.kaly.controller;

import com.kaly.mapper.UserMapper;
import com.kaly.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;

    @GetMapping({"/","/index"})
    public String indexTest (HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies!=null){
            for (Cookie cookie : cookies) {
                if ( "token".equals(cookie.getName()) ) {
                    String token = cookie.getValue();
                    User user = userMapper.findUserByToken(token);
                    if ( user!=null ) {
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }

        return "index";
    }
}
