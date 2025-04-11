package com.backend.Rina.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import jakarta.annotation.PostConstruct;
import java.nio.file.Path;
import java.nio.file.Paths;

@ConfigurationProperties(prefix = "storage")
public class StorageProperties {
    private String location = "uploads";
    private String allowedExtensions = "jpg,jpeg,png,gif,webp";
    private long maxFileSizeMB = 10;
    private boolean autoCreateDirectory = true;

    private Path resolvedLocation;

    @PostConstruct
    public void init() {
        this.resolvedLocation = Paths.get(this.location).toAbsolutePath().normalize();
        System.out.println("Configuración de almacenamiento inicializada. Ruta: " + this.resolvedLocation);
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String[] getAllowedExtensions() {
        return allowedExtensions.split(",");
    }

    public void setAllowedExtensions(String allowedExtensions) {
        this.allowedExtensions = allowedExtensions;
    }

    public long getMaxFileSizeBytes() {
        return maxFileSizeMB * 1024 * 1024;
    }

    public void setMaxFileSizeMB(long maxFileSizeMB) {
        this.maxFileSizeMB = maxFileSizeMB;
    }

    public boolean isAutoCreateDirectory() {
        return autoCreateDirectory;
    }

    public void setAutoCreateDirectory(boolean autoCreateDirectory) {
        this.autoCreateDirectory = autoCreateDirectory;
    }

    public Path getResolvedLocation() {
        return resolvedLocation;
    }

    @Override
    public String toString() {
        return "StorageProperties{" +
                "location='" + location + '\'' +
                ", resolvedLocation=" + resolvedLocation +
                ", allowedExtensions='" + allowedExtensions + '\'' +
                ", maxFileSizeMB=" + maxFileSizeMB +
                ", autoCreateDirectory=" + autoCreateDirectory +
                '}';
    }
}