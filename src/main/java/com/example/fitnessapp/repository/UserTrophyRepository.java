package com.example.fitnessapp.repository;

import com.example.fitnessapp.entity.UserTrophy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserTrophyRepository extends JpaRepository<UserTrophy, Long> {
    
    List<UserTrophy> findByUserId(Long userId);
    Optional<UserTrophy> findByUserIdAndTrophyId(Long userId, Long trophyId);

}
