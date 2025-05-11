package com.belejki.belejki.restful.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "reminders")
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @Column(name = "expired")
    private boolean expired;
    @Column(name = "expires_soon")
    private boolean expiresSoon; //flag if reminder expires in 'n' number of days
    @Column(name = "expires_today")
    private boolean expiresToday;
    @Column(name = "month_mail")
    private boolean monthMail; //flag if mail is sended month before expires
    @Column(name = "week_mail")
    private boolean weekMail; //flag if mail is sended week before expires
    @Column(name = "today_mail")
    private boolean todayMail; //flag if mail is sended day before expires

    public Reminder(String name, String description, LocalDate expiration, int importanceLevel) {

        this.name = name;
        this.description = description;
        this.expiration = expiration;
        this.expired = false;
        this.expiresSoon = false;
        this.importanceLevel = importanceLevel;
        this.monthMail = false;
        this.weekMail = false;
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

    public boolean isMonthMail() {
        return monthMail;
    }

    public void setMonthMail(boolean monthMail) {
        this.monthMail = monthMail;
    }

    public boolean isWeekMail() {
        return weekMail;
    }

    public void setWeekMail(boolean weekMail) {
        this.weekMail = weekMail;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isExpiresToday() {
        return expiresToday;
    }

    public void setExpiresToday(boolean expiresToday) {
        this.expiresToday = expiresToday;
    }

    public boolean isTodayMail() {
        return todayMail;
    }

    public void setTodayMail(boolean todayMail) {
        this.todayMail = todayMail;
    }
}

