package com.project.have_it.habit;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class HabitController {
    private final HabitRepository habitRepository;


    @GetMapping("/api/habit/list")
    ResponseEntity<List<Habit>> getHabits() {
        List<Habit> result = habitRepository.findAll();
        System.out.println("Fetched habits: " + result);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/api/habit/detail/{id}")
    ResponseEntity<Habit> detail(@PathVariable Long id, Model model) {
        //List<Commets> comments = commentRepository.findAllByParentId(id);
        Optional<Habit> result = habitRepository.findById(id);
        if (result.isPresent()) {
            return ResponseEntity.ok(result.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
