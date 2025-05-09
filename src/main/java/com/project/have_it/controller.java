package com.project.have_it;

import com.project.have_it.habit.Habit;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class controller {
    @GetMapping("/api/menu")
    public ResponseEntity<Map<String,String>> testJson() {
        Map<String, String> menu = new HashMap<>();
        menu.put("habit_list", "/api/habit/list");
        menu.put("habit_", "/api/habit/list");
        return ResponseEntity.ok(menu);
    }
}
