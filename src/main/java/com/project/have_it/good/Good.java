package com.project.have_it.good;

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
public class Good {

    @EmbeddedId
    private GoodId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("member")  // LikeId.memberId와 연결
    @JoinColumn(name = "member", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("habitId")   // LikeId.habitId와 연결
    @JoinColumn(name = "habit_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Habit habit;

    private String content;

    @CreationTimestamp
    private LocalDateTime createdAt;
}