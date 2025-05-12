package com.belejki.belejki.restful.service;

import com.belejki.belejki.restful.dto.ReminderDto;
import com.belejki.belejki.restful.dto.ReminderPatchDto;
import com.belejki.belejki.restful.entity.Reminder;
import com.belejki.belejki.restful.entity.User;
import com.belejki.belejki.restful.exception.ReminderNotFoundException;
import com.belejki.belejki.restful.exception.UserNotFoundException;
import com.belejki.belejki.restful.mapper.ReminderMapper;
import com.belejki.belejki.restful.repository.ReminderRepository;
import com.belejki.belejki.restful.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReminderService {
    private static final int DAYS_BEFORE_EXPIRE = 7;
    private static final int YEARS_AFTER_EXPIRED = 2;
    private final ReminderRepository reminderRepository;
    private final ReminderMapper reminderMapper;
    private final UserRepository userRepository;

    @Autowired
    public ReminderService(ReminderRepository reminderRepository, ReminderMapper reminderMapper, UserRepository userRepository) {
        this.reminderRepository = reminderRepository;
        this.reminderMapper = reminderMapper;
        this.userRepository = userRepository;
    }


    public ReminderDto save(@Valid ReminderDto reminder, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
        Reminder entity = reminderMapper.toEntity(reminder, user);
        setExpirationFlags(entity);
        Reminder saved = reminderRepository.save(entity);
        reminder.setUserId(saved.getUser().getId());
        reminder.setId(saved.getId());
        return reminder;
    }

    public Reminder saveWithUserId(@Valid ReminderDto dto, Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found for id: " + id));
        Reminder reminder = reminderMapper.toEntity(dto, user);
        setExpirationFlags(reminder);
        return reminderRepository.save(reminder);
    }

    public Reminder findByReminder(Reminder reminder) {
        return reminderRepository.findById(reminder.getId()).orElseThrow(() -> new ReminderNotFoundException("Reminder not found."));
    }

    public Reminder findById(Long id) {
        return reminderRepository.findById(id).orElseThrow(() -> new ReminderNotFoundException("Reminder not found for id: " + id));
    }

    public Page<Reminder> findAllByUser_Username(String username, Pageable pageable) {
        return reminderRepository.findAllByUser_Username(username, pageable);
    }

    public Page<Reminder> findAllByUser_Id(Long userId, Pageable pageable) {
        return reminderRepository.findAllByUser_Id(userId, pageable);
    }

    public Page<Reminder> findAllByExpiredTrueAndUser_Id(Long userId, Pageable pageable) {
        return reminderRepository.findByExpiredTrueAndUser_Id(userId, pageable);
    }

    public Page<Reminder> findAllByExpiredTrueAndUser_Username(String username, Pageable pageable) {
        return reminderRepository.findByExpiredTrueAndUser_Username(username, pageable);
    }

    public Page<Reminder> findAllByExpiresSoonTrueAndUser_Id(Long userId, Pageable pageable) {
        return reminderRepository.findByExpiresSoonTrueAndUser_Id(userId, pageable);
    }

    public Page<Reminder> findAllByExpiresSoonTrueAndUser_Username(String username, Pageable pageable) {
        return reminderRepository.findAllByExpiresSoonTrueAndUser_Username(username, pageable);
    }

    public Page<Reminder> findAllByExpiresTodayTrueAndUser_Id(Long userId, Pageable pageable) {
        return reminderRepository.findAllByExpiresTodayTrueAndUser_Id(userId, pageable);
    }

    public Page<Reminder> findAllByExpiresTodayTrueAndUser_Username(String username, Pageable pageable) {
        return reminderRepository.findAllByExpiresTodayTrueAndUser_Username(username, pageable);
    }

    public List<Reminder> findAllExpiredBefore() {
        LocalDate cutoffDate = LocalDate.now().minusYears(YEARS_AFTER_EXPIRED);
        return reminderRepository.findAllExpiredBefore(cutoffDate);
    }

    public Page<Reminder> findAllByNameContainingAndUser_Username(String name, String username, Pageable pageable) {
        return reminderRepository.findAllByNameContainingAndUser_Username(name, username, pageable);
    }

    public Page<Reminder> findAllByDescriptionContainingAndUser_Username(String descr, String username, Pageable pageable) {
        return reminderRepository.findAllByDescriptionContainingAndUser_Username(descr, username, pageable);
    }


    public Reminder update(Reminder reminder, @Valid ReminderDto dto) {
        User user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new UserNotFoundException("User not found for id: " + dto.getUserId()));
        reminder.setName(dto.getName());
        reminder.setDescription(dto.getDescription());
        reminder.setImportanceLevel(dto.getImportanceLevel());
        reminder.setUser(user);
        reminder.setExpiration(dto.getExpiration());
        reminder.setExpired(dto.isExpired());
        reminder.setExpiresSoon(dto.isExpiresSoon());
        reminder.setExpiresToday(dto.isExpiresToday());
        reminder.setMonthMail(dto.isMonthMail());
        reminder.setWeekMail(dto.isWeekMail());
        reminder.setTodayMail(dto.isTodayMail());

        return reminderRepository.save(reminder);
    }

    public Reminder patchReminder(Long id, @Valid ReminderPatchDto dto) {
        Reminder reminder = findById(id);

        // Update only fields that are provided
        if (dto.getName() != null) {
            reminder.setName(dto.getName());
        }

        if (dto.getDescription() != null) {
            reminder.setDescription(dto.getDescription());
        }

        if (dto.getImportanceLevel() != null) {
            reminder.setImportanceLevel(dto.getImportanceLevel());
        }

        if (dto.getExpiration() != null) {
            reminder.setExpiration(dto.getExpiration());
            setExpirationFlags(reminder);
        }

        return reminderRepository.save(reminder);
    }


    public Reminder delete(Long id) {
        Reminder byId = findById(id);
        reminderRepository.delete(byId);
        return byId;
    }

    public Page<Reminder> deleteAllByUser_Username(String username, Pageable pageable) {
        Page<Reminder> allByUserUsername = findAllByUser_Username(username, pageable);
        reminderRepository.deleteAll(allByUserUsername);
        return allByUserUsername;
    }

    public Page<Reminder> deleteAllByUser_Id(Long id, Pageable pageable) {
        Page<Reminder> allByUserId = findAllByUser_Id(id, pageable);
        reminderRepository.deleteAll(allByUserId);
        return allByUserId;
    }

    private void setExpirationFlags(Reminder reminder) {
        LocalDate now = LocalDate.now();
        LocalDate reminderDate = reminder.getExpiration();
        boolean isLessThanDaysBeforeExpire = now.isAfter(reminderDate.minusDays(DAYS_BEFORE_EXPIRE + 1)); //adding 1 to make it before today
        boolean expired = now.isAfter(reminderDate);
        boolean expiresToday = now.isEqual(reminderDate);

        if (expired) {
            reminder.setExpired(true);
        }

        if (isLessThanDaysBeforeExpire && !reminder.isExpired()) {
            reminder.setExpiresSoon(true);
            if (expiresToday) {
                reminder.setExpiresToday(true);
            }
        }
    }
}
