package com.klay.controller;

import com.klay.cache.TagCache;
import com.klay.dto.QuestionDto;
import com.klay.mapper.UserMapper;
import com.klay.model.Question;
import com.klay.model.User;
import com.klay.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish/{id}")
    public String edit (@PathVariable("id")Long id,
                        Model model) {
        QuestionDto question = questionService.questionById(id);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",question.getId());
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }
    @GetMapping("/publish")
    public String publish(Model model){
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }
    @PostMapping("/publish")
    public String doPublish(@RequestParam(value = "title",required = false) String title,
                            @RequestParam(value = "tag",required = false) String tag,
                            @RequestParam(value = "description",required = false) String description,
                            @RequestParam(value = "id",required = false) Long id,
                            Model model,
                            HttpServletRequest request){
        model.addAttribute("title",title);
        model.addAttribute("tag",tag);
        model.addAttribute("description",description);
        model.addAttribute("id",id);
        model.addAttribute("tags", TagCache.get());

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

        String invalid = TagCache.filterInvalid(tag);
        if (StringUtils.isNotBlank(invalid)) {
            model.addAttribute("error","输入非法标签:" + invalid);
            return "publish";
        }

        User user =(User) request.getSession().getAttribute("user");
        if (user==null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        Question question = new Question();
        question.setTag(tag);
        question.setTitle(title);
        question.setDescription(description);
        question.setCreator(user.getId());
        question.setId(id);
        questionService.createOrUpdate(question);
        return "redirect:/";
    }
}
