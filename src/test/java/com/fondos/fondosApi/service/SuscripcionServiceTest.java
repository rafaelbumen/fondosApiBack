package com.fondos.fondosApi.service;

import com.fondos.fondosApi.dto.SuscripcionRequest;
import com.fondos.fondosApi.model.Fondo;
import com.fondos.fondosApi.model.User;
import com.fondos.fondosApi.repository.FondoRepository;
import com.fondos.fondosApi.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class SuscripcionServiceTest {

    private UserRepository userRepository;
    private FondoRepository fondoRepository;
    private SuscripcionService suscripcionService;
    private SNSService snsService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        fondoRepository = mock(FondoRepository.class);
        suscripcionService = new SuscripcionService(userRepository, fondoRepository, snsService);
    }

    @Test
    void subscribe_usuarioNuevoAFondo_deberiaSuscribirse() {
        // Arrange
        SuscripcionRequest request = new SuscripcionRequest();
        request.setUserId("u1");
        request.setFondoId("f1");
        request.setMonto(200_000L);

        Fondo fondo = new Fondo();
        fondo.setId("f1");
        fondo.setNombre("FondoTest");
        fondo.setMontoMinimo(100_000L);
        fondo.setTotalAportes(0L);

        User user = new User();
        user.setId("u1");
        user.setBalance(300_000L);
        user.setSuscripciones(new ArrayList<>());
        user.setTransactions(new ArrayList<>());

        when(userRepository.findById("u1")).thenReturn(Optional.of(user));
        when(fondoRepository.findById("f1")).thenReturn(Optional.of(fondo));

        // Act
        suscripcionService.subscribe(request);

        // Assert
        assertEquals(1, user.getSuscripciones().size());
        assertEquals(1, user.getTransactions().size());
        assertEquals(100_000L, user.getBalance()); // Se descontó el aporte
        assertEquals(200_000L, fondo.getTotalAportes());

        verify(userRepository).save(user);
        verify(fondoRepository).save(fondo);
    }
}
