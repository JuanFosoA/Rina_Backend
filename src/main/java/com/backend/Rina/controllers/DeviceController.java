package com.backend.Rina.controllers;

import com.backend.Rina.dtos.RegisterDeviceRequest;
import com.backend.Rina.services.DeviceService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;

    @PostMapping("/register")
    public ResponseEntity<Void> registerDevice(
            @RequestBody @Valid RegisterDeviceRequest request) {

        deviceService.registerDeviceSilently(
                request.expoPushToken(),
                request.deviceId()
        );

        return ResponseEntity.ok().build(); // Siempre retorna 200 OK
    }
}