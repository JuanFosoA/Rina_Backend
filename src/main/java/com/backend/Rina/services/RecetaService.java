package com.backend.Rina.services;


import com.backend.Rina.models.Receta;
import com.backend.Rina.repository.RecetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecetaService {
    private final RecetaRepository recetaRepository;

    @Autowired
    public RecetaService(RecetaRepository recetaRepository) {
        this.recetaRepository = recetaRepository;
    }

    public Receta crearReceta(Receta receta) {
        return recetaRepository.save(receta);
    }

    public List<Receta> obtenerTodasLasRecetas() {
        return recetaRepository.findAll();
    }

    public Optional<Receta> obtenerRecetaPorId(String id) {
        return recetaRepository.findById(id);
    }

    public Receta actualizarReceta(String id, Receta receta) {
        if (recetaRepository.existsById(id)) {
            receta.setId(id);
            return recetaRepository.save(receta);
        }
        return null;
    }

    public void eliminarReceta(String id) {
        recetaRepository.deleteById(id);
    }

    public List<Receta> buscarPorNombre(String nombre) {
        return recetaRepository.findByNombre(nombre);
    }

    public List<Receta> buscarPorCategoria(List<String> categorias) {
        return recetaRepository.findByCategoria(categorias);
    }
}
