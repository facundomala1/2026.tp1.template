package com.bibliotech.repository;

import com.bibliotech.model.Recurso;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LibroRepository implements Repository<Recurso, String> {
    private final List<Recurso> baseDeDatos = new ArrayList<>();

    @Override
    public void guardar(Recurso recurso) {
        baseDeDatos.removeIf(r -> r.isbn().equals(recurso.isbn()));
        baseDeDatos.add(recurso);
    }

    @Override
    public Optional<Recurso> buscarPorId(String id) {
        return baseDeDatos.stream()
                .filter(r -> r.isbn().equals(id))
                .findFirst();
    }

    @Override
    public List<Recurso> buscarTodos() {
        return new ArrayList<>(baseDeDatos);
    }
}