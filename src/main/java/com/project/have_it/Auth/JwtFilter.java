package com.project.have_it.Auth;

import com.project.have_it.DTO.CustomUser;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

//@Component
public class JwtFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();

        if(cookies == null) {
            filterChain.doFilter(request, response);
            return;
        }
        String jwtCookie = null;
        for(int i=0; i< cookies.length; i++)  {
            if(cookies[i].getName().equals("jwt")) {
                jwtCookie = cookies[i].getValue();
            }
        }

        if(jwtCookie == null) {
            filterChain.doFilter(request, response);
            return;
        }

        Claims claim;
        try {
            claim = JwtUtil.extractToken(jwtCookie);
        } catch(Exception e) {
            filterChain.doFilter(request,response);
            return;
        }
        var jwtUsername = claim.get("username").toString();
        var arr = claim.get("authorities").toString().split(",");
        var authorities = Arrays.stream(arr).map(data -> new SimpleGrantedAuthority(data)).toList();
        var jwtDisplayName = claim.get("displayName").toString();
        var customUser = new CustomUser(
                jwtUsername,
                "null",
                authorities,
                jwtDisplayName
        );
        var authToken = new UsernamePasswordAuthenticationToken(
                customUser, null, authorities
        );

        System.out.println(" >> filter ok");
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);
    }
}
