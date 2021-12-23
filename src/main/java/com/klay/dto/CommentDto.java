package com.klay.dto;

import lombok.Data;

@Data
public class CommentDto {
    private Long parentId;
    private String context;
    private int type;
}
