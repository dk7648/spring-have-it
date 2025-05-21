package com.project.have_it.scrap;

import com.project.have_it.Member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Scrap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 스크랩한 사용자
    @ManyToOne
    @JoinColumn(name = "username", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;

    // 복사된 Habit 정보
    private String title;
    private String content;
    private Integer difficult;
    private Integer repeatCount;

    // 원본의 id만 저장 (optional)
    private Long originalHabitId;

    private LocalDateTime scrappedAt = LocalDateTime.now();
}