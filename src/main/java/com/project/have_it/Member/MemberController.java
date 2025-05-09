package com.project.have_it.Member;

import com.project.have_it.Auth.JwtUtil;
import com.project.have_it.DTO.CustomUser;
import com.project.have_it.DTO.JwtDto;
import com.project.have_it.DTO.LoginDto;
import com.project.have_it.DTO.MemberDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    @PostMapping("/register")
    String register(@RequestBody MemberDto memberDto) {
        memberService.save(memberDto);
        return "회원가입 성공";
    }

    @PostMapping("/login/jwt")
    public ResponseEntity<JwtDto> loginJWT(@RequestBody LoginDto data,
                                           HttpServletResponse response){
        System.out.println(">>jwt");
        var authToken = new UsernamePasswordAuthenticationToken(
                data.getUsername(), data.getPassword()
        );
        var auth = authenticationManagerBuilder.getObject().authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);

        var jwt = JwtUtil.createToken(SecurityContextHolder.getContext().getAuthentication());
        System.out.println(jwt);

        var cookie = new Cookie("jwt", jwt);
        cookie.setMaxAge(3600*10); //10시간 쿠키 유효
        cookie.setHttpOnly(true); //쿠키 임의 조작 막기
        cookie.setPath("/"); //모든 경로에서 쿠키 사용
        cookie.setSecure(false);
        response.addCookie(cookie); //쿠키 같이 보냄
        return ResponseEntity.ok(new JwtDto(jwt));
    }

    @GetMapping("/myPage")
    public ResponseEntity<CustomUser> myPage(Authentication rawAuth) {
        System.out.println(rawAuth);
        if (rawAuth == null) {
            System.out.println("Authentication is null");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Object principal = rawAuth.getPrincipal();
        if(principal instanceof CustomUser) {
            CustomUser user = (CustomUser) principal;
            System.out.println(user);
            System.out.println(user.getDisplayName());
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }


}
