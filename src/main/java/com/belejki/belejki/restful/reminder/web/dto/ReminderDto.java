package com.belejki.belejki.restful.reminder.web.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ReminderDto {

    private Long id;
    private String userUsername;
    @NotBlank(message = "Reminder name cannot be blank.")
    private String name;
    @Min(value = 1, message = "Minimal value is 1")
    @Max(value = 10, message = "Maximal value is 10")
    private Integer importanceLevel;
    private String description;
    @NotNull(message = "Reminder expiration cannot be empty.")
    @FutureOrPresent(message = "Reminder expiration date must be in present or future.")
    private LocalDate expiration;
    private boolean expired;
    private boolean expiresSoon;
    private boolean expiresToday;
    private boolean expiresAfterMonth;
    private boolean monthMail;
    private boolean weekMail;
    private boolean todayMail;

}
