package com.belejki.belejki.restful.reminder.repository;

import com.belejki.belejki.restful.reminder.domain.Reminder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDate;
import java.util.Optional;

@RepositoryRestResource
public interface ReminderRepository extends JpaRepository<Reminder, Long> {

    Page<Reminder> findAllByExpiredTrueOrderByExpirationAsc(Pageable pageable);

    Page<Reminder> findAllByExpiresSoonTrueOrderByExpirationAsc(Pageable pageable);

    Page<Reminder> findAllByExpiredTrueAndUser_UsernameOrderByExpirationAsc(String username, Pageable pageable);

    Page<Reminder> findAllByExpiresTodayTrueOrderByExpirationAsc(Pageable pageable);

    Page<Reminder> findAllByUser_IdOrderByExpirationAsc(Long userId, Pageable pageable);

    Page<Reminder> findAllByUser_UsernameOrderByExpirationAsc(String username, Pageable pageable);

    Page<Reminder> findAllByExpiredTrueAndUser_IdOrderByExpirationAsc(Long userId, Pageable pageable);

    Page<Reminder> findAllByExpiresSoonTrueAndUser_IdOrderByExpirationAsc(Long userId, Pageable pageable);

    Page<Reminder> findAllByExpiresSoonTrueAndUser_UsernameOrderByExpirationAsc(String username, Pageable pageable);

    Page<Reminder> findAllByExpiresTodayTrueAndUser_IdOrderByExpirationAsc(Long userId, Pageable pageable);

    Page<Reminder> findAllByExpiresTodayTrueAndUser_UsernameOrderByExpirationAsc(String username, Pageable pageable);

    Page<Reminder> findAllByExpirationBeforeOrderByExpirationAsc(LocalDate cutoffDate, Pageable pageable);

    Page<Reminder> findAllByNameContainingAndUser_UsernameOrderByExpirationAsc(String name, String username, Pageable pageable);

    Page<Reminder> findAllByDescriptionContainingAndUser_UsernameOrderByExpirationAsc(String descr, String username, Pageable pageable);

    Optional<Reminder> findByIdAndUser_UsernameOrderByExpirationAsc(Long id, String username);

    Page<Reminder> findByExpiresAfterMonthTrueOrderByExpirationAsc(Pageable pageable);

    Page<Reminder> findAllByExpiredFalseAndExpiresSoonFalseAndUser_UsernameOrderByExpirationAsc(String username, Pageable pageable);

    void deleteAllByUser_Username(String username);

    void deleteAllByUser_Id(Long id);

    void deleteByIdAndUser_Username(Long id, String username);

    void deleteAllByExpirationBefore(LocalDate beforeDate);
}
