package com.backend.Rina.services;

import com.backend.Rina.config.StorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Set;
import java.util.UUID;

@Service
public class FileStorageService {
    private final Path rootLocation;
    private static final Set<String> ALLOWED_EXTENSIONS = Set.of(".jpg", ".jpeg", ".png", ".webp", ".gif");
    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB

    @Autowired
    public FileStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation()).toAbsolutePath().normalize();
    }

    public void init() {
        try {
            Files.createDirectories(rootLocation);
            System.out.println("Directorio de uploads creado en: " + rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo inicializar el almacenamiento en: " + rootLocation, e);
        }
    }

    public String store(MultipartFile file) {
        try {
            if (file == null || file.isEmpty()) {
                throw new RuntimeException("Archivo vacío o nulo");
            }

            if (file.getSize() > MAX_FILE_SIZE) {
                throw new RuntimeException("El archivo excede el tamaño máximo permitido (10MB)");
            }

            String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
            String extension = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();

            if (!ALLOWED_EXTENSIONS.contains(extension)) {
                throw new RuntimeException("Tipo de archivo no permitido. Extensiones permitidas: " + ALLOWED_EXTENSIONS);
            }

            if (!Files.exists(rootLocation)) {
                init();
            }

            String filename = UUID.randomUUID() + extension;
            Path destinationFile = rootLocation.resolve(filename).normalize();

            if (!destinationFile.getParent().equals(rootLocation.toAbsolutePath())) {
                throw new RuntimeException("No se puede almacenar el archivo fuera del directorio de uploads");
            }

            Files.copy(file.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Archivo guardado en: " + destinationFile);

            return filename;
        } catch (IOException e) {
            throw new RuntimeException("Error al almacenar el archivo: " + e.getMessage(), e);
        }
    }

    public Resource loadAsResource(String filename) {
        try {
            if (filename == null || filename.isEmpty()) {
                throw new RuntimeException("Nombre de archivo inválido");
            }

            Path filePath = rootLocation.resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("No se pudo leer el archivo: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error al leer el archivo: " + filename, e);
        }
    }
}