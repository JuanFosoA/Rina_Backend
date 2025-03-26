package com.backend.Rina.repository;

import com.backend.Rina.models.Receta;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RecetaRepository extends MongoRepository<Receta, String> {
    List<Receta> findByNombre(String nombre);
    List<Receta> findByCategoria(List<String> categorias);
}
