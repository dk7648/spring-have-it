package com.project.have_it.DTO;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class CustomUser extends User {
    private String displayName;

    public CustomUser(String username,
                      String password,
                      Collection<? extends GrantedAuthority> authorities,
                      String displayName
    ) {
        super(username, password, authorities);
        this.displayName = displayName;
    }
}
