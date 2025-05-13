package com.backend.Rina.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ExpoNotificationService {
    private static final String EXPO_API_URL = "https://exp.host/--/api/v2/push/send";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void sendPushNotifications(List<ExpoPushMessage> messages) throws Exception {
        if (messages == null || messages.isEmpty()) {
            throw new IllegalArgumentException("Messages list cannot be null or empty");
        }

        // Expo recomienda enviar máximo 100 mensajes por lote
        int batchSize = 100;
        for (int i = 0; i < messages.size(); i += batchSize) {
            List<ExpoPushMessage> batch = messages.subList(i, Math.min(messages.size(), i + batchSize));
            sendBatch(batch);
        }
    }

    private void sendBatch(List<ExpoPushMessage> batch) throws Exception {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(EXPO_API_URL);

            // Configurar headers
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setHeader("Accept-Encoding", "gzip, deflate");

            // Convertir mensajes a JSON
            String jsonPayload = objectMapper.writeValueAsString(batch);
            httpPost.setEntity(new StringEntity(jsonPayload));

            // Ejecutar la petición y procesar respuesta
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                handleResponse(response);
            }
        }
    }

    private void handleResponse(CloseableHttpResponse response) throws Exception {
        int statusCode = response.getStatusLine().getStatusCode();

        if (statusCode != 200) {
            String errorResponse = response.getEntity() != null ?
                    new String(response.getEntity().getContent().readAllBytes()) : "No response body";
            throw new RuntimeException("Expo API request failed with status: " +
                    statusCode + ". Response: " + errorResponse);
        }

        // Opcional: procesar la respuesta para obtener tickets
        ExpoPushResponse pushResponse = objectMapper.readValue(
                response.getEntity().getContent(),
                ExpoPushResponse.class
        );

        // Aquí puedes registrar los tickets para verificar su estado después
        logTickets(pushResponse.getData());
    }

    private void logTickets(List<ExpoPushTicket> tickets) {
        // Implementa tu lógica de logging aquí
        tickets.forEach(ticket -> {
            if (!"ok".equals(ticket.getStatus())) {
                System.err.println("Error en ticket: " + ticket.getMessage());
            }
        });
}

    public static class ExpoPushMessage {
        private String to;
        private String title;
        private String body;
        private Map<String, Object> data;

        // Constructor
        public ExpoPushMessage(String to, String title, String body, Map<String, Object> data) {
            this.to = to;
            this.title = title;
            this.body = body;
            this.data = data;
        }

        // Getters (necesarios para la serialización JSON)
        public String getTo() { return to; }
        public String getTitle() { return title; }
        public String getBody() { return body; }
        public Map<String, Object> getData() { return data; }
    }

    public static class ExpoPushResponse {
        private List<ExpoPushTicket> data;

        // Getters y setters
        public List<ExpoPushTicket> getData() { return data; }
        public void setData(List<ExpoPushTicket> data) { this.data = data; }
    }

    public static class ExpoPushTicket {
        private String status;
        private String id;
        private String message;

        // Getters y setters
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }
}