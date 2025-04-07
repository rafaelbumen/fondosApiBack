package com.fondos.fondosApi.service;

import com.fondos.fondosApi.dto.DesvinculacionRequest;
import com.fondos.fondosApi.dto.SuscripcionRequest;
import com.fondos.fondosApi.model.*;
import com.fondos.fondosApi.repository.IFondoRepository;
import com.fondos.fondosApi.repository.IUserRepository;

import java.util.Optional;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class SuscripcionService {

    private final IUserRepository userRepository;
    private final IFondoRepository fondoRepository;

    public SuscripcionService(IUserRepository userRepository, IFondoRepository fondoRepository) {
        this.userRepository = userRepository;
        this.fondoRepository = fondoRepository;
    }

    public void subscribe(SuscripcionRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
    
        Fondo fondo = fondoRepository.findById(request.getFondoId())
                .orElseThrow(() -> new IllegalArgumentException("Fondo no encontrado"));
    
        if (request.getMonto() < fondo.getMontoMinimo()) {
            throw new IllegalArgumentException("Monto inferior al mínimo del fondo");
        }
    
        Optional<Suscripcion> suscripcionExistente = user.getSuscripciones().stream()
                .filter(s -> s.getFondoId().equals(request.getFondoId()))
                .findFirst();
    
        String tipoTransaccion;
        if (suscripcionExistente.isPresent()) {
            // Ya existe, sumar al monto actual
            Suscripcion suscripcion = suscripcionExistente.get();
            suscripcion.setMonto(suscripcion.getMonto() + request.getMonto());
            tipoTransaccion = "aporte";
        } else {

            System.out.println(fondo);
            // Nueva suscripción
            Suscripcion nueva = new Suscripcion();
            nueva.setId(UUID.randomUUID().toString());
            nueva.setFondoId(fondo.getId());
            nueva.setFondoNombre(fondo.getNombre());
            nueva.setMonto(request.getMonto());
            nueva.setFecha(LocalDate.now());
            user.getSuscripciones().add(nueva);
            tipoTransaccion = "suscripcion";
        }
    
        // Registrar transacción
        Transaction trans = new Transaction();
        trans.setId(UUID.randomUUID().toString());
        trans.setTipo(tipoTransaccion);
        trans.setFondoId(fondo.getId());
        trans.setFondoNombre(fondo.getNombre());
        trans.setDescripcion(tipoTransaccion.equals("suscripcion")
                ? "Suscripción al fondo " + fondo.getNombre()
                : "Aporte adicional al fondo " + fondo.getNombre());
        trans.setMonto(request.getMonto());
        trans.setFecha(LocalDateTime.now());
    
        user.getTransactions().add(trans);
    
        // Descontar balance del usuario
        if (user.getBalance() < request.getMonto()) {
            throw new IllegalArgumentException("El usuario no tiene suficiente saldo");
        }
        user.setBalance(user.getBalance() - request.getMonto());
    
        // Sumar al total del fondo
        fondo.setTotalAportes(fondo.getTotalAportes() + request.getMonto());
    
        // Guardar cambios
        fondoRepository.save(fondo);
        userRepository.save(user);
    }
    

    public void desvincularDeFondo(DesvinculacionRequest request) {
    User user = userRepository.findById(request.getUserId())
            .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

    Suscripcion suscripcion = user.getSuscripciones().stream()
            .filter(s -> s.getFondoId().equals(request.getFondoId()))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("El usuario no está suscrito a este fondo"));

    // Quitar la suscripción
    user.getSuscripciones().remove(suscripcion);

    // Agregar transacción
    Transaction transaccion = new Transaction();
    transaccion.setId(UUID.randomUUID().toString());
    transaccion.setTipo("cancelacion");
    transaccion.setFondoId(request.getFondoId());
    transaccion.setFondoNombre(suscripcion.getFondoNombre());
    transaccion.setDescripcion("Cancelación del fondo " + suscripcion.getFondoNombre());
    transaccion.setMonto(suscripcion.getMonto());
    transaccion.setFecha(LocalDateTime.now());
    user.getTransactions().add(transaccion);

    // Reembolsar el monto
    user.setBalance(user.getBalance() + suscripcion.getMonto());

    // Actualizar fondo
    Fondo fondo = fondoRepository.findById(request.getFondoId())
            .orElseThrow(() -> new IllegalArgumentException("Fondo no encontrado"));
    fondo.setTotalAportes(fondo.getTotalAportes() - suscripcion.getMonto());

    fondoRepository.save(fondo);
    userRepository.save(user);
}

}

