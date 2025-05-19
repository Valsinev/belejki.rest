package com.belejki.belejki.restful.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReminderSchedulerDto {
    private Long id;
    private String name;
    private LocalDate expiration;
    private Boolean expired;
    private Boolean expiresSoon;
    private Boolean expiresToday;
    private Boolean expiresAfterMonth;
    private String username;
    private String locale;
    private Boolean monthMail;
    private Boolean weekMail;
    private Boolean todayMail;
}

