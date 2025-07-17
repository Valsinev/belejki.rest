package com.belejki.belejki.restful.reminder.web.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReminderPatchDto {

	@NotNull
	private Long id;
	private String name;
	private Integer importanceLevel;
	private String description;
	private LocalDate expiration;
	private boolean expired;
	private boolean expiresSoon;
	private boolean expiresToday;
	private boolean expiresAfterMonth;
	private boolean monthMail;
	private boolean weekMail;
	private boolean todayMail;
}
