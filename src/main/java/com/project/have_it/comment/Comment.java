package com.project.have_it.comment;

import com.project.have_it.Member.Member;
import com.project.have_it.habit.Habit;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    @ManyToOne
    @JoinColumn(name = "username", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;

    @ManyToOne
    @JoinColumn(name = "habit_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Habit habit;

    private Integer rate;

    @CreationTimestamp
    private LocalDateTime created;

}
