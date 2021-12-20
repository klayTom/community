package com.kaly.controller;

import com.kaly.dto.PageinationDto;
import com.kaly.mapper.UserMapper;
import com.kaly.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;

    @GetMapping({"/","/index"})
    public String indexTest (HttpServletRequest request, Model model,
                             @RequestParam(value = "page",defaultValue = "1") Integer page,
                             @RequestParam(value = "size",defaultValue = "5") Integer size) {

        PageinationDto pageination= questionService.list(page,size);
        model.addAttribute("pageination",pageination);
        return "index";
    }
}
