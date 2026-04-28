package com.bibliotech.service;

import com.bibliotech.exception.BibliotecaException;
import com.bibliotech.exception.LibroNoDisponibleException;
import com.bibliotech.model.Libro;
import com.bibliotech.model.Prestamo;
import com.bibliotech.model.Socio;
import com.bibliotech.repository.PrestamoRepository;
import com.bibliotech.repository.Repository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

public class PrestamoService {
    // Inyectamos ambos repositorios
    private final Repository<Libro, String> libroRepository;
    private final PrestamoRepository prestamoRepository;

    public PrestamoService(Repository<Libro, String> libroRepository, PrestamoRepository prestamoRepository) {
        this.libroRepository = libroRepository;
        this.prestamoRepository = prestamoRepository;
    }

    public void prestarLibro(String isbn, Socio socio) throws BibliotecaException {
        Optional<Libro> libroOpt = libroRepository.buscarPorId(isbn);
        if (libroOpt.isEmpty()) {
            throw new LibroNoDisponibleException(isbn);
        }
        Libro libro = libroOpt.get();

        // El repositorio cuenta automáticamente cuántos tiene sin devolver
        int activos = prestamoRepository.contarPrestamosActivosDeSocio(socio.dni());

        if (activos >= socio.maxPrestamos()) {
            System.out.println(
                    "Error: " + socio.nombre() + " alcanzó su límite de " + socio.maxPrestamos() + " préstamos.");
            return;
        }

        // Creamos el registro con 7 días de plazo
        LocalDate hoy = LocalDate.now();
        LocalDate vencimiento = hoy.plusDays(7);
        String prestamoId = UUID.randomUUID().toString(); // Genera un ID único seguro

        Prestamo nuevoPrestamo = new Prestamo(prestamoId, socio, libro, hoy, vencimiento, null);
        prestamoRepository.guardar(nuevoPrestamo);

        System.out.println(
                "✅ Préstamo registrado: '" + libro.titulo() + "' a " + socio.nombre() + ". Vence el: " + vencimiento);
    }

    public void devolverLibro(String prestamoId) throws BibliotecaException {
        Optional<Prestamo> prestamoOpt = prestamoRepository.buscarPorId(prestamoId);
        if (prestamoOpt.isEmpty()) {
            throw new BibliotecaException("El préstamo no existe.");
        }

        Prestamo prestamoActual = prestamoOpt.get();
        if (prestamoActual.fechaDevolucionReal() != null) {
            System.out.println("Este libro ya fue devuelto.");
            return;
        }

        LocalDate hoy = LocalDate.now();

        Prestamo prestamoDevuelto = new Prestamo(
                prestamoActual.id(), prestamoActual.socio(), prestamoActual.recurso(),
                prestamoActual.fechaPrestamo(), prestamoActual.fechaVencimiento(), hoy);

        prestamoRepository.guardar(prestamoDevuelto);

        if (prestamoDevuelto.estaVencido(hoy)) {
            long diasRetraso = ChronoUnit.DAYS.between(prestamoDevuelto.fechaVencimiento(), hoy);
            System.out.println("Devolución tardía: '" + prestamoDevuelto.recurso().titulo() + "' devuelto con "
                    + diasRetraso + " días de retraso.");
        } else {
            System.out.println("✅ Devolución a tiempo: '" + prestamoDevuelto.recurso().titulo() + "'.");
        }
    }
}