package com.backend.Rina.controllers;

import com.backend.Rina.dtos.RecetaDto;
import com.backend.Rina.models.Receta;
import com.backend.Rina.schemas.InformacionNutricional;
import com.backend.Rina.services.FileStorageService;
import com.backend.Rina.services.RecetaService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/recetas")
public class RecetaController {

    private final RecetaService recetaService;
    private final FileStorageService storageService;

    @Autowired
    public RecetaController(RecetaService recetaService, FileStorageService storageService) {
        this.recetaService = recetaService;
        this.storageService = storageService;
    }


    private RecetaDto convertToDto(Receta receta) {
        RecetaDto dto = new RecetaDto();

        // Mapeo de propiedades básicas
        dto.setId(receta.getId());
        dto.setNombre(receta.getNombre());
        dto.setPorciones(receta.getPorciones());
        dto.setTiempoPreparacion(receta.getTiempoPreparacion());

        // Mapeo de imagen
        if (receta.getImagenNombre() != null && !receta.getImagenNombre().isEmpty()) {
            dto.setImagenNombre(receta.getImagenNombre());
        } else {
            dto.setImagenNombre(null); // Asegurar que sea null si no hay imagen
        }

        if (receta.getIngredientes() != null) {
            dto.setIngredientes(new ArrayList<>(receta.getIngredientes()));
        } else {
            dto.setIngredientes(Collections.emptyList());
        }

        if (receta.getInstrucciones() != null) {
            dto.setInstrucciones(new ArrayList<>(receta.getInstrucciones()));
        } else {
            dto.setInstrucciones(Collections.emptyList());
        }

        if (receta.getInformacionNutricional() != null) {
            InformacionNutricional infoNutricional = new InformacionNutricional();
            infoNutricional.setCalorias(receta.getInformacionNutricional().getCalorias());
            infoNutricional.setProteinas(receta.getInformacionNutricional().getProteinas());
            infoNutricional.setCarbohidratos(receta.getInformacionNutricional().getCarbohidratos());
            infoNutricional.setGrasas(receta.getInformacionNutricional().getGrasas());
            dto.setInformacionNutricional(infoNutricional);
        }

        if (receta.getCategoria() != null) {
            dto.setCategorias(new ArrayList<>(receta.getCategoria()));
        } else {
            dto.setCategorias(Collections.emptyList());
        }

        return dto;
    }

    @GetMapping
    //@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<RecetaDto>> obtenerTodasLasRecetas() {
        try {
            List<Receta> recetas = recetaService.obtenerTodasLasRecetas();

            if (recetas.isEmpty()) {
                return ResponseEntity.ok(Collections.emptyList());
            }

            List<RecetaDto> recetasDTO = recetas.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(recetasDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    // @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Receta> obtenerRecetaPorId(@PathVariable String id) {
        Optional<Receta> receta = recetaService.obtenerRecetaPorId(id);
        return receta.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Receta> crearRecetaConImagen(
            @RequestPart("receta") Receta receta,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen,
            HttpServletRequest request) {

        System.out.println("=== INICIO DE PETICIÓN ===");
        System.out.println("Content-Type recibido: " + request.getContentType());
        System.out.println("Receta recibida: " + receta.toString());

        try {
            if (imagen != null) {
                System.out.println("Imagen recibida - Nombre: " + imagen.getOriginalFilename());
                System.out.println("Imagen recibida - Tamaño: " + imagen.getSize());
                System.out.println("Imagen recibida - Tipo: " + imagen.getContentType());

                if (!imagen.isEmpty()) {
                    String imagenNombre = storageService.store(imagen);
                    receta.setImagenNombre(imagenNombre);
                    System.out.println("Imagen guardada con nombre: " + imagenNombre);
                } else {
                    System.out.println("Imagen está vacía");
                }
            } else {
                System.out.println("No se recibió imagen (null)");
            }

            Receta nuevaReceta = recetaService.crearReceta(receta);
            System.out.println("=== RECETA CREADA CON ÉXITO ===");
            return new ResponseEntity<>(nuevaReceta, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            System.out.println("ERROR al crear receta: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Receta> actualizarReceta(@PathVariable String id, @RequestBody Receta receta) {
        Receta recetaActualizada = recetaService.actualizarReceta(id, receta);
        if (recetaActualizada != null) {
            return new ResponseEntity<>(recetaActualizada, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarReceta(@PathVariable String id) {
        recetaService.eliminarReceta(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/buscar")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<Receta>> buscarRecetas(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) List<String> categorias) {

        if (nombre != null && !nombre.isEmpty()) {
            return new ResponseEntity<>(recetaService.buscarPorNombre(nombre), HttpStatus.OK);
        } else if (categorias != null && !categorias.isEmpty()) {
            return new ResponseEntity<>(recetaService.buscarPorCategoria(categorias), HttpStatus.OK);
        }
        return new ResponseEntity<>(recetaService.obtenerTodasLasRecetas(), HttpStatus.OK);
    }

    @GetMapping("/imagen/{filename:.+}")
    //@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Resource> servirImagen(@PathVariable String filename) {
        try {
            Resource file = storageService.loadAsResource(filename);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + file.getFilename() + "\"")
                    .body(file);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
