package com.project.have_it.DTO;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CommentDto {

    private String content;
    private Integer rate;
    private Long parentId;

}
