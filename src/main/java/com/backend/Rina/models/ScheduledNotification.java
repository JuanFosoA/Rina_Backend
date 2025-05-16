package com.backend.Rina.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "scheduled_notifications")
public class ScheduledNotification {
    @Id
    private String id;
    private String userId;
    private String recipeId;
    private LocalDateTime triggerTime;
    private LocalDateTime notificationTime;
    private boolean delivered;
    private String title;
    private String body;


    public ScheduledNotification(String userId, String recipeId, LocalDateTime triggerTime,
                                 String title, String body) {
        this.userId = userId;
        this.recipeId = recipeId;
        this.triggerTime = triggerTime;
        this.notificationTime = triggerTime.minusMinutes(20);
        this.title = title;
        this.body = body;
        this.delivered = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    public LocalDateTime getTriggerTime() {
        return triggerTime;
    }

    public void setTriggerTime(LocalDateTime triggerTime) {
        this.triggerTime = triggerTime;
    }

    public LocalDateTime getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(LocalDateTime notificationTime) {
        this.notificationTime = notificationTime;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }


    @Transient // Para MongoDB, indica que no se persiste
    public LocalDateTime calculateNotificationTime() {
        return this.triggerTime.minusMinutes(20);
    }
}
