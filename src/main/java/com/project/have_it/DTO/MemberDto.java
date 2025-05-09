package com.project.have_it.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberDto {
    private String username;
    private String password;
    private String displayName;
}
