package com.project.have_it.comment;

import com.project.have_it.habit.Habit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByHabit(Habit habit);
}
