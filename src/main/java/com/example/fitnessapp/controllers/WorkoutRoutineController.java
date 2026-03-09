package com.example.fitnessapp.controllers;

import org.springframework.web.bind.annotation.*;
import com.example.fitnessapp.service.WorkoutRoutineService;
import com.example.fitnessapp.dto.WorkoutRoutineDTO;
import org.springframework.http.ResponseEntity;
import com.example.fitnessapp.security.SecurityUtils;

import java.util.List;

@RestController
@RequestMapping("/api/routines")
public class WorkoutRoutineController {

    private final WorkoutRoutineService routineService;

    public WorkoutRoutineController(WorkoutRoutineService routineService) {
        this.routineService = routineService;
    }

    @PostMapping
    public ResponseEntity<WorkoutRoutineDTO> createRoutine(@RequestBody WorkoutRoutineDTO dto) {
        String userEmail = SecurityUtils.getCurrentUserEmail();
        return ResponseEntity.ok(routineService.createRoutine(dto, userEmail));
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkoutRoutineDTO> getRoutine(@PathVariable Long id) {
        return ResponseEntity.ok(routineService.getRoutineById(id));
    }

    @GetMapping("/me")
    public ResponseEntity<List<WorkoutRoutineDTO>> getMyRoutines() {
        String userEmail = SecurityUtils.getCurrentUserEmail();
        return ResponseEntity.ok(routineService.getMyRoutines(userEmail));
    }

}
