package com.project.have_it.good;

import com.project.have_it.Member.Member;
import com.project.have_it.Member.MemberRepository;
import com.project.have_it.habit.Habit;
import com.project.have_it.habit.HabitRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor

public class GoodService {

    private final GoodRepository goodRepository;
    private final HabitRepository habitRepository;

    @Transactional
    public boolean toggle(Member member, Long habitId) {
        Habit habit = habitRepository.findById(habitId)
                .orElseThrow(() -> new EntityNotFoundException("게시글 없음"));

        Optional<Good> existing = goodRepository.findByMemberAndHabit(member, habit);
        if (existing.isPresent()) {
            goodRepository.delete(existing.get());
            return false; // 좋아요 취소됨
        } else {
            goodRepository.save(new Good(member, habit));
            return true; // 좋아요 등록됨
        }
    }

    @Transactional
    public boolean showState(Member member, Long habitId) {
        Habit habit = habitRepository.findById(habitId)
                .orElseThrow(() -> new EntityNotFoundException("게시글 없음"));

        Optional<Good> existing = goodRepository.findByMemberAndHabit(member, habit);
        if (existing.isPresent()) {
            return true;
        } else {
            return false; // 좋아요 등록됨
        }
    }
}
