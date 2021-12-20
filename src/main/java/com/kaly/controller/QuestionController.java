package com.kaly.controller;

import com.kaly.dto.QuestionDto;
import com.kaly.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable("id") Integer id,
                           Model model){
        QuestionDto questionDto = questionService.questionById(id);
        model.addAttribute("question",questionDto);
        return "question";
    }
}
