package com.bibliotech.model;

import java.time.LocalDate;

public record Prestamo(String id, Socio socio, Recurso recurso, LocalDate fechaPrestamo, LocalDate fechaVencimiento,
        LocalDate fechaDevolucionReal) {

    public boolean estaVencido(LocalDate fechaActual) {
        return fechaDevolucionReal == null && fechaActual.isAfter(fechaVencimiento);
    }
}