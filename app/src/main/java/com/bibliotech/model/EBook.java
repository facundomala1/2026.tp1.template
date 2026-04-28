package com.bibliotech.model;

public record EBook(String isbn, String titulo, String autor, int anio, String categoria, String formatoDigital) implements Recurso {}
