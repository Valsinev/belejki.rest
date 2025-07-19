package com.belejki.belejki.restful.scheduler.web;

import com.belejki.belejki.restful.scheduler.service.ScheduleService;
import com.belejki.belejki.restful.scheduler.web.dto.ReminderSchedulerDto;
import com.belejki.belejki.restful.shared.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/schedule/reminders")
public class SchedulerController {

   private final ScheduleService scheduleService;
   private final AuthService authService;

   @Autowired
	public SchedulerController(ScheduleService scheduleService, AuthService authService) {
		this.scheduleService = scheduleService;
	   this.authService = authService;
   }


	@GetMapping("/flags-before")
    public ResponseEntity<Page<ReminderSchedulerDto>> findAllBeforePassedDate(
            @RequestParam("cutoff") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate cutoff,
            Authentication authentication,
            Pageable pageable) {
        boolean admin = authService.isAdmin(authentication);
        if (!admin) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        Page<ReminderSchedulerDto> founded = scheduleService.findAllByExpirationBeforeOrderByExpirationAsc(cutoff, pageable);
        return ResponseEntity.ok(founded);
    }



    //The idea of this method is to patch expiration flags when the scheduler sets them according to the expiration date
    //it just updates expiration flags
    @PatchMapping("/patch")
    public ResponseEntity<Void> patchScheduleReminder(@RequestBody List<ReminderSchedulerDto> flagsDtos, Authentication authentication) {
       boolean admin = authService.isAdmin(authentication);
        if (!admin) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        scheduleService.patchReminderFlags(flagsDtos);

        return ResponseEntity.ok().build();
    }



    @GetMapping("/expires-soon")
    public ResponseEntity<Page<ReminderSchedulerDto>> findAllExpiresSoon(Pageable pageable, Authentication authentication) {
        boolean admin = authService.isAdmin(authentication);
        if (!admin) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        Page<ReminderSchedulerDto> byExpiresSoonTrue = scheduleService.findAllByExpiresSoonTrueOrderByExpirationAsc(pageable);
        return ResponseEntity.ok(byExpiresSoonTrue);
    }

    @GetMapping("/expires-today")
    public ResponseEntity<Page<ReminderSchedulerDto>> findAllExpiresToday(Pageable pageable, Authentication authentication) {
        boolean admin = authService.isAdmin(authentication);
        if (!admin) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Page<ReminderSchedulerDto> byExpiresTodayTrue = scheduleService.findAllByExpiresTodayTrueOrderByExpirationAsc(pageable);
        return ResponseEntity.ok(byExpiresTodayTrue);
    }


    @GetMapping("/expires-month")
    public ResponseEntity<Page<ReminderSchedulerDto>> findAllExpiresAfterMonth(Pageable pageable, Authentication authentication) {
        boolean admin = authService.isAdmin(authentication);
        if (!admin) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Page<ReminderSchedulerDto> byExpiresAfterMonthTrue = scheduleService.findByExpiresAfterMonthTrueOrderByExpirationAsc(pageable);
        return ResponseEntity.ok(byExpiresAfterMonthTrue);
    }

}
