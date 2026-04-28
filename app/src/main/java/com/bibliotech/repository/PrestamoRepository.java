package com.bibliotech.repository;

import com.bibliotech.model.Prestamo;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PrestamoRepository implements Repository<Prestamo, String> {
    private final List<Prestamo> baseDeDatos = new ArrayList<>();

    @Override
    public void guardar(Prestamo prestamo) {
        baseDeDatos.removeIf(p -> p.id().equals(prestamo.id()));
        baseDeDatos.add(prestamo);
    }

    @Override
    public Optional<Prestamo> buscarPorId(String id) {
        return baseDeDatos.stream()
                .filter(p -> p.id().equals(id))
                .findFirst();
    }

    @Override
    public List<Prestamo> buscarTodos() {
        return new ArrayList<>(baseDeDatos);
    }

    public List<Prestamo> buscarHistorialPorSocio(String dni) {
        return baseDeDatos.stream()
                .filter(p -> p.socio().dni().equals(dni))
                .collect(Collectors.toList());
    }

    public int contarPrestamosActivosDeSocio(String dni) {
        return (int) baseDeDatos.stream()
                .filter(p -> p.socio().dni().equals(dni) && p.fechaDevolucionReal() == null)
                .count();
    }
}