package com.jo.goal.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Post {
    private Long id;
    private String categoryId;
    private String title;
    private String content;
    private String postImg;
}
