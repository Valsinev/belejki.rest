package com.belejki.belejki.restful.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ReminderDto {

    private Long id;
    private Long userId;
    @NotBlank(message = "Reminder must have name.")
    @Size(min = 2, max = 64, message = "Reminder name name must be between 2 and 64 characters.")
    private String name;
    @NotNull
    @Min(value = 1, message = "Importance level must be between 1 and 10 number.")
    @Max(value = 10, message = "Importance level must be between 1 and 10 number.")
    private Integer importanceLevel;
    @Size(max = 128, message = "Reminder name name must be between 2 and 64 characters.")
    private String description;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")  // Optional if used in REST serialization
    private LocalDate expiration;
    private boolean expired;
    private boolean expiresSoon;
    private boolean expiresToday;
    private boolean monthMail;
    private boolean weekMail;
    private boolean todayMail;
}
