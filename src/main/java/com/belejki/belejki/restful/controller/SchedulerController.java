package com.belejki.belejki.restful.controller;

import com.belejki.belejki.restful.dto.ReminderSchedulerDto;
import com.belejki.belejki.restful.entity.Reminder;
import com.belejki.belejki.restful.mapper.ReminderMapper;
import com.belejki.belejki.restful.repository.ReminderRepository;
import com.belejki.belejki.restful.service.ReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/schedule")
public class SchedulerController {

    private final ReminderRepository reminderRepository;
    private final ReminderMapper reminderMapper;
    private final ReminderService reminderService;

    @Autowired
    public SchedulerController(ReminderRepository reminderRepository, ReminderMapper reminderMapper, ReminderService reminderService) {
        this.reminderRepository = reminderRepository;
        this.reminderMapper = reminderMapper;
        this.reminderService = reminderService;
    }


    @GetMapping("/reminders/flags-before")
    public Page<ReminderSchedulerDto> findAllBeforePassedDate(
            @RequestParam("cutoff") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate cutoff,
            Pageable pageable) {
        Page<Reminder> founded = reminderRepository.findAllByExpirationBefore(cutoff, pageable);
        return founded.map(reminderMapper::toFlagsDto);
    }



    @PutMapping("/reminders/patch")
    public ResponseEntity<Void> patchScheduleReminder(@RequestBody List<ReminderSchedulerDto> flagsDtos) {
        flagsDtos.forEach(dto -> {
            Reminder byId = reminderService.findById(dto.getId());
            if (dto.getName() != null) {
                byId.setDescription(dto.getName());
            }
            if (dto.getExpiration() != null) {
                byId.setExpiration(dto.getExpiration());
            }
            if (dto.getExpired() != null) {
                byId.setExpired(dto.getExpired());
            }
            if (dto.getExpiresAfterMonth() != null) {
                byId.setExpiresAfterMonth(dto.getExpiresAfterMonth());
            }
            if (dto.getExpiresSoon() != null) {
                byId.setExpiresSoon(dto.getExpiresSoon());
            }
            if (dto.getExpiresToday() != null) {
                byId.setExpiresToday(dto.getExpiresToday());
            }
            if (dto.getMonthMail() != null) {
                byId.setMonthMail(dto.getMonthMail());
            }
            if (dto.getWeekMail() != null) {
                byId.setWeekMail(dto.getWeekMail());
            }
            if (dto.getTodayMail() != null) {
                byId.setTodayMail(dto.getTodayMail());
            }
        });

        reminderRepository.flush();
        return ResponseEntity.ok().build();
    }

}
