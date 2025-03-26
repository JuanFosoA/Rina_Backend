package com.backend.Rina.schemas;

public class Instruccion {
    private String paso;
    private int orden;

    public Instruccion() {}

    public Instruccion(String paso, int orden) {
        this.paso = paso;
        this.orden = orden;
    }

    public String getPaso() {
        return paso;
    }

    public void setPaso(String paso) {
        this.paso = paso;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }
}
