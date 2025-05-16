package com.backend.Rina.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.*;

@Document(collection = "devices")
public class Device {
    @Id
    private String id;

    @Indexed(unique = true)
    private String expoPushToken;

    @Indexed
    private String deviceId;

    private Instant createdAt = Instant.now();

    //Pasarlo a UTC-5
    public ZonedDateTime getCreatedAtColombiaTime() {
        return createdAt.atZone(ZoneId.of("America/Bogota"));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExpoPushToken() {
        return expoPushToken;
    }

    public void setExpoPushToken(String expoPushToken) {
        this.expoPushToken = expoPushToken;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
