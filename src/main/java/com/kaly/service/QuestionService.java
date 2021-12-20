package com.kaly.service;

import com.kaly.dto.PageinationDto;
import com.kaly.dto.QuestionDto;
import com.kaly.mapper.QuestionMapper;
import com.kaly.mapper.UserMapper;
import com.kaly.model.Question;
import com.kaly.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    QuestionMapper questionMapper;

    public PageinationDto list(Integer page, Integer size) {
        PageinationDto pageinationDto = new PageinationDto();

        Integer totalPage;

        Integer totalCount = questionMapper.count();

        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }

        pageinationDto.setPageination(totalPage,page);

        Integer offset = size * (page-1);
        List<Question> questions = questionMapper.list(offset,size);
        List<QuestionDto> questionDtoList = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.getUserById(question.getCreator());
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }
        pageinationDto.setQuestions(questionDtoList);
        return pageinationDto;
    }

    public PageinationDto list(Integer userId, Integer page, Integer size) {
        PageinationDto pageinationDto = new PageinationDto();
        Integer totalPage;

        Integer totalCount = questionMapper.countByUserId(userId);

        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }

        pageinationDto.setPageination(totalPage,page);

        Integer offset = size * (page-1);
        List<Question> questions = questionMapper.listByUserId(userId,offset,size);
        List<QuestionDto> questionDtoList = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.getUserById(question.getCreator());
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }
        pageinationDto.setQuestions(questionDtoList);
        return pageinationDto;
    }

    public QuestionDto questionById(Integer id) {
        QuestionDto questionDto = new QuestionDto();
        Question question = questionMapper.getById(id);
        BeanUtils.copyProperties(question,questionDto);
        User user = userMapper.getUserById(question.getCreator());
        questionDto.setUser(user);
        return questionDto;
    }

    public void createOrUpdate(Question question) {
        if (question.getId()==null) {
            // 创建
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.create(question);
        }else{
            // 更新
            question.setGmtModified(System.currentTimeMillis());
            questionMapper.update(question);
        }
    }
}
