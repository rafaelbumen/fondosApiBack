package com.fondos.fondosApi.service;

import com.fondos.fondosApi.model.Fondo;
import com.fondos.fondosApi.repository.FondoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FondoServiceTest {

    private FondoRepository fondoRepository;
    private FondoService fondoService;

    @BeforeEach
    void setUp() {
        fondoRepository = mock(FondoRepository.class);
        fondoService = new FondoService(fondoRepository);
    }

    @Test
    void getAllFunds_deberiaRetornarListaDeFondos() {
        Fondo fondo1 = new Fondo();
        fondo1.setId("1");
        fondo1.setNombre("Fondo1");

        Fondo fondo2 = new Fondo();
        fondo2.setId("2");
        fondo2.setNombre("Fondo2");

        List<Fondo> fondos = Arrays.asList(fondo1, fondo2);

        when(fondoRepository.findAll()).thenReturn(fondos);

        List<Fondo> resultado = fondoService.getAll();

        assertEquals(2, resultado.size());
        assertEquals("Fondo1", resultado.get(0).getNombre());
        assertEquals("Fondo2", resultado.get(1).getNombre());
        verify(fondoRepository, times(1)).findAll();
    }
}
