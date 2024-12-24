package com.example.belejki.demo.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "reminders")
public class Reminder {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "importance_level")
    private int importanceLevel;
    @Column(name = "reminder_description")
    private String description;
    @Column(name = "expiration")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate expiration;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "user_id")
    private User userId;
    @Column(name = "expired")
    private boolean expired;
    @Column(name = "almost_expired")
    private boolean expiresInNumberOfDays;

    public Reminder() {
    }

    public Reminder(String description, LocalDate expiration, boolean expired, boolean expiresInNumberOfDays, int importanceLevel, String name, User userId) {
        this.description = description;
        this.expiration = expiration;
        this.expired = expired;
        this.expiresInNumberOfDays = expiresInNumberOfDays;
        this.importanceLevel = importanceLevel;
        this.name = name;
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getExpiration() {
        return expiration;
    }

    public void setExpiration(LocalDate expiration) {
        this.expiration = expiration;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public boolean isExpiresInNumberOfDays() {
        return expiresInNumberOfDays;
    }

    public void setExpiresInNumberOfDays(boolean expiresInNumberOfDays) {
        this.expiresInNumberOfDays = expiresInNumberOfDays;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getImportanceLevel() {
        return importanceLevel;
    }

    public void setImportanceLevel(int importanceLevel) {
        this.importanceLevel = importanceLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Reminder reminder = (Reminder) o;
        return importanceLevel == reminder.importanceLevel && expired == reminder.expired && expiresInNumberOfDays == reminder.expiresInNumberOfDays && Objects.equals(id, reminder.id) && Objects.equals(name, reminder.name) && Objects.equals(description, reminder.description) && Objects.equals(expiration, reminder.expiration) && Objects.equals(userId, reminder.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, importanceLevel, description, expiration, userId, expired, expiresInNumberOfDays);
    }
}

