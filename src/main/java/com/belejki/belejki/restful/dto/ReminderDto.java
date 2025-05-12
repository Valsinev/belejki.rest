package com.belejki.belejki.restful.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotBlank
    private String name;
    @NotNull
    @Min(value = 1, message = "Importance level must be between 1 and 10 number.")
    @Max(value = 10, message = "Importance level must be between 1 and 10 number.")
    private Integer importanceLevel;
    private String description;
    @NotNull
    @JsonFormat(pattern = "dd-MM-yyyy")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate expiration;
    private boolean expired;
    private boolean expiresSoon;
    private boolean expiresToday;
    private boolean monthMail;
    private boolean weekMail;
    private boolean todayMail;
}
