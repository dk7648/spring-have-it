package com.project.have_it.DTO;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class HabitDto {
    private String title;
    private String content;
    private Integer difficult;
    private Integer repeatCount;
}
