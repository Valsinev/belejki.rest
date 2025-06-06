package com.belejki.belejki.restful.repository;

import com.belejki.belejki.restful.entity.Reminder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface ReminderRepository extends JpaRepository<Reminder, Long> {

    Page<Reminder> findByExpiredTrue(Pageable pageable);

    Page<Reminder> findByExpiresSoonTrue(Pageable pageable);

    Page<Reminder> findByExpiredTrueAndUser_Username(String username, Pageable pageable);

    Page<Reminder> findByExpiresTodayTrue(Pageable pageable);

    Page<Reminder> findAllByUser_Id(Long userId, Pageable pageable);

    Page<Reminder> findAllByUser_Username(String username, Pageable pageable);

    Page<Reminder> findByExpiredTrueAndUser_Id(Long userId, Pageable pageable);

    Page<Reminder> findByExpiresSoonTrueAndUser_Id(Long userId, Pageable pageable);

    Page<Reminder> findAllByExpiresSoonTrueAndUser_Username(String username, Pageable pageable);

    Page<Reminder> findAllByExpiresTodayTrueAndUser_Id(Long userId, Pageable pageable);

    Page<Reminder> findAllByExpiresTodayTrueAndUser_Username(String username, Pageable pageable);

    @Query("SELECT r FROM Reminder r WHERE r.expiration < :cutoffDate")
    List<Reminder> findAllExpiredBefore(@Param("cutoffDate") LocalDate cutoffDate);

    Page<Reminder> findAllByNameContainingAndUser_Username(String name, String username, Pageable pageable);

    Page<Reminder> findAllByDescriptionContainingAndUser_Username(String descr, String username, Pageable pageable);

    Optional<Reminder> findByIdAndUser_Username(Long id, String username);

    Page<Reminder> findByExpiresAfterMonthTrue(Pageable pageable);

    Page<Reminder> findAllByExpirationBefore(LocalDate cutoff, Pageable pageable);
}
