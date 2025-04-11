package com.backend.Rina.dtos;

import com.backend.Rina.schemas.InformacionNutricional;
import com.backend.Rina.schemas.Ingrediente;
import com.backend.Rina.schemas.Instruccion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecetaDto {
    private String id;
    private String nombre;
    private String imagenNombre;
    private int porciones;
    private int tiempoPreparacion;
    private List<Ingrediente> ingredientes = new ArrayList<>();
    private List<Instruccion> instrucciones = new ArrayList<>();
    private InformacionNutricional informacionNutricional;
    private List<String> categorias = new ArrayList<>();

    // Getters y Setters mejorados
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagenNombre() {
        return imagenNombre;
    }

    public void setImagenNombre(String imagenNombre) {
        this.imagenNombre = imagenNombre;
    }

    public int getPorciones() {
        return porciones;
    }

    public void setPorciones(int porciones) {
        this.porciones = porciones;
    }

    public int getTiempoPreparacion() {
        return tiempoPreparacion;
    }

    public void setTiempoPreparacion(int tiempoPreparacion) {
        this.tiempoPreparacion = tiempoPreparacion;
    }

    public List<Ingrediente> getIngredientes() {
        return Collections.unmodifiableList(ingredientes);
    }

    public void setIngredientes(List<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes != null ? new ArrayList<>(ingredientes) : new ArrayList<>();
    }

    public List<Instruccion> getInstrucciones() {
        return Collections.unmodifiableList(instrucciones);
    }

    public void setInstrucciones(List<Instruccion> instrucciones) {
        this.instrucciones = instrucciones != null ? new ArrayList<>(instrucciones) : new ArrayList<>();
    }

    public InformacionNutricional getInformacionNutricional() {
        return informacionNutricional;
    }

    public void setInformacionNutricional(InformacionNutricional informacionNutricional) {
        this.informacionNutricional = informacionNutricional;
    }

    public List<String> getCategorias() {
        return Collections.unmodifiableList(categorias);
    }

    public void setCategorias(List<String> categorias) {
        this.categorias = categorias != null ? new ArrayList<>(categorias) : new ArrayList<>();
    }


    @Override
    public String toString() {
        return "RecetaDto{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", porciones=" + porciones +
                ", tiempoPreparacion=" + tiempoPreparacion +
                ", ingredientes=" + ingredientes.size() +
                ", instrucciones=" + instrucciones.size() +
                ", categorias=" + categorias.size() +
                '}';
    }
}