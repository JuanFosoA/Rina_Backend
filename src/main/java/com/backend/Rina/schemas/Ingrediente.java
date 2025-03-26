package com.backend.Rina.schemas;

public class Ingrediente {
    private String nombre;
    private Cantidad cantidad;

    public Ingrediente() {}

    public Ingrediente(String nombre, Cantidad cantidad) {
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Cantidad getCantidad() {
        return cantidad;
    }

    public void setCantidad(Cantidad cantidad) {
        this.cantidad = cantidad;
    }
}
