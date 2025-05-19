package com.belejki.belejki.restful.mapper;

import com.belejki.belejki.restful.dto.ReminderDto;
import com.belejki.belejki.restful.dto.ReminderSchedulerDto;
import com.belejki.belejki.restful.entity.Reminder;
import com.belejki.belejki.restful.entity.User;
import org.springframework.stereotype.Component;

@Component
public class ReminderMapper {

    public ReminderDto toDto(Reminder reminder) {
        ReminderDto dto = new ReminderDto();
        dto.setId(reminder.getId());
        dto.setUserId(reminder.getUser().getId());
        dto.setName(reminder.getName());
        dto.setDescription(reminder.getDescription());
        dto.setImportanceLevel(reminder.getImportanceLevel());
        dto.setExpiration(reminder.getExpiration());
        dto.setExpired(reminder.isExpired());
        dto.setExpiresToday(reminder.isExpiresToday());
        dto.setExpiresSoon(reminder.isExpiresSoon());
        dto.setWeekMail(reminder.isWeekMail());
        dto.setMonthMail(reminder.isMonthMail());
        dto.setTodayMail(reminder.isTodayMail());
        return dto;
    }

    public Reminder toEntity(ReminderDto dto, User user) {
        Reminder reminder = new Reminder();
        reminder.setId(dto.getId());
        reminder.setUser(user);
        reminder.setName(dto.getName());
        reminder.setDescription(dto.getDescription());
        reminder.setImportanceLevel(dto.getImportanceLevel());
        reminder.setExpiration(dto.getExpiration());
        reminder.setExpired(dto.isExpired());
        reminder.setExpiresToday(dto.isExpiresToday());
        reminder.setExpiresSoon(dto.isExpiresSoon());
        reminder.setMonthMail(dto.isMonthMail());
        reminder.setWeekMail(dto.isWeekMail());
        reminder.setTodayMail(dto.isTodayMail());
        return reminder;
    }

    public ReminderSchedulerDto toFlagsDto(Reminder reminder) {
        ReminderSchedulerDto flagsDto = new ReminderSchedulerDto();
        flagsDto.setId(reminder.getId());
        flagsDto.setExpiration(reminder.getExpiration());
        flagsDto.setExpired(reminder.isExpired());
        flagsDto.setExpiresAfterMonth(reminder.isExpiresAfterMonth());
        flagsDto.setExpiresSoon(reminder.isExpiresSoon());
        flagsDto.setExpiresToday(reminder.isExpiresToday());
        return flagsDto;
    }

    public ReminderSchedulerDto toSchedulerDto(Reminder reminder) {
        ReminderSchedulerDto dto = new ReminderSchedulerDto();
        dto.setId(reminder.getId());
        dto.setName(reminder.getName());
        dto.setExpiration(reminder.getExpiration());
        dto.setExpired(reminder.isExpired());
        dto.setExpiresSoon(reminder.isExpiresSoon());
        dto.setExpiresToday(reminder.isExpiresToday());
        dto.setExpiresAfterMonth(reminder.isExpiresAfterMonth());
        dto.setUsername(reminder.getUser().getUsername());
        dto.setLocale(reminder.getUser().getLocale());
        dto.setMonthMail(reminder.isMonthMail());
        dto.setWeekMail(reminder.isWeekMail());
        dto.setTodayMail(reminder.isTodayMail());
        return dto;
    }
}
