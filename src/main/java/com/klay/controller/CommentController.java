package com.klay.controller;

import com.klay.dto.CommentDto;
import com.klay.dto.ResultDto;
import com.klay.exception.CustomizeErrorCode;
import com.klay.mapper.CommentMapper;
import com.klay.model.Comment;
import com.klay.model.User;
import com.klay.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
public class CommentController {
    @Autowired
    CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post (@RequestBody CommentDto commentDto,
                        HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if ( user==null ) {
            ResultDto.errorOf(CustomizeErrorCode.NO_LOGIN);

        }
        Comment comment = new Comment();
        comment.setContext(commentDto.getContext());
        comment.setType(commentDto.getType());
        comment.setParentId(commentDto.getParentId());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L);
        commentService.insert(comment);
        return ResultDto.okOf();
    }
}
