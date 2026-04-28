package com.bibliotech.repository;

import com.bibliotech.model.Libro;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LibroRepository implements Repository<Libro, String> {
    private final List<Libro> baseDeDatos = new ArrayList<>();

    @Override
    public void guardar(Libro libro) {
        baseDeDatos.add(libro);
    }

    @Override
    public Optional<Libro> buscarPorId(String isbn) {
        return baseDeDatos.stream()
                .filter(libro -> libro.isbn().equals(isbn))
                .findFirst();
    }

    @Override
    public List<Libro> buscarTodos() {
        return new ArrayList<>(baseDeDatos);
    }

    // Método para la Búsqueda Avanzada exigida en el TP
    public List<Libro> buscarPorCriterio(String filtro) {
        if (filtro == null || filtro.trim().isEmpty()) {
            return buscarTodos(); // Si no ingresa nada, devuelve todo
        }
        
        String termino = filtro.toLowerCase().trim();
        
        return baseDeDatos.stream()
                .filter(libro -> 
                    libro.titulo().toLowerCase().contains(termino) ||
                    libro.autor().toLowerCase().contains(termino) ||
                    libro.categoria().toLowerCase().contains(termino)
                )
                .toList(); // Devuelve una nueva lista solo con los resultados
    }
}