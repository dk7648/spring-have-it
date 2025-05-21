package com.project.have_it.good;

import com.project.have_it.Member.Member;
import com.project.have_it.habit.Habit;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "likes", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"member", "habit"})
})
public class Good {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "username", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT), nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "habit_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT), nullable = false)
    private Habit habit;

    protected Good() {}

    public Good(Member member, Habit habit) {
        this.member = member;
        this.habit = habit;
    }
}