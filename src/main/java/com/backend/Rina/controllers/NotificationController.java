package com.backend.Rina.controllers;

import com.backend.Rina.models.User;
import com.backend.Rina.models.UserDevice;
import com.backend.Rina.repository.UserDeviceRepository;
import com.backend.Rina.services.RecipeNotificationScheduler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final UserDeviceRepository userDeviceRepository;
    private final RecipeNotificationScheduler notificationScheduler;

    @PostMapping("/register-token")
    public ResponseEntity<Void> registerToken(
            @RequestBody RegisterTokenRequest request,
            @AuthenticationPrincipal User user) {

        UserDevice device = new UserDevice();
        device.setUserId(user.getId());
        device.setExpoPushToken(request.token());
        device.setCreatedAt(LocalDateTime.now());

        userDeviceRepository.save(device);
        return ResponseEntity.ok().build();
    }

    // DTO para el request
    public record RegisterTokenRequest(String token) {}
}
