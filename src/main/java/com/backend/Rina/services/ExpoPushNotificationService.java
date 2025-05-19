package com.backend.Rina.services;

import com.backend.Rina.models.UserExpoToken;
import com.backend.Rina.repository.UserExpoTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class ExpoPushNotificationService {
    private static final String EXPO_API_URL = "https://exp.host/--/api/v2/push/send";

    @Autowired
    private UserExpoTokenRepository tokenRepository;

    public void saveToken(String userId, String expoPushToken) {
        UserExpoToken token = tokenRepository.findByUserId(userId).orElse(new UserExpoToken());
        token.setUserId(userId);
        token.setExpoPushToken(expoPushToken);
        tokenRepository.save(token);
    }

    public void sendPushNotification(String userId, String title, String body) {
        tokenRepository.findByUserId(userId).ifPresent(token -> {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, String> payload = new HashMap<>();
            payload.put("to", token.getExpoPushToken());
            payload.put("title", title);
            payload.put("body", body);

            HttpEntity<Map<String, String>> request = new HttpEntity<>(payload, headers);
            restTemplate.postForEntity(EXPO_API_URL, request, String.class);
        });
    }
}
