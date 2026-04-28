package com.bibliotech.exception;

public class LibroNoDisponibleException extends BibliotecaException {
    public LibroNoDisponibleException(String titulo) {
        super("El libro '" + titulo + "' no se encuentra disponible para préstamo en este momento.");
    }
}