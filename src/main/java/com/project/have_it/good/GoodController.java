package com.project.have_it.good;

import com.project.have_it.DTO.CustomUser;
import com.project.have_it.Member.Member;
import com.project.have_it.Member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class GoodController {
    private final GoodService goodService;
    private final MemberRepository memberRepository;

    // 좋아요 등록
    @GetMapping("/api/habit/detail/{habitId}/good")
    public ResponseEntity<Boolean> state(@PathVariable Long habitId,
                                          Authentication rawAuth) {
        System.out.println(">> good / get");
        Object principal = rawAuth.getPrincipal();
        if (principal instanceof CustomUser) {
            CustomUser auth = (CustomUser) principal;
            Optional<Member> optionalMember = memberRepository.findById(auth.getUsername());
            if (optionalMember.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            Member member = optionalMember.get();
            boolean isGood = goodService.showState(member, habitId);
            return ResponseEntity.ok(isGood ? true : false);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @PostMapping("/api/habit/detail/{habitId}/good")
    public ResponseEntity<Boolean> toggle(@PathVariable Long habitId,
                                          Authentication rawAuth) {
        System.out.println(">> good / post");
        Object principal = rawAuth.getPrincipal();
        if(principal instanceof CustomUser) {
            CustomUser auth = (CustomUser) principal;
            Optional<Member> optionalMember = memberRepository.findById(auth.getUsername());
            if (optionalMember.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            Member member = optionalMember.get();
            boolean isGood = goodService.toggle(member, habitId);
            return ResponseEntity.ok(isGood ? true : false);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
