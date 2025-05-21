package com.project.have_it.comment;

import com.project.have_it.DTO.CommentDto;
import com.project.have_it.DTO.CustomUser;
import com.project.have_it.Member.Member;
import com.project.have_it.Member.MemberRepository;
import com.project.have_it.habit.Habit;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    @PostMapping("/api/comment/write")
    public ResponseEntity<Comment> write(Authentication rawAuth, @RequestBody CommentDto commentDto) {
        System.out.println(">> comment / write");
        System.out.println(commentDto.toString());
        Object principal = rawAuth.getPrincipal();
        if(principal instanceof CustomUser) {
            CustomUser auth = (CustomUser) principal;
            Optional<Member> optionalMember = memberRepository.findById(auth.getUsername());
            if(optionalMember.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            Comment comment = new Comment();

            Member member = optionalMember.get();
            comment.setMember(member);

            var habit = new Habit();
            habit.setId(commentDto.getParentId());
            comment.setHabit(habit);

            comment.setContent(commentDto.getContent());
            comment.setRate(commentDto.getRate());
            commentRepository.save(comment);
            return ResponseEntity.ok(comment);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/api/habit/detail/{id}/comments")
    ResponseEntity<List<Comment>> getComments(@PathVariable Long id) {
        List<Comment> result = commentRepository.findAllByHabitId(id);
        System.out.println("result : " + result);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/api/comment/top")
    ResponseEntity<List<Comment>> getCommentsTop6() {
        List<Comment> result = commentRepository.findTop6ByOrderByIdDesc();
        return ResponseEntity.ok(result);
    }
}
