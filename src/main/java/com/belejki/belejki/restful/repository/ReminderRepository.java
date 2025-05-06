package com.belejki.belejki.restful.repository;

import com.belejki.belejki.restful.entity.Reminder;
import com.belejki.belejki.restful.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReminderRepository extends JpaRepository<Reminder, Long> {
    List<Reminder> findByUser_Username(String username);

    List<Reminder> findByUser(User user);

    Page<Reminder> findByExpiredTrue(Pageable pageable);

    Page<Reminder> findByExpiresSoonTrue(Pageable pageable);

    Page<Reminder> findByUser_Username(String username, Pageable pageable);

    Page<Reminder> findByUser(User user, Pageable pageable);

    Page<Reminder> findByExpiredTrueAndUser(User user, Pageable pageable);

    Page<Reminder> findByExpiredTrueAndUser_Username(String username, Pageable pageable);

    Page<Reminder> findByExpiresSoonTrueAndUser(User user, Pageable pageable);

    Page<Reminder> findByExpiresSoonTrueAndUser_Username(String username, Pageable pageable);

    Page<Reminder> findByNameContainingIgnoreCaseAndUser_Username(String name, String username, Pageable pageable);
}
