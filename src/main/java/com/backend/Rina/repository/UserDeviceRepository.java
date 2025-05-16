package com.backend.Rina.repository;

import com.backend.Rina.models.ScheduledNotification;
import com.backend.Rina.models.UserDevice;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserDeviceRepository extends MongoRepository<UserDevice, String> {
    List<UserDevice> findByUserId(String userId);
    Optional<UserDevice> findByExpoPushToken(String token);
}
