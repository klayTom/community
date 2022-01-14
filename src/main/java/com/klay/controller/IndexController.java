package com.klay.controller;

import com.klay.dto.PageinationDto;
import com.klay.mapper.UserMapper;
import com.klay.service.QuestionService;
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
                             @RequestParam(value = "size",defaultValue = "5") Integer size,
                             @RequestParam(value = "search",required = false) String search) {

        PageinationDto pageination= questionService.list(search,page,size);
        model.addAttribute("pageination",pageination);
        model.addAttribute("search",search);
        return "index";
    }
}
