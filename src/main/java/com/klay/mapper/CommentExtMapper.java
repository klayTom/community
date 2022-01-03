package com.klay.mapper;

import com.klay.model.Comment;

public interface CommentExtMapper {
    int incCommentCount(Comment comment);
}