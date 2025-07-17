package com.belejki.belejki.restful.scheduler.service;

import com.belejki.belejki.restful.reminder.domain.Reminder;
import com.belejki.belejki.restful.reminder.repository.ReminderRepository;
import com.belejki.belejki.restful.reminder.service.ReminderServiceImpl;
import com.belejki.belejki.restful.scheduler.web.dto.ReminderSchedulerDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

	private final ReminderRepository reminderRepository;
	private final ModelMapper modelMapper;
	private final ReminderServiceImpl reminderService;

	@Autowired
	public ScheduleServiceImpl(ReminderRepository reminderRepository, ModelMapper modelMapper, ReminderServiceImpl reminderService) {
		this.reminderRepository = reminderRepository;
		this.modelMapper = modelMapper;
		this.reminderService = reminderService;
	}

	@Override
	public Page<ReminderSchedulerDto> findAllByExpirationBeforeOrderByExpirationAsc(LocalDate cutoff, Pageable pageable) {
		Page<Reminder> founded = reminderRepository.findAllByExpirationBeforeOrderByExpirationAsc(cutoff, pageable);
		return founded.map(reminder -> modelMapper.map(reminder, ReminderSchedulerDto.class));
	}

	@Override
	public void patchReminderFlags(List<ReminderSchedulerDto> flagsDtos) {

		flagsDtos.forEach(reminderService::patchScheduleReminder);
	}

	@Override
	public Page<ReminderSchedulerDto> findAllByExpiresSoonTrueOrderByExpirationAsc(Pageable pageable) {
		Page<Reminder> allByExpiresTodayTrueOrderByExpirationAsc = reminderRepository.findAllByExpiresSoonTrueOrderByExpirationAsc(pageable);
		return allByExpiresTodayTrueOrderByExpirationAsc.map(reminder -> modelMapper.map(reminder, ReminderSchedulerDto.class));
	}

	@Override
	public Page<ReminderSchedulerDto> findAllByExpiresTodayTrueOrderByExpirationAsc(Pageable pageable) {
		Page<Reminder> allByExpiresTodayTrueOrderByExpirationAsc = reminderRepository.findAllByExpiresTodayTrueOrderByExpirationAsc(pageable);
		return allByExpiresTodayTrueOrderByExpirationAsc.map(reminder -> modelMapper.map(reminder, ReminderSchedulerDto.class));
	}

	@Override
	public Page<ReminderSchedulerDto> findByExpiresAfterMonthTrueOrderByExpirationAsc(Pageable pageable) {
		Page<Reminder> allByExpiresAfterMonthTrueOrderByExpirationAsc = reminderRepository.findByExpiresAfterMonthTrueOrderByExpirationAsc(pageable);
		return allByExpiresAfterMonthTrueOrderByExpirationAsc.map(reminder -> modelMapper.map(reminder, ReminderSchedulerDto.class));
	}
}
