package com.kaly.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GithubUser {
    private String name;
    private String dio;
    private Long id;
    private String avatarUrl;
}
