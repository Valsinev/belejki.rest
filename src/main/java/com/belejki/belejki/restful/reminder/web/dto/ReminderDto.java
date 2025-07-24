package com.belejki.belejki.restful.reminder.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

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
    @NotNull
    @Future(message = "{reminder.expiration.date.must.be.in.the.future}")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate expiration;
    private boolean expired;
    private boolean expiresSoon;
    private boolean expiresToday;
    private boolean expiresAfterMonth;
    private boolean monthMail;
    private boolean weekMail;
    private boolean todayMail;

}
