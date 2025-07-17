package com.belejki.belejki.restful.reminder.service;

import com.belejki.belejki.restful.reminder.web.dto.ReminderPatchDto;
import com.belejki.belejki.restful.reminder.web.dto.ReminderRequestDto;
import com.belejki.belejki.restful.reminder.web.dto.ReminderResponseDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;

public interface ReminderService {
	ReminderResponseDto save(@Valid ReminderRequestDto reminder, String username);

	Page<ReminderResponseDto> findAll(Pageable pageable);

	Page<ReminderResponseDto> findAllByUser_IdOrderByExpirationAsc(Long userId, Pageable pageable);

	Page<ReminderResponseDto> findAllByUser_UsernameOrderByExpirationAsc(String username, Pageable pageable);

	Page<ReminderResponseDto> findAllByExpiredTrueOrderByExpirationAsc(Pageable pageable);

	ReminderResponseDto findByIdAndUser_UsernameOrderByExpirationAsc(Long id, String username);

	Page<ReminderResponseDto> findAllByExpiredFalseAndExpiresSoonFalseAndUser_UsernameOrderByExpirationAsc(String username, Pageable pageable);

	Page<ReminderResponseDto> findAllByExpiredTrueAndUser_UsernameOrderByExpirationAsc(String username, Pageable pageable);

	Page<ReminderResponseDto> findAllByExpiresSoonTrueAndUser_UsernameOrderByExpirationAsc(String username, Pageable pageable);

	Page<ReminderResponseDto> findAllByExpiresTodayTrueAndUser_UsernameOrderByExpirationAsc(String username, Pageable pageable);

	Page<ReminderResponseDto> findAllByNameContainingAndUser_UsernameOrderByExpirationAsc(String name, String username, Pageable pageable);

	Page<ReminderResponseDto> findAllByDescriptionContainingAndUser_UsernameOrderByExpirationAsc(String descr, String username, Pageable pageable);

	ReminderResponseDto updateByIdAndUser_Username(@Valid ReminderRequestDto dto, String username);

	ReminderResponseDto patchReminderByIdAndUser_Username(@Valid ReminderPatchDto dto, String username);

	void deleteById(Long id);

	void deleteAllByUser_Username(String username);

	void deleteAllByUser_Id(Long id);


	void deleteAllExpiredBeforeYears();

	void deleteByIdAndUser_Username(Long id, String username);
}
