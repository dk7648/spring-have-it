package com.project.have_it.habit;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface HabitRepository extends JpaRepository<Habit, Long> {
    List<Habit> findTop6ByOrderByIdDesc();
}
