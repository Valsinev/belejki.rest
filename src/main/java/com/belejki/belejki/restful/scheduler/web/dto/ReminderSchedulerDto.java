package com.belejki.belejki.restful.scheduler.web.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReminderSchedulerDto {
    private Long id;
    private String name;
    private String userUsername;
    private LocalDate expiration;
    private Boolean expired;
    private Boolean expiresSoon;
    private Boolean expiresToday;
    private Boolean expiresAfterMonth;
    private String locale;
    private Boolean monthMail;
    private Boolean weekMail;
    private Boolean todayMail;
}

