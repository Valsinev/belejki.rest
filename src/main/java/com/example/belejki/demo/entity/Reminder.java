package com.example.belejki.demo.entity;


import jakarta.persistence.*;
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
    private User user;
    @Column(name = "expired")
    private boolean expired;
    @Column(name = "expires_soon")
    private boolean expiresSoon;
    @Column(name = "sended_mail")
    private boolean sendedMail;

    public Reminder() {
    }

    public Reminder(String name, String description, LocalDate expiration, boolean expired, boolean expiresSoon, int importanceLevel, User userId, boolean sendedMail) {

        this.name = name;
        this.description = description;
        this.expiration = expiration;
        this.expired = expired;
        this.expiresSoon = expiresSoon;
        this.importanceLevel = importanceLevel;
        this.user = userId;
        this.sendedMail = sendedMail;
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

    public boolean isExpiresSoon() {
        return expiresSoon;
    }

    public void setExpiresSoon(boolean expiresSoon) {
        this.expiresSoon = expiresSoon;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isSendedMail() {
        return sendedMail;
    }

    public void setSendedMail(boolean sendedMail) {
        this.sendedMail = sendedMail;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Reminder reminder = (Reminder) o;
        return importanceLevel == reminder.importanceLevel && expired == reminder.expired && expiresSoon == reminder.expiresSoon && sendedMail == reminder.sendedMail && Objects.equals(id, reminder.id) && Objects.equals(name, reminder.name) && Objects.equals(description, reminder.description) && Objects.equals(expiration, reminder.expiration) && Objects.equals(user, reminder.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, importanceLevel, description, expiration, user, expired, expiresSoon, sendedMail);
    }

    @Override
    public String toString() {
        return "Reminder{" +
                "description='" + description + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", importanceLevel=" + importanceLevel +
                ", expiration=" + expiration +
                ", userId=" + user +
                ", expired=" + expired +
                ", expiresSoon=" + expiresSoon +
                ", sendedMail=" + sendedMail +
                '}';
    }
}

