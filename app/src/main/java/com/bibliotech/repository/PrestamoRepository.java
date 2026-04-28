package com.bibliotech.repository;

import com.bibliotech.model.Prestamo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PrestamoRepository implements Repository<Prestamo, String> {
    private final List<Prestamo> baseDeDatos = new ArrayList<>();

    @Override
    public void guardar(Prestamo prestamo) {
        // Removemos la versión vieja si existe (útil para cuando actualizamos con la
        // fecha de devolución)
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

    public int contarPrestamosActivosDeSocio(String dniSocio) {
        return (int) baseDeDatos.stream()
                .filter(p -> p.socio().dni().equals(dniSocio) && p.fechaDevolucionReal() == null)
                .count();
    }

    public List<Prestamo> buscarHistorialPorSocio(String dniSocio) {
        return baseDeDatos.stream()
                .filter(p -> p.socio().dni().equals(dniSocio))
                .toList();
    }
}