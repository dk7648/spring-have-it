package com.project.have_it.habit;

import com.project.have_it.DTO.CommentDto;
import com.project.have_it.DTO.CustomUser;
import com.project.have_it.DTO.HabitDto;
import com.project.have_it.Member.Member;
import com.project.have_it.Member.MemberRepository;
import com.project.have_it.comment.Comment;
import com.project.have_it.model.HashtagGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class HabitController {
    private final HabitRepository habitRepository;
    private final MemberRepository memberRepository;


    @GetMapping("/api/habit/list")
    ResponseEntity<List<Habit>> getHabits() {
        List<Habit> result = habitRepository.findAll();
        System.out.println("Fetched habits: " + result);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/api/habit/top")
    ResponseEntity<List<Habit>> getHabitsTop6() {
        List<Habit> result = habitRepository.findTop6ByOrderByIdDesc();
        System.out.println("Fetched top 6 habits: " + result);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/api/habit/detail/{id}")
    ResponseEntity<Habit> detail(@PathVariable Long id, Model model) {
        //List<Commets> comments = commentRepository.findAllByParentId(id);
        System.out.println(">> detail");
        System.out.println("id : " + id);
        Optional<Habit> result = habitRepository.findById(id);
        System.out.println("result : " + result);
        if (result.isPresent()) {
            return ResponseEntity.ok(result.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/api/habit/write")
    public ResponseEntity<Habit> write(Authentication rawAuth, @RequestBody HabitDto habitDto) {
        Object principal = rawAuth.getPrincipal();
        if(principal instanceof CustomUser) {
            CustomUser auth = (CustomUser) principal;
            Optional<Member> optionalMember = memberRepository.findById(auth.getUsername());
            if(optionalMember.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            String hashtags = HashtagGenerator.generateHashtags(habitDto.getTitle(), habitDto.getContent());
            System.out.println(hashtags);  // "#명상,#아침,#하루시작" 등

            System.out.println(" >> habit / write");
            System.out.println(habitDto.toString());
            Habit habit = new Habit();

            habit.setTitle(habitDto.getTitle());
            habit.setContent(habitDto.getContent());
            habit.setDifficult(habitDto.getDifficult());
            habit.setRepeatCount(habitDto.getRepeatCount());
            habit.setHashtags(hashtags);

            Member member = optionalMember.get();
            habit.setMember(member);

            habitRepository.save(habit);

            return ResponseEntity.ok(habit);
        }
        return ResponseEntity.notFound().build();
    }
}
