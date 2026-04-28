package com.bibliotech.model;

public record EBook(
        String isbn,
        String titulo,
        String autor,
        int anio,
        String categoria,
        String formatoArchivo,
        double tamanioMB) implements Recurso {
}