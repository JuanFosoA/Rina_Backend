package com.backend.Rina.services;

import com.backend.Rina.models.Device;
import com.backend.Rina.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;

    public void registerDeviceSilently(String expoToken, String deviceId) {
        deviceRepository.findByDeviceId(deviceId)
                .ifPresentOrElse(
                        device -> {
                            // Actualiza el token si el dispositivo existe
                            if (!device.getExpoPushToken().equals(expoToken)) {
                                device.setExpoPushToken(expoToken);
                                deviceRepository.save(device);
                                log.info("Token actualizado para dispositivo: {}", deviceId);
                            }
                        },
                        () -> {
                            // Crea nuevo registro si no existe
                            Device newDevice = new Device();
                            newDevice.setExpoPushToken(expoToken);
                            newDevice.setDeviceId(deviceId);
                            deviceRepository.save(newDevice);
                            log.info("Nuevo dispositivo registrado: {}", deviceId);
                            log.info("Hora colombia: {}", newDevice.getCreatedAtColombiaTime());
                        }
                );
    }
}