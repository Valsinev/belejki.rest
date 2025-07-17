package com.belejki.belejki.restful.config.mappings;

import com.belejki.belejki.restful.reminder.domain.Reminder;
import com.belejki.belejki.restful.reminder.web.dto.ReminderRequestDto;
import com.belejki.belejki.restful.reminder.web.dto.ReminderResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReminderMappingConfig {

	@Autowired
	public void reminderMappingConfigs(ModelMapper modelMapper) {
		modelMapper.createTypeMap(ReminderRequestDto.class, Reminder.class)
				.addMappings(mapping -> {
					mapping.map(ReminderRequestDto::getId, Reminder::setId);
					mapping.map(ReminderRequestDto::getName, Reminder::setName);
					mapping.map(ReminderRequestDto::getDescription, Reminder::setDescription);
					mapping.map(ReminderRequestDto::getExpiration, Reminder::setExpiration);
					mapping.map(ReminderRequestDto::getImportanceLevel, Reminder::setImportanceLevel);
					mapping.map(ReminderRequestDto::isExpired, Reminder::setExpired);
					mapping.map(ReminderRequestDto::isExpiresSoon, Reminder::setExpiresSoon);
					mapping.map(ReminderRequestDto::isExpiresToday, Reminder::setExpiresToday);
					mapping.map(ReminderRequestDto::isExpiresAfterMonth, Reminder::setExpiresAfterMonth);
					mapping.map(ReminderRequestDto::isMonthMail, Reminder::setMonthMail);
					mapping.map(ReminderRequestDto::isWeekMail, Reminder::setWeekMail);
					mapping.map(ReminderRequestDto::isTodayMail, Reminder::setTodayMail);

				});

		modelMapper.createTypeMap(Reminder.class, ReminderRequestDto.class)
				.addMappings(mapping -> {
					mapping.map(Reminder::getId, ReminderRequestDto::setId);
					mapping.map(Reminder::getName, ReminderRequestDto::setName);
					mapping.map(Reminder::getDescription, ReminderRequestDto::setDescription);
					mapping.map(Reminder::getExpiration, ReminderRequestDto::setExpiration);
					mapping.map(Reminder::getImportanceLevel, ReminderRequestDto::setImportanceLevel);
					mapping.map(Reminder::isExpired, ReminderRequestDto::setExpired);
					mapping.map(Reminder::isExpiresSoon, ReminderRequestDto::setExpiresSoon);
					mapping.map(Reminder::isExpiresToday, ReminderRequestDto::setExpiresToday);
					mapping.map(Reminder::isExpiresAfterMonth, ReminderRequestDto::setExpiresAfterMonth);
					mapping.map(Reminder::isMonthMail, ReminderRequestDto::setMonthMail);
					mapping.map(Reminder::isWeekMail, ReminderRequestDto::setWeekMail);
					mapping.map(Reminder::isTodayMail, ReminderRequestDto::setTodayMail);

				});

		modelMapper.createTypeMap(Reminder.class, ReminderResponseDto.class)
				.addMappings(mapping -> {
					mapping.map(Reminder::getId, ReminderResponseDto::setId);
					mapping.map(Reminder::getName, ReminderResponseDto::setName);
					mapping.map(Reminder::getDescription, ReminderResponseDto::setDescription);
					mapping.map(Reminder::getExpiration, ReminderResponseDto::setExpiration);
					mapping.map(Reminder::getImportanceLevel, ReminderResponseDto::setImportanceLevel);
					mapping.map(Reminder::isExpired, ReminderResponseDto::setExpired);
					mapping.map(Reminder::isExpiresSoon, ReminderResponseDto::setExpiresSoon);
					mapping.map(Reminder::isExpiresToday, ReminderResponseDto::setExpiresToday);
					mapping.map(Reminder::isExpiresAfterMonth, ReminderResponseDto::setExpiresAfterMonth);
					mapping.map(Reminder::isMonthMail, ReminderResponseDto::setMonthMail);
					mapping.map(Reminder::isWeekMail, ReminderResponseDto::setWeekMail);
					mapping.map(Reminder::isTodayMail, ReminderResponseDto::setTodayMail);
					mapping.map(reminder -> reminder.getUser().getUsername(), ReminderResponseDto::setUser);

				});
	}
}
