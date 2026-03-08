package com.example.fitnessapp.controllers;

import com.example.fitnessapp.dto.BodyWeightEntryDTO;
import com.example.fitnessapp.service.BodyWeightEntryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/body-weight")
public class BodyWeightEntryController {
    
    private final BodyWeightEntryService bodyWeightService;

    public BodyWeightEntryController(BodyWeightEntryService bodyWeightService) {
        this.bodyWeightService = bodyWeightService;
    }


    @PostMapping
    public ResponseEntity<BodyWeightEntryDTO> logWeight(@RequestBody BodyWeightEntryDTO dto) {
        return ResponseEntity.ok(bodyWeightService.logWeight(dto));
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BodyWeightEntryDTO>> getWeightHistory(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(bodyWeightService.getWeightHistory(userId));
    }

}
