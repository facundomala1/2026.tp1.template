package com.bibliotech.repository;

import com.bibliotech.exception.BibliotecaException;
import com.bibliotech.model.Socio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SocioRepository implements Repository<Socio, String> {
    private final List<Socio> baseDeDatos = new ArrayList<>();

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    public void registrarSocio(Socio nuevoSocio) throws BibliotecaException {
        if (nuevoSocio.email() == null || !nuevoSocio.email().matches(EMAIL_REGEX)) {
            throw new BibliotecaException("Error: El formato del email '" + nuevoSocio.email() + "' es inválido.");
        }

        boolean dniExiste = baseDeDatos.stream()
                .anyMatch(s -> s.dni().equals(nuevoSocio.dni()));

        if (dniExiste) {
            throw new BibliotecaException("Error: Ya existe un socio registrado con el DNI " + nuevoSocio.dni());
        }

        guardar(nuevoSocio);
        System.out.println("✅ Socio registrado exitosamente: " + nuevoSocio.nombre());
    }

    @Override
    public void guardar(Socio socio) {
        baseDeDatos.removeIf(s -> s.dni().equals(socio.dni()));
        baseDeDatos.add(socio);
    }

    @Override
    public Optional<Socio> buscarPorId(String dni) {
        return baseDeDatos.stream()
                .filter(s -> s.dni().equals(dni))
                .findFirst();
    }

    @Override
    public List<Socio> buscarTodos() {
        return new ArrayList<>(baseDeDatos);
    }
}