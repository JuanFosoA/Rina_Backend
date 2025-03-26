package com.backend.Rina.models;

import com.backend.Rina.schemas.Categoria;
import com.backend.Rina.schemas.InformacionNutricional;
import com.backend.Rina.schemas.Ingrediente;
import com.backend.Rina.schemas.Instruccion;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import java.util.List;

@Document(collection = "recetas")
public class Receta {
    @Id
    private String id;
    private String nombre;
    private String imagenNombre;
    private int porciones;
    private int tiempoPreparacion;
    private List<Ingrediente> ingredientes;
    private List<Instruccion> instrucciones;
    private InformacionNutricional informacionNutricional;
    private List<String> categorias;

    public Receta() {}

    public Receta(String nombre,
                  int porciones,
                  int tiempoPreparacion,
                  List<Ingrediente> ingredientes,
                  List<Instruccion> instrucciones,
                  InformacionNutricional informacionNutricional,
                  List<String> categorias) {
        this.nombre = nombre;
        this.porciones = porciones;
        this.tiempoPreparacion = tiempoPreparacion;
        this.ingredientes = ingredientes;
        this.instrucciones = instrucciones;
        this.informacionNutricional = informacionNutricional;
        this.categorias = categorias;
    }

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
        return ingredientes;
    }

    public void setIngredientes(List<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public List<Instruccion> getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(List<Instruccion> instrucciones) {
        this.instrucciones = instrucciones;
    }

    public InformacionNutricional getInformacionNutricional() {
        return informacionNutricional;
    }

    public void setInformacionNutricional(InformacionNutricional informacionNutricional) {
        this.informacionNutricional = informacionNutricional;
    }

    public List<String> getCategoria() {
        return categorias;
    }

    public void setCategoria(List<String> categorias) {
        this.categorias = categorias;
    }
}
