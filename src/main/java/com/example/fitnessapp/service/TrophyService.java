package com.example.fitnessapp.service;

import com.example.fitnessapp.dto.UserTrophyDTO;
import com.example.fitnessapp.dto.WorkoutSessionDTO;
import com.example.fitnessapp.dto.WorkoutSetDTO;
import com.example.fitnessapp.entity.Trophy;
import com.example.fitnessapp.entity.User;
import com.example.fitnessapp.entity.UserTrophy;
import com.example.fitnessapp.entity.enums.MetricType;
import com.example.fitnessapp.repository.TrophyRepository;
import com.example.fitnessapp.repository.UserTrophyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class TrophyService {
    
    private final TrophyRepository trophyRepository;
    private final UserTrophyRepository userTrophyRepository;

    public TrophyService(TrophyRepository trophyRepository, UserTrophyRepository userTrophyRepository) {
        this.trophyRepository = trophyRepository;
        this.userTrophyRepository = userTrophyRepository;
    }


    @Transactional
    public void evaluateWorkoutTrophies(User user, WorkoutSessionDTO session) {
        List<Trophy> allTrophies = trophyRepository.findAll();

        Double sessionVolume = 0.0;
        Integer sessionSets = 0;

        if(session.getSets() != null) {
            sessionSets = session.getSets().size();
            for (WorkoutSetDTO set : session.getSets()) {
                if (set.getWeight() != null && set.getReps() != null) {
                    sessionVolume += (set.getWeight().doubleValue() * set.getReps());
                }
            }
        }

        for (Trophy trophy : allTrophies) {

            UserTrophy userTrophy = userTrophyRepository.findByUserIdAndTrophyId(user.getId(), trophy.getId())
                    .orElseGet(() -> {
                        UserTrophy newUserTrophy = new UserTrophy();
                        newUserTrophy.setUser(user);
                        newUserTrophy.setTrophy(trophy);
                        newUserTrophy.setCurrentValue(0.0);
                        newUserTrophy.setUnlocked(false);
                        return newUserTrophy;
                    });
            
            if (userTrophy.isUnlocked()) {
                continue; 
            }

            Double newValue = userTrophy.getCurrentValue();

            switch (trophy.getMetricType()) {
                case WORKOUT_COUNT:
                    newValue += 1;
                    break;
                case TOTAL_WEIGHT:
                    newValue += sessionVolume;
                    break;
                case TOTAL_REPS:
                    if(session.getSets() != null) {
                        newValue += session.getSets().stream()
                                .filter(set -> set.getReps() != null)
                                .mapToInt(WorkoutSetDTO::getReps)
                                .sum();
                    }
                    break;
                case TOTAL_SETS:
                    newValue += sessionSets;
                    break;
                case SESSION_VOLUME:
                    if (sessionVolume > newValue) {
                        newValue = sessionVolume;
                    }
                    break;
                case SESSION_SETS:
                    if (sessionSets > newValue) {
                        newValue = sessionSets.doubleValue();
                    }
                    break;
                default:
                    break; 
            }

            userTrophy.setCurrentValue(newValue);

            if (newValue >= trophy.getTargetValue()) {
                userTrophy.setUnlocked(true);
                userTrophy.setUnlockedAt(LocalDateTime.now());

                System.out.println("Congratulations! You've unlocked the trophy: " + trophy.getName());

                
            }
                
            userTrophyRepository.save(userTrophy);

        }

        
    }

    public List<UserTrophyDTO> getUserTrophiesAsDTO(Long userId) {
    return userTrophyRepository.findByUserId(userId).stream().map(ut -> {
        UserTrophyDTO dto = new UserTrophyDTO();
        dto.setTrophyId(ut.getTrophy().getId());
        dto.setName(ut.getTrophy().getName());
        dto.setDescription(ut.getTrophy().getDescription());
        dto.setMetricType(ut.getTrophy().getMetricType());
        dto.setTargetValue(ut.getTrophy().getTargetValue());
        dto.setCurrentValue(ut.getCurrentValue());
        dto.setUnlocked(ut.isUnlocked());
        dto.setUnlockedAt(ut.getUnlockedAt());
        dto.setIconSvg(ut.getTrophy().getIconSvg());
        return dto;
    }).collect(Collectors.toList());
}

}
