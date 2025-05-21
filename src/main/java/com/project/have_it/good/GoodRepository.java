package com.project.have_it.good;

import com.project.have_it.Member.Member;
import com.project.have_it.habit.Habit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GoodRepository extends JpaRepository<Good, Long> {
    Optional<Good> findByMemberAndHabit(Member member, Habit post);
    boolean existsByMemberAndHabit(Member member, Habit post);
}
