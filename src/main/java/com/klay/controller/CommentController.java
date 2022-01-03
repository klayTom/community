package com.klay.controller;

import com.klay.dto.CommentCreateDto;
import com.klay.dto.CommentDto;
import com.klay.dto.ResultDto;
import com.klay.enums.CommentTypeEnum;
import com.klay.exception.CustomizeErrorCode;
import com.klay.model.Comment;
import com.klay.model.User;
import com.klay.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {
    @Autowired
    CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post (@RequestBody CommentCreateDto commentCreateDto,
                        HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if ( user==null ) {
            return ResultDto.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        if (commentCreateDto == null || StringUtils.isBlank(commentCreateDto.getContext())) {
            return ResultDto.errorOf(CustomizeErrorCode.CONTEXT_IS_EMPTY);
        }
        Comment comment = new Comment();
        comment.setContext(commentCreateDto.getContext());
        comment.setType(commentCreateDto.getType());
        comment.setParentId(commentCreateDto.getParentId());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L);
        commentService.insert(comment);
        return ResultDto.okOf();
    }

    @ResponseBody
    @RequestMapping(value = "/comment/{id}",method = RequestMethod.GET)
    public ResultDto<List<CommentDto>> comment (@PathVariable(name = "id") Long id) {
        List<CommentDto> commentDtos = commentService.listByTargetId(id, CommentTypeEnum.COMMENT);
        return ResultDto.okOf(commentDtos);
    }
}
