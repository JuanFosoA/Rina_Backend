package com.backend.Rina.schemas;

public class Cantidad {
    private double valor;
    private String unidad;

    public Cantidad() {}

    public Cantidad(double valor, String unidad) {
        this.valor = valor;
        this.unidad = unidad;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }
}
