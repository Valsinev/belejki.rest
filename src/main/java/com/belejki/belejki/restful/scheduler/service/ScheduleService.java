package com.belejki.belejki.restful.scheduler.service;

import com.belejki.belejki.restful.scheduler.web.dto.ReminderSchedulerDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {
	Page<ReminderSchedulerDto> findAllByExpirationBeforeOrderByExpirationAsc(LocalDate cutoff, Pageable pageable);

	void patchReminderFlags(List<ReminderSchedulerDto> flagsDtos);

	Page<ReminderSchedulerDto> findAllByExpiresSoonTrueOrderByExpirationAsc(Pageable pageable);

	Page<ReminderSchedulerDto> findAllByExpiresTodayTrueOrderByExpirationAsc(Pageable pageable);

	Page<ReminderSchedulerDto> findByExpiresAfterMonthTrueOrderByExpirationAsc(Pageable pageable);
}
