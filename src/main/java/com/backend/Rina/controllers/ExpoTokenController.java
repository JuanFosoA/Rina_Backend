package com.backend.Rina.controllers;

import com.backend.Rina.security.jwt.JwtUtils;
import com.backend.Rina.services.ExpoPushNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/expo-tokens")
public class ExpoTokenController {
    @Autowired
    private ExpoPushNotificationService expoService;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping()
    public ResponseEntity<Map<String, Object>> saveToken(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody Map<String, String> request
    ) {
        try {
            String token = authHeader.substring(7);
            String userId = jwtUtils.extractUserId(token);
            String expoToken = request.get("expoPushToken");

            expoService.saveToken(userId, expoToken);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Token guardado exitosamente");
            response.put("userId", userId);
            response.put("timestamp", LocalDateTime.now().toString());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
