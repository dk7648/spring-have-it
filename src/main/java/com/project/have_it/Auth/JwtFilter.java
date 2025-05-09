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
        System.out.println(" >> jwt filter ");
        Cookie[] cookies = request.getCookies();

        if(cookies == null) {
            filterChain.doFilter(request, response);
            return;
        }
        System.out.println(" cookies != null");
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
        System.out.print("jwtCookie : ");
        System.out.println(jwtCookie);

        Claims claim;
        try {
            claim = JwtUtil.extractToken(jwtCookie);
        } catch(Exception e) {
            System.out.println("Exception!! : " + e.getMessage());
            filterChain.doFilter(request,response);
            return;
        }
        var jwtUsername = claim.get("username").toString();
        var arr = claim.get("authorities").toString().split(",");
        var authorities = Arrays.stream(arr).map(data -> new SimpleGrantedAuthority(data)).toList();
        var jwtDisplayName = claim.get("displayName").toString();
        System.out.println(">>>>>>");
        var customUser = new CustomUser(
                jwtUsername,
                "null",
                authorities,
                jwtDisplayName
        );
        System.out.println("custom User : " + customUser);
        var authToken = new UsernamePasswordAuthenticationToken(
                customUser, null, authorities
        );

        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);
    }
}
