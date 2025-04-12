package com.belejki.belejki.restful.repository;

import com.belejki.belejki.restful.entity.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReminderRepository extends JpaRepository<Reminder, Long> {
}
