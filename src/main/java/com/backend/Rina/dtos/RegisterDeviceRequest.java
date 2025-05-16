package com.backend.Rina.dtos;

import jakarta.validation.constraints.NotBlank;

public record RegisterDeviceRequest(
        @NotBlank String expoPushToken,
        @NotBlank String deviceId
) {}