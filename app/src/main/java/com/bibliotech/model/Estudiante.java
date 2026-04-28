package com.bibliotech.model;

public record Estudiante(String dni, String nombre, String email) implements Socio {
    @Override
    public int maxPrestamos() { return 3; }
}
