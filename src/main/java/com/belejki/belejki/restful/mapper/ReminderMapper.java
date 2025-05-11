package com.belejki.belejki.restful.mapper;

import com.belejki.belejki.restful.dto.ReminderDto;
import com.belejki.belejki.restful.entity.Reminder;
import com.belejki.belejki.restful.entity.User;
import org.springframework.stereotype.Component;

@Component
public class ReminderMapper {

    public ReminderDto toDto(Reminder reminder, Long userId) {
        ReminderDto dto = new ReminderDto();
        dto.setId(reminder.getId());
        dto.setUserId(userId);
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
}
