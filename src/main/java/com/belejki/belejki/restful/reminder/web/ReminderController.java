package com.belejki.belejki.restful.reminder.web;

import com.belejki.belejki.restful.reminder.service.ReminderService;
import com.belejki.belejki.restful.reminder.web.dto.ReminderPatchDto;
import com.belejki.belejki.restful.reminder.web.dto.ReminderRequestDto;
import com.belejki.belejki.restful.reminder.web.dto.ReminderResponseDto;
import com.belejki.belejki.restful.shared.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@RestController
public class ReminderController {

	private final ReminderService reminderService;
	private final AuthService authService;

	@Autowired
	public ReminderController(ReminderService reminderService, AuthService authService) {
		this.reminderService = reminderService;
		this.authService = authService;
	}


	//region POST METHODS

	@PostMapping("/user/reminders")
	public ResponseEntity<ReminderResponseDto> save(@Valid @RequestBody ReminderRequestDto reminder,
	                                                BindingResult bindingResult,
	                                                Authentication authentication) {
		if (bindingResult.hasErrors()) {
			return ResponseEntity.badRequest().build();
		}
		String username = authentication.getName();
		ReminderResponseDto saved = reminderService.save(reminder, username);
		return ResponseEntity.ok(saved);
	}

	//endregion

	//region GET METHODS

	//region ADMIN
	@GetMapping("/admin/reminders")
	public ResponseEntity<Page<ReminderResponseDto>> findAll(Pageable pageable, Authentication authentication) {
		boolean admin = authService.isAdmin(authentication);
		if (!admin) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		Page<ReminderResponseDto> all = reminderService.findAll(pageable);
		return ResponseEntity.ok(all);
	}

	@GetMapping("/admin/reminders/user/id/{userId}")
	public ResponseEntity<Page<ReminderResponseDto>> findAllByUser_Id(@PathVariable Long userId,
	                                                                 Pageable pageable,
	                                                                 Authentication authentication) {
		boolean admin = authService.isAdmin(authentication);
		if (!admin) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		Page<ReminderResponseDto> allByUserId = reminderService.findAllByUser_IdOrderByExpirationAsc(userId, pageable);
		return ResponseEntity.ok(allByUserId);
	}

	@GetMapping("/admin/reminders/user/{username}")
	public ResponseEntity<Page<ReminderResponseDto>> findAllByUser_Username(@PathVariable String username,
	                                                                       Pageable pageable,
	                                                                       Authentication authentication) {
		boolean admin = authService.isAdmin(authentication);
		if (!admin) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		Page<ReminderResponseDto> allByUserUsername = reminderService.findAllByUser_UsernameOrderByExpirationAsc(username, pageable);
		return ResponseEntity.ok(allByUserUsername);
	}

	@GetMapping("/admin/reminders/expired")
	public ResponseEntity<Page<ReminderResponseDto>> findAllExpired(Authentication authentication, Pageable pageable) {
		boolean admin = authService.isAdmin(authentication);
		if (!admin) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		Page<ReminderResponseDto> byExpiredTrue = reminderService.findAllByExpiredTrueOrderByExpirationAsc(pageable);
		return ResponseEntity.ok(byExpiredTrue);
	}


	//endregion

	//region USER

	@GetMapping("/user/reminders/id/{id}")
	public ResponseEntity<ReminderResponseDto> findByReminder(@PathVariable Long id, Authentication authentication) {
		String username = authentication.getName();
		ReminderResponseDto founded = reminderService.findByIdAndUser_UsernameOrderByExpirationAsc(id, username);
		return ResponseEntity.ok(founded);
	}

	@GetMapping("/user/reminders")
	public ResponseEntity<Page<ReminderResponseDto>> findAllOwnReminders(Pageable pageable, Authentication authentication) {
		String username = authentication.getName();
		Page<ReminderResponseDto> byUserUseId = reminderService.findAllByUser_UsernameOrderByExpirationAsc(username, pageable);
		return ResponseEntity.ok(byUserUseId);
	}

	@GetMapping("/user/reminders/not-soon")
	public ResponseEntity<Page<ReminderResponseDto>> findAllOwnedRemindersNotExpiredAndNotExpiredSoon(Pageable pageable, Authentication authentication) {
		String username = authentication.getName();
		Page<ReminderResponseDto> byUserUseId = reminderService.findAllByExpiredFalseAndExpiresSoonFalseAndUser_UsernameOrderByExpirationAsc(username, pageable);
		return ResponseEntity.ok(byUserUseId);
	}


	@GetMapping("/user/reminders/expired")
	public ResponseEntity<Page<ReminderResponseDto>> findAllOwnedByExpiredTrueAndUserId(Pageable pageable, Authentication authentication) {
		String username = authentication.getName();
		Page<ReminderResponseDto> byUserUseId = reminderService.findAllByExpiredTrueAndUser_UsernameOrderByExpirationAsc(username, pageable);
		return ResponseEntity.ok(byUserUseId);
	}

	@GetMapping("/user/reminders/expires-soon")
	public ResponseEntity<Page<ReminderResponseDto>> findAllOwnedByExpiresSoonTrueAndUserId(Pageable pageable, Authentication authentication) {
		String username = authentication.getName();
		Page<ReminderResponseDto> byUserUseId = reminderService.findAllByExpiresSoonTrueAndUser_UsernameOrderByExpirationAsc(username, pageable);
		return ResponseEntity.ok(byUserUseId);
	}

	@GetMapping("/user/reminders/expires-today")
	public ResponseEntity<Page<ReminderResponseDto>> findAllOwnedByExpiresTodayTrueAndUserUsername(Pageable pageable, Authentication authentication) {
		String username = authentication.getName();
		Page<ReminderResponseDto> byUserUseId = reminderService.findAllByExpiresTodayTrueAndUser_UsernameOrderByExpirationAsc(username, pageable);
		return ResponseEntity.ok(byUserUseId);
	}


	@GetMapping("/user/reminders/name/{name}")
	public ResponseEntity<Page<ReminderResponseDto>> findAllOwnedByNameContaining(@PathVariable String name,
	                                                                             Pageable pageable,
	                                                                             Authentication authentication) {
		String username = authentication.getName();
		Page<ReminderResponseDto> byNameContainingAndUser = reminderService.findAllByNameContainingAndUser_UsernameOrderByExpirationAsc(name, username, pageable);
		return ResponseEntity.ok(byNameContainingAndUser);
	}

	@GetMapping("/user/reminders/description/{descr}")
	public ResponseEntity<Page<ReminderResponseDto>> findAllOwnedByDescriptionContaining(@PathVariable String descr,
	                                                                                    Pageable pageable,
	                                                                                    Authentication authentication) {
		String username = authentication.getName();
		Page<ReminderResponseDto> byNameContainingAndUser = reminderService.findAllByDescriptionContainingAndUser_UsernameOrderByExpirationAsc(descr, username, pageable);
		return ResponseEntity.ok(byNameContainingAndUser);
	}


	//endregion

	//endregion


	//region PUT METHODS

	@PutMapping("/user/reminders")
	public ResponseEntity<ReminderResponseDto> updateReminderById(@Valid @RequestBody ReminderRequestDto dto,
																 BindingResult bindingResult,
	                                                             Authentication authentication) {

		if (bindingResult.hasErrors()) {
			return ResponseEntity.badRequest().build();
		}

		String username = authentication.getName();
		ReminderResponseDto updated = reminderService.updateByIdAndUser_Username(dto, username);
		return ResponseEntity.ok(updated);
	}


	//endregion

	//region PATCH METHODS
	@PatchMapping("/user/reminders")
	public ResponseEntity<ReminderResponseDto> patchUser(@Valid @RequestBody ReminderPatchDto dto,
	                                                     BindingResult bindingResult,
	                                                     Authentication authentication) {

		if (bindingResult.hasErrors()) {
			return ResponseEntity.badRequest().build();
		}
		String username = authentication.getName();

		ReminderResponseDto patchedReminder = reminderService.patchReminderByIdAndUser_Username(dto, username);
		return ResponseEntity.ok(patchedReminder);
	}

	//endregion

	//region DELETE METHODS


	@DeleteMapping("/admin/reminders/id/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable Long id, Authentication authentication) {
		boolean admin = authService.isAdmin(authentication);
		if (!admin) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		reminderService.deleteById(id);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/admin/reminders/user/{username}")
	public ResponseEntity<Void> deleteAllByUser_Username(@PathVariable String username, Authentication authentication) {
		boolean admin = authService.isAdmin(authentication);
		if (!admin) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		reminderService.deleteAllByUser_Username(username);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/admin/reminders/user/id/{id}")
	public ResponseEntity<Void> deleteAllByUser_Username(@PathVariable Long id, Authentication authentication) {
		boolean admin = authService.isAdmin(authentication);
		if (!admin) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		reminderService.deleteAllByUser_Id(id);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/admin/reminders/clear")
	public ResponseEntity<Void> deleteAllExpiredInYears(Authentication authentication) {
		boolean admin = authService.isAdmin(authentication);
		if (!admin) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		reminderService.deleteAllExpiredBeforeYears();
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/user/reminders/id/{id}")
	public ResponseEntity<Void> deleteWithUserById(@PathVariable Long id, Authentication authentication) {
		String username = authentication.getName();
		reminderService.deleteByIdAndUser_Username(id, username);
		return ResponseEntity.ok().build();
	}

	//endregion


}
