package com.example.fitnessapp.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "user_trophies")
public class UserTrophy {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "trophy_id", nullable = false)
    private Trophy trophy;

    @Column(name = "current_value", nullable = false)
    private Double currentValue;

    @Column(name = "is_unlocked", nullable = false)
    private Boolean isUnlocked;

    @Column(name = "unlocked_at")
    private LocalDateTime unlockedAt;



    public UserTrophy() {}



    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Trophy getTrophy() { return trophy; }
    public void setTrophy(Trophy trophy) { this.trophy = trophy; }

    public Double getCurrentValue() { return currentValue; }
    public void setCurrentValue(Double currentValue) { this.currentValue = currentValue; }

    public Boolean isUnlocked() { return isUnlocked; }
    public void setUnlocked(Boolean unlocked) { this.isUnlocked = unlocked; }

    public LocalDateTime getUnlockedAt() { return unlockedAt; }
    public void setUnlockedAt(LocalDateTime unlockedAt) { this.unlockedAt = unlockedAt; }


}
