package com.example.fitnessapp.service;

import com.example.fitnessapp.dto.BodyWeightEntryDTO;
import com.example.fitnessapp.entity.BodyWeightEntry;
import com.example.fitnessapp.entity.User;
import com.example.fitnessapp.repository.BodyWeightEntryRepository;
import com.example.fitnessapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BodyWeightEntryService {
    
    private final BodyWeightEntryRepository bodyWeightRepository;
    private final UserRepository userRepository;

    public BodyWeightEntryService(BodyWeightEntryRepository bodyWeightRepository, UserRepository userRepository) {
        this.bodyWeightRepository = bodyWeightRepository;
        this.userRepository = userRepository;
    }

    public BodyWeightEntryDTO logWeight(BodyWeightEntryDTO dto, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

            BodyWeightEntry entry = bodyWeightRepository.findByUserIdAndDate(user.getId(), dto.getDate())
                .orElse(new BodyWeightEntry());
                
            entry.setUser(user);
            entry.setDate(dto.getDate());
            entry.setWeight(dto.getWeight());

            BodyWeightEntry savedEntry = bodyWeightRepository.save(entry);

            user.setCurrentWeight(dto.getWeight());
            userRepository.save(user);

            dto.setId(savedEntry.getId());
            return dto;
        }


    public List<BodyWeightEntryDTO> getMyWeightHistory(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return bodyWeightRepository.findByUserIdOrderByDateDesc(user.getId())
                .stream()
                .map(entry -> {
                    BodyWeightEntryDTO dto = new BodyWeightEntryDTO();
                    dto.setId(entry.getId());
                    dto.setUserId(entry.getUser().getId());
                    dto.setDate(entry.getDate());
                    dto.setWeight(entry.getWeight());
                    return dto;
                })
                .collect(Collectors.toList());
    }

}
