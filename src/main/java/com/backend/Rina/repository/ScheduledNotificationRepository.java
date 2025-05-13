package com.backend.Rina.repository;

import com.backend.Rina.models.ScheduledNotification;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduledNotificationRepository extends MongoRepository<ScheduledNotification, String> {
    List<ScheduledNotification> findByNotificationTimeBeforeAndDeliveredFalse(LocalDateTime dateTime);
}