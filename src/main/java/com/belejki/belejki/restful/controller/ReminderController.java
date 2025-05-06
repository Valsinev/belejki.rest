package com.belejki.belejki.restful.controller;

import com.belejki.belejki.restful.entity.Reminder;
import com.belejki.belejki.restful.entity.User;
import com.belejki.belejki.restful.repository.ReminderRepository;
import com.belejki.belejki.restful.service.ReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReminderController {

    private ReminderRepository reminderRepository;
    private ReminderService reminderService;

    @Autowired
    public ReminderController(ReminderRepository reminderRepository, ReminderService reminderService) {
        this.reminderRepository = reminderRepository;
        this.reminderService = reminderService;
    }

    //region GET METHODS

    @GetMapping("/admin/reminders")
    public Page<Reminder> findAll(Pageable pageable) {
        return reminderRepository.findAll(pageable);
    }

    @GetMapping("/admin/reminders/expired")
    public Page<Reminder> findAllExpired(Pageable pageable) {
        return reminderRepository.findByExpiredTrue(pageable);
    }


    @GetMapping("/admin/reminders/expires-soon")
    public Page<Reminder> findAllExpiresSoon(Pageable pageable) {
        return reminderRepository.findByExpiresSoonTrue(pageable);
    }


    @GetMapping("/{username}/reminders")
    public Page<Reminder> findAllByUser_Username(@PathVariable String username, Pageable pageable) {
        return reminderRepository.findByUser_Username(username, pageable);
    }

    @GetMapping("/user/reminders")
    public Page<Reminder> findAllByUser(@RequestBody User user, Pageable pageable) {
        return reminderRepository.findByUser(user, pageable);
    }

    @GetMapping("/user/reminders/expired")
    public Page<Reminder> findAllExpiredByUser(@RequestBody User user, Pageable pageable) {
        return reminderRepository.findByExpiredTrueAndUser(user, pageable);
    }


    @GetMapping("/{username}/reminders/expired")
    public Page<Reminder> findAllExpiredByUser_Username(@PathVariable String username, Pageable pageable) {
        return reminderRepository.findByExpiredTrueAndUser_Username(username, pageable);
    }


    @GetMapping("/user/reminders/expires-soon")
    public Page<Reminder> findAllExpiresSoonByUser(@RequestBody User user, Pageable pageable) {
        return reminderRepository.findByExpiresSoonTrueAndUser(user, pageable);
    }

    @GetMapping("/{username}/reminders/expires-soon")
    public Page<Reminder> findAllExpiresSoonByUser_Username(@PathVariable String username, Pageable pageable) {
        return reminderRepository.findByExpiresSoonTrueAndUser_Username(username, pageable);
    }

    @GetMapping("/user/reminders/{username}")
    public Page<Reminder> findAllByNameContainingAndUser_Username(@RequestParam String name, @PathVariable String username, Pageable pageable) {
        return reminderRepository.findByNameContainingIgnoreCaseAndUser_Username(name, username, pageable);
    }

    //endregion

    //region POST METHODS

    @PostMapping("/user/reminders")
    public Reminder save(@RequestBody Reminder reminder) {
        return reminderRepository.save(reminder);
    }
    //endregion

    //region DELETE METHODS

    @DeleteMapping("/user/reminders")
    public ResponseEntity delete(@RequestBody Reminder reminder) {
        reminderRepository.delete(reminder);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/admin/reminders/id/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        reminderRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/admin/reminders/user")
    public ResponseEntity deleteAllByUser(@RequestBody User user) {
        List<Reminder> byUser = reminderRepository.findByUser(user);
        reminderRepository.deleteAll(byUser);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/admin/reminders/{username}")
    public ResponseEntity deleteAllByUser_Username(@PathVariable String username) {
        List<Reminder> byUserUsername = reminderRepository.findByUser_Username(username);
        reminderRepository.deleteAll(byUserUsername);
        return ResponseEntity.noContent().build();
    }


    //endregion



}
