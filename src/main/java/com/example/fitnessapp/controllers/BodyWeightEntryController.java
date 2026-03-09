package com.example.fitnessapp.controllers;

import com.example.fitnessapp.dto.BodyWeightEntryDTO;
import com.example.fitnessapp.service.BodyWeightEntryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.fitnessapp.security.SecurityUtils;

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
        String userEmail = SecurityUtils.getCurrentUserEmail();
        return ResponseEntity.ok(bodyWeightService.logWeight(dto, userEmail));
    }


    @GetMapping("/me")
    public ResponseEntity<List<BodyWeightEntryDTO>> getMyWeightHistory() {
        String userEmail = SecurityUtils.getCurrentUserEmail();
        return ResponseEntity.ok(bodyWeightService.getMyWeightHistory(userEmail));
    }

}
