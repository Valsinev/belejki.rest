package com.belejki.belejki.restful.controller;

import com.belejki.belejki.restful.dto.*;
import com.belejki.belejki.restful.entity.Reminder;
import com.belejki.belejki.restful.entity.User;
import com.belejki.belejki.restful.exception.RecipeNotFoundException;
import com.belejki.belejki.restful.mapper.ReminderMapper;
import com.belejki.belejki.restful.repository.ReminderRepository;
import com.belejki.belejki.restful.service.ReminderService;
import com.belejki.belejki.restful.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import java.util.List;

import static com.belejki.belejki.restful.controller.Utility.checkIfOwnerOrAdmin;

@RestController
public class ReminderController {

    private final ReminderRepository reminderRepository;
    private final ReminderService reminderService;
    private final ReminderMapper reminderMapper;
    private final UserService userService;

    @Autowired
    public ReminderController(ReminderRepository reminderRepository, ReminderService reminderService, ReminderMapper reminderMapper, UserService userService) {
        this.reminderRepository = reminderRepository;
        this.reminderService = reminderService;
        this.reminderMapper = reminderMapper;
        this.userService = userService;
    }


    //region POST METHODS

    @PostMapping("/user/reminders")
    public ReminderDto save(@Valid @RequestBody ReminderDto reminder, Authentication authentication) {
        String username = authentication.getName();
        return reminderService.save(reminder, username);
    }

    //endregion

    //region GET METHODS

    //region ADMIN
    @GetMapping("/admin/reminders")
    public Page<ReminderDto> findAll(Pageable pageable) {
        Page<Reminder> all = reminderRepository.findAll(pageable);
        return all.map(reminder -> reminderMapper.toDto(reminder, reminder.getUser().getId()));
    }

    @GetMapping("/admin/reminders/user/id/{userId}")
    public Page<ReminderDto> findAllByUser_Id(@PathVariable Long userId,
                                              Pageable pageable) {
        Page<Reminder> allByUserId = reminderRepository.findAllByUser_Id(userId, pageable);
        return allByUserId.map(reminder -> reminderMapper.toDto(reminder, reminder.getUser().getId()));
    }

    @GetMapping("/admin/reminders/user/{username}")
    public Page<ReminderDto> findAllByUser_Username(@PathVariable String username,
                                              Pageable pageable) {
        Page<Reminder> allByUserUsername = reminderRepository.findAllByUser_Username(username, pageable);
        return allByUserUsername.map(reminder -> reminderMapper.toDto(reminder, reminder.getUser().getId()));
    }

    @GetMapping("/admin/reminders/expired")
    public Page<ReminderDto> findAllExpired(Pageable pageable) {
        Page<Reminder> byExpiredTrue = reminderRepository.findByExpiredTrue(pageable);
        return byExpiredTrue
                .map(reminder -> reminderMapper.toDto(reminder, reminder.getUser().getId()));
    }


    @GetMapping("/admin/reminders/expires-soon")
    public Page<ReminderDto> findAllExpiresSoon(Pageable pageable) {
        Page<Reminder> byExpiresSoonTrue = reminderRepository.findByExpiresSoonTrue(pageable);
        return byExpiresSoonTrue
                .map(reminder -> reminderMapper.toDto(reminder, reminder.getUser().getId()));
    }

    @GetMapping("/admin/reminders/expires-today")
    public Page<ReminderDto> findAllExpiresToday(Pageable pageable) {
        Page<Reminder> byExpiresTodayTrue = reminderRepository.findByExpiresTodayTrue(pageable);
        return byExpiresTodayTrue
                .map(reminder -> reminderMapper.toDto(reminder, reminder.getUser().getId()));
    }

    //endregion

    //region USER

    @GetMapping("/user/reminders/id/{id}")
    public ReminderDto findByReminder(@PathVariable Long id, Authentication authentication) {
        String username = authentication.getName();
        Reminder founded = reminderRepository.findByIdAndUser_Username(id, username)
                .orElseThrow(() -> new RecipeNotFoundException("Reminder not found for id: " + id));
        return reminderMapper.toDto(founded, founded.getUser().getId());
    }

    @GetMapping("/user/reminders")
    public Page<ReminderDto> findAllOwnReminders(Pageable pageable, Authentication authentication) {
        String username = authentication.getName();
        Page<Reminder> byUserUseId = reminderRepository.findAllByUser_Username(username, pageable);
        return byUserUseId.map(reminder ->
                reminderMapper.toDto(reminder, reminder.getUser().getId()));
    }

    @GetMapping("/user/reminders/expired")
    public Page<ReminderDto> findAllOwnedByExpiredTrueAndUserId(Pageable pageable, Authentication authentication) {
        String username = authentication.getName();
        Page<Reminder> byUserUseId = reminderService.findAllByExpiredTrueAndUser_Username(username, pageable);
        return byUserUseId.map(reminder ->
                reminderMapper.toDto(reminder, reminder.getUser().getId()));
    }

    @GetMapping("/user/reminders/expires-soon")
    public Page<ReminderDto> findAllOwnedByExpiresSoonTrueAndUserId(Pageable pageable, Authentication authentication) {
        String username = authentication.getName();
        Page<Reminder> byUserUseId = reminderService.findAllByExpiresSoonTrueAndUser_Username(username, pageable);
        return byUserUseId.map(reminder ->
                reminderMapper.toDto(reminder, reminder.getUser().getId()));
    }

    @GetMapping("/user/reminders/expires-today")
    public Page<ReminderDto> findAllOwnedByExpiresTodayTrueAndUserUsername(Pageable pageable, Authentication authentication) {
        String username = authentication.getName();
        Page<Reminder> byUserUseId = reminderService.findAllByExpiresTodayTrueAndUser_Username(username, pageable);
        return byUserUseId.map(reminder ->
                reminderMapper.toDto(reminder, reminder.getUser().getId()));
    }


    @GetMapping("/user/reminders/name/{name}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER'")
    public Page<ReminderDto> findAllOwnedByNameContaining(@PathVariable String name,
                                                          Pageable pageable,
                                                          Authentication authentication) {
        String username = authentication.getName();
        Page<Reminder> byNameContainingAndUser = reminderService.findAllByNameContainingAndUser_Username(name, username, pageable);
        return byNameContainingAndUser.map(reminder ->
                reminderMapper.toDto(reminder, reminder.getUser().getId()));
    }

    @GetMapping("/user/reminders/description/{descr}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER'")
    public Page<ReminderDto> findAllOwnedByDescriptionContaining(@PathVariable String descr,
                                                                 Pageable pageable,
                                                                 Authentication authentication) {
        String username = authentication.getName();
        Page<Reminder> byNameContainingAndUser = reminderService.findAllByDescriptionContainingAndUser_Username(descr, username, pageable);
        return byNameContainingAndUser.map(reminder ->
                reminderMapper.toDto(reminder, reminder.getUser().getId()));
    }


    //endregion

    //endregion


    //region PUT METHODS

    @PutMapping("/user/reminders/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER'")
    public ReminderDto updateReminderById(@PathVariable Long id,
                                           @Valid @RequestBody ReminderDto dto,
                                          Authentication authentication) {
        User user = userService.findById(dto.getUserId());
        boolean access = checkIfOwnerOrAdmin(authentication, user.getUsername());
        if (!access) {
            throw new AccessDeniedException("Only owner or admin can update this reminder.");
        }
        Reminder byId = reminderService.findById(id);
        Reminder updated = reminderService.update(byId, dto);
        return reminderMapper.toDto(updated, updated.getUser().getId());
    }

    //endregion

    //region PATCH METHODS
    @PatchMapping("/user/reminders/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER'")
    public ReminderDto patchUser(
            @PathVariable Long id,
            @Valid @RequestBody ReminderPatchDto dto,
            Authentication authentication) {
        User user = userService.findById(dto.getUserId());
        boolean access = checkIfOwnerOrAdmin(authentication, user.getUsername());
        if (!access) {
            throw new AccessDeniedException("Only owner or admin can update this reminder.");
        }

        Reminder patchedReminder = reminderService.patchReminder(id, dto);
        return reminderMapper.toDto(patchedReminder, patchedReminder.getUser().getId());
    }


    //endregion

    //region DELETE METHODS


    @DeleteMapping("/admin/reminders/id/{id}")
    public ResponseEntity<ReminderDto> deleteById(@PathVariable Long id) {
        Reminder byId = reminderService.delete(id);
        return ResponseEntity.ok(reminderMapper.toDto(byId, id));
    }

    @DeleteMapping("/admin/reminders/user/{username}")
    public ResponseEntity<List<ReminderDto>> deleteAllByUser_Username(@PathVariable String username, Pageable pageable) {
        Page<Reminder> reminders = reminderService.deleteAllByUser_Username(username, pageable);
        List<ReminderDto> dto = reminders.stream().map(reminder ->
                reminderMapper.toDto(reminder, reminder.getUser().getId())).toList();
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/admin/reminders/user/id/{id}")
    public ResponseEntity<List<ReminderDto>> deleteAllByUser_Username(@PathVariable Long id, Pageable pageable) {
        Page<Reminder> reminders = reminderService.deleteAllByUser_Id(id, pageable);
        List<ReminderDto> dto = reminders.stream().map(reminder ->
                reminderMapper.toDto(reminder, reminder.getUser().getId())).toList();
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/admin/reminders/clear")
    public ResponseEntity<List<ReminderDto>> deleteAllExpiredInYears(Pageable pageable) {
        List<Reminder> expiredBeforeYears = reminderService.findAllExpiredBefore();
        List<ReminderDto> dto = expiredBeforeYears.stream().map(reminder ->
                reminderMapper.toDto(reminder, reminder.getUser().getId())).toList();
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/user/reminders/id/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER'")
    public ResponseEntity<ReminderDto> deleteWithUserById(@PathVariable Long id, Authentication authentication) {
        Reminder byId = reminderService.findById(id);
        boolean access = checkIfOwnerOrAdmin(authentication, byId.getUser().getUsername());
        if (!access) {
            throw new AccessDeniedException("Only owner or admin can delete this reminder.");
        }
        reminderService.delete(id);
        return ResponseEntity.ok(reminderMapper.toDto(byId, byId.getUser().getId()));
    }

    //endregion



}
