package com.example.fitnessapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.fitnessapp.entity.BodyWeightEntry;

import java.util.List;

@Repository
public interface BodyWeightEntryRepository extends JpaRepository<BodyWeightEntry, Long> {
    
    // Gets weight history, newest first
    List<BodyWeightEntry> findByUserIdOrderByDateDesc(Long userId);

}
