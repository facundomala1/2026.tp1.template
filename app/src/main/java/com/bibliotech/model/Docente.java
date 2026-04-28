package com.bibliotech.model;

public record Docente(String dni, String nombre, String email) implements Socio {
    @Override
    public int maxPrestamos() { return 5; }
}
