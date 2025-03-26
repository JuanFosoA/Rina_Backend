package com.backend.Rina.controllers;

import com.backend.Rina.models.Receta;
import com.backend.Rina.services.FileStorageService;
import com.backend.Rina.services.RecetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

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

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<Receta>> obtenerTodasLasRecetas() {
        List<Receta> recetas = recetaService.obtenerTodasLasRecetas();
        return new ResponseEntity<>(recetas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Receta> obtenerRecetaPorId(@PathVariable String id) {
        Optional<Receta> receta = recetaService.obtenerRecetaPorId(id);
        return receta.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Receta> crearRecetaConImagen(
            @RequestPart("receta") Receta receta,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen) {

        try {
            if (imagen != null && !imagen.isEmpty()) {
                String imagenNombre = storageService.store(imagen);
                receta.setImagenNombre(imagenNombre);
            }

            Receta nuevaReceta = recetaService.crearReceta(receta);
            return new ResponseEntity<>(nuevaReceta, HttpStatus.CREATED);
        } catch (RuntimeException e) {
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
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
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
