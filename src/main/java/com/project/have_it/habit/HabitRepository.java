package com.project.have_it.habit;

import org.springframework.data.jpa.repository.JpaRepository;


public interface HabitRepository extends JpaRepository<Habit, Long> {
}
