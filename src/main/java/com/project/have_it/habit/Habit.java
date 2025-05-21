package com.project.have_it.habit;

import com.project.have_it.Member.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Habit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200)
    private String title;
    private String content;
    private Integer difficult;
    private Integer repeatCount;

    @ManyToOne
    @JoinColumn(name = "username", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;

    private String hashtags;
}
