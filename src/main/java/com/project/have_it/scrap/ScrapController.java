package com.project.have_it.scrap;

import com.project.have_it.DTO.CustomUser;
import com.project.have_it.Member.Member;
import com.project.have_it.Member.MemberRepository;
import com.project.have_it.habit.Habit;
import com.project.have_it.habit.HabitRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ScrapController {
    private final ScrapService scrapService;
    private final MemberRepository memberRepository;
    private final HabitRepository habitRepository;
    @PostMapping("/api/habit/detail/{habitId}/scrap")
    @Transactional
    public ResponseEntity<?> createScrap(@PathVariable Long habitId, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof CustomUser customUser)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not logged in");
        }

        Optional<Member> optionalMember = memberRepository.findById(customUser.getUsername());
        if (optionalMember.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        }
        Optional<Habit> optionalHabit = habitRepository.findById(habitId);
        if (optionalHabit.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Habit not found");
        }

        Scrap scrap = scrapService.scrapHabit(optionalMember.get(), optionalHabit.get());
        return ResponseEntity.ok(scrap);
    }

    @GetMapping("/api/scrap/me")
    public ResponseEntity<?> getMyScraps(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof CustomUser customUser)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not logged in");
        }

        Optional<Member> optionalMember = memberRepository.findById(customUser.getUsername());
        if (optionalMember.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        }

        List<Scrap> scraps = scrapService.getScrapsByMember(optionalMember.get());
        return ResponseEntity.ok(scraps);
    }

    // 3. 스크랩 삭제
    @DeleteMapping("/api/scrap/{scrapId}")
    public ResponseEntity<?> deleteScrap(@PathVariable Long scrapId, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof CustomUser customUser)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not logged in");
        }

        boolean deleted = scrapService.deleteScrap(scrapId, customUser.getUsername());
        if (deleted) {
            return ResponseEntity.ok("Deleted");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Cannot delete");
        }
    }

}
