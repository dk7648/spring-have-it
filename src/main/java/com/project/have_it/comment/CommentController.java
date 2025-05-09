package com.project.have_it.comment;

import com.project.have_it.DTO.CommentDto;
import com.project.have_it.DTO.CustomUser;
import com.project.have_it.Member.Member;
import com.project.have_it.habit.Habit;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentRepository commentRepository;

    @PostMapping("/api/comment/write")
    public ResponseEntity<Comment> write(Authentication rawAuth, @RequestBody CommentDto commentDto) {
        Object principal = rawAuth.getPrincipal();
        if(principal instanceof CustomUser) {
            CustomUser auth = (CustomUser) principal;
            Comment comment = new Comment();

            var member = new Member();
            member.setUsername(auth.getUsername());
            comment.setMember(member);

            var habit = new Habit();
            habit.setId(commentDto.getParentId());
            comment.setHabit(habit);

            comment.setContent(commentDto.getContent());

            commentRepository.save(comment);
            return ResponseEntity.ok(comment);
        }
        return ResponseEntity.notFound().build();
    }
}
