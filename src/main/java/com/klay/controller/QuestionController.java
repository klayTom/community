package com.klay.controller;

import com.klay.dto.CommentDto;
import com.klay.dto.QuestionDto;
import com.klay.enums.CommentTypeEnum;
import com.klay.service.CommentService;
import com.klay.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,  Model model){
        QuestionDto questionDto = questionService.questionById(id);
        List<CommentDto> comments = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);
        List<QuestionDto> relatedQuestions = questionService.selectRelated(questionDto);

        // 累加阅读数
        questionService.incView(id);
        model.addAttribute("question",questionDto);
        model.addAttribute("comments",comments);
        model.addAttribute("relatedQuestions",relatedQuestions);
        return "question";
    }
}
