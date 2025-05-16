package com.backend.Rina.repository;

import com.backend.Rina.models.Device;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DeviceRepository extends MongoRepository<Device, String> {
    Optional<Device> findByExpoPushToken(String token);

    Optional<Device> findByDeviceId(String deviceId);

    List<Device> findByCreatedAtBefore(LocalDateTime date);

    void deleteByDeviceId(String deviceId);

    void deleteByCreatedAtLessThan(LocalDateTime date);
}
