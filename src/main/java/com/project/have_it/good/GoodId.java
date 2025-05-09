package com.project.have_it.good;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class GoodId implements Serializable {

    private Long member;
    private Long habitId;

    // 기본 생성자 필수
    public GoodId() {}

    public GoodId(Long member, Long habitId) {
        this.member = member;
        this.habitId = habitId;
    }

    // equals()와 hashCode() 반드시 구현
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GoodId)) return false;
        GoodId goodId = (GoodId) o;
        return Objects.equals(member, goodId.member) &&
                Objects.equals(habitId, goodId.habitId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(member, habitId);
    }
}