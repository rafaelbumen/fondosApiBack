package com.fondos.fondosApi.init;

import org.springframework.stereotype.Component;

import com.fondos.fondosApi.model.Fondo;
import com.fondos.fondosApi.repository.IFondoRepository;
import jakarta.annotation.PostConstruct;

@Component
public class FondoSeeder {

    private final IFondoRepository fondoRepository;

    public FondoSeeder(IFondoRepository fondoRepository) {
        this.fondoRepository = fondoRepository;
    }

    @PostConstruct
    public void init() {
        if (fondoRepository.findAll().isEmpty()) {
            fondoRepository.save(crearFondo("1", "FPV_EL CLIENTE_RECAUDADORA", 75_000L, "FPV", 150_000L));
            fondoRepository.save(crearFondo("2", "FPV_EL CLIENTE_ECOPETROL", 125_000L, "FPV", 200_000L));
            fondoRepository.save(crearFondo("3", "DEUDAPRIVADA", 50_000L, "FIC", 500_000L));
            fondoRepository.save(crearFondo("4", "FDO-ACCIONES", 250_000L, "FIC", 1_000_000L));
            fondoRepository.save(crearFondo("5", "FPV_EL CLIENTE_DINAMICA", 100_000L, "FPV", 800_000L));
            System.out.println("✅ Fondos insertados correctamente con aportes iniciales.");
        } else {
            System.out.println("ℹ️ Ya existen fondos registrados.");
        }
    }

    private Fondo crearFondo(String id, String nombre, Long montoMinimo, String categoria, Long totalAportes) {
        Fondo fondo = new Fondo();
        fondo.setId(id);
        fondo.setNombre(nombre);
        fondo.setMontoMinimo(montoMinimo);
        fondo.setCategoria(categoria);
        fondo.setTotalAportes(totalAportes);
        return fondo;
    }
}

