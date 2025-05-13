package com.backend.Rina.services;

import com.backend.Rina.models.Receta;
import com.backend.Rina.models.ScheduledNotification;
import com.backend.Rina.models.UserDevice;
import com.backend.Rina.repository.RecetaRepository;
import com.backend.Rina.repository.ScheduledNotificationRepository;
import com.backend.Rina.repository.UserDeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RecipeNotificationScheduler {

    private final ScheduledNotificationRepository scheduledNotificationRepository;
    private final ExpoNotificationService expoNotificationService;
    private final UserDeviceRepository userDeviceRepository;
    private final RecetaRepository recipeRepository;

    public void scheduleRecipeNotification(String userId, String recipeId, LocalDateTime mealTime) {
        // Obtener nombre de la receta
        String recipeName = recipeRepository.findById(recipeId)
                .map(Receta::getNombre)
                .orElse("tu receta"); // Valor por defecto

        ScheduledNotification notification = new ScheduledNotification(
                userId,
                recipeId,
                mealTime,
                "¡Hora de cocinar!",
                String.format("Recuerda preparar %s para tu comida", recipeName)
        );

        scheduledNotificationRepository.save(notification);
    }

    @Scheduled(fixedRate = 60000)
    public void checkPendingNotifications() {
        LocalDateTime now = LocalDateTime.now();
        List<ScheduledNotification> pendingNotifications = scheduledNotificationRepository
                .findByNotificationTimeBeforeAndDeliveredFalse(now);

        for (ScheduledNotification notification : pendingNotifications) {
            sendNotification(notification);
            notification.setDelivered(true);
            scheduledNotificationRepository.save(notification);
        }
    }

    private void sendNotification(ScheduledNotification notification) {
        List<UserDevice> userDevices = userDeviceRepository.findByUserId(notification.getUserId());

        if (!userDevices.isEmpty()) {
            List<ExpoNotificationService.ExpoPushMessage> messages = userDevices.stream()
                    .map(device -> new ExpoNotificationService.ExpoPushMessage(
                            device.getExpoPushToken(),
                            notification.getTitle(),
                            notification.getBody(),
                            Map.of(
                                    "recipeId", notification.getRecipeId(),
                                    "type", "recipeReminder"
                            )
                    ))
                    .toList();

            try {
                expoNotificationService.sendPushNotifications(messages);
            } catch (Exception e) {
                // Manejar error y posible reintento
            }
        }
    }
}
