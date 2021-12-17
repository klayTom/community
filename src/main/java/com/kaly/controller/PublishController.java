package com.kaly.controller;

import com.kaly.mapper.QuestionMapper;
import com.kaly.mapper.UserMapper;
import com.kaly.model.Question;
import com.kaly.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }
    @PostMapping("/publish")
    public String doPublish(@RequestParam("title") String title, @RequestParam("tag")
            String tag, @RequestParam("description") String description, Model model, HttpServletRequest request){
        model.addAttribute("title",title);
        model.addAttribute("tag",tag);
        model.addAttribute("description",description);

        if ("".equals(title)) {
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if ("".equals(description)) {
            model.addAttribute("error","问题补充不能为空");
            return "publish";
        }
        if ("".equals(tag)){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }


        Cookie[] cookies = request.getCookies();
        User user = null;
        if (cookies!=null) {
            for (Cookie cookie : cookies) {
                if ( "token".equals(cookie.getName()) ) {
                    String token = cookie.getValue();
                    user = userMapper.findUserByToken(token);
                    if ( user!=null ) {
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }
        if (user==null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        Question question = new Question();
        question.setTag(tag);
        question.setTitle(title);
        question.setDescription(description);
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        question.setCreator(user.getId());
        questionMapper.create(question);
        return "redirect:/";
    }
}
