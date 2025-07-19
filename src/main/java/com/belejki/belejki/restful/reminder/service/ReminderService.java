package com.belejki.belejki.restful.reminder.service;

import com.belejki.belejki.restful.reminder.web.dto.ReminderPatchDto;
import com.belejki.belejki.restful.reminder.web.dto.ReminderDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReminderService {
	ReminderDto save(@Valid ReminderDto reminder, String username);

	Page<ReminderDto> findAll(Pageable pageable);

	Page<ReminderDto> findAllByUser_IdOrderByExpirationAsc(Long userId, Pageable pageable);

	Page<ReminderDto> findAllByUser_UsernameOrderByExpirationAsc(String username, Pageable pageable);

	Page<ReminderDto> findAllByExpiredTrueOrderByExpirationAsc(Pageable pageable);

	ReminderDto findByIdAndUser_UsernameOrderByExpirationAsc(Long id, String username);

	Page<ReminderDto> findAllByExpiredFalseAndExpiresSoonFalseAndUser_UsernameOrderByExpirationAsc(String username, Pageable pageable);

	Page<ReminderDto> findAllByExpiredTrueAndUser_UsernameOrderByExpirationAsc(String username, Pageable pageable);

	Page<ReminderDto> findAllByExpiresSoonTrueAndUser_UsernameOrderByExpirationAsc(String username, Pageable pageable);

	Page<ReminderDto> findAllByExpiresTodayTrueAndUser_UsernameOrderByExpirationAsc(String username, Pageable pageable);

	Page<ReminderDto> findAllByNameContainingAndUser_UsernameOrderByExpirationAsc(String name, String username, Pageable pageable);

	Page<ReminderDto> findAllByDescriptionContainingAndUser_UsernameOrderByExpirationAsc(String descr, String username, Pageable pageable);

	ReminderDto updateByIdAndUser_Username(@Valid ReminderDto dto, String username);

	ReminderDto patchReminderByIdAndUser_Username(@Valid ReminderPatchDto dto, String username);

	void deleteById(Long id);

	void deleteAllByUser_Username(String username);

	void deleteAllByUser_Id(Long id);


	void deleteAllExpiredBeforeYears(int years);

	void deleteByIdAndUser_Username(Long id, String username);
}
