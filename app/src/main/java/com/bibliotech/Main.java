package com.bibliotech;

import com.bibliotech.exception.BibliotecaException;
import com.bibliotech.model.Estudiante;
import com.bibliotech.model.Libro;
import com.bibliotech.model.Prestamo;
import com.bibliotech.repository.LibroRepository;
import com.bibliotech.repository.PrestamoRepository;
import com.bibliotech.service.PrestamoService;

public class Main {
    public static void main(String[] args) {
        System.out.println("Iniciando Sistema BiblioTech...\n");

        LibroRepository libroRepo = new LibroRepository();
        libroRepo.guardar(new Libro("111", "Clean Architecture", "Robert C. Martin", 2017, "Programación"));

        PrestamoRepository prestamoRepo = new PrestamoRepository();

        PrestamoService servicio = new PrestamoService(libroRepo, prestamoRepo);

        Estudiante estudiante = new Estudiante("40123456", "Facundo", "facundo@universidad.edu");

        try {
            System.out.println("--- Prueba 1: Prestar Libro ---");
            servicio.prestarLibro("111", estudiante);

            Prestamo prestamoActivo = prestamoRepo.buscarHistorialPorSocio("40123456").get(0);

            System.out.println("\n--- Prueba 2: Devolver Libro ---");
            servicio.devolverLibro(prestamoActivo.id());

        } catch (BibliotecaException e) {
            System.err.println(" Excepción: " + e.getMessage());
        }
    }
}