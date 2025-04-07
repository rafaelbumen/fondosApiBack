package com.fondos.fondosApi.service;

import com.fondos.fondosApi.dto.UserRequest;
import com.fondos.fondosApi.model.Transaction;
import com.fondos.fondosApi.model.User;
import com.fondos.fondosApi.repository.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private IUserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(IUserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    void getTransacciones_deberiaRetornarTransaccionesDelUsuario() {
        // Arrange
        String userId = "user123";
        Transaction tx = new Transaction();
        tx.setId("tx1");

        User user = new User();
        user.setId(userId);
        user.setTransactions(List.of(tx));

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        List<Transaction> resultado = userService.getTransacciones(userId);

        // Assert
        assertEquals(1, resultado.size());
        assertEquals("tx1", resultado.get(0).getId());
        verify(userRepository).findById(userId);
    }

    @Test
    void getTransacciones_usuarioNoExiste_deberiaLanzarExcepcion() {
        String userId = "desconocido";
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            userService.getTransacciones(userId);
        });

        assertEquals("Usuario no encontrado", ex.getMessage());
    }

    @Test
    void createUser_deberiaCrearUsuarioCorrectamente() {
        // Arrange
        String randomEmail = "test_" + UUID.randomUUID() + "@example.com";

        UserRequest request = new UserRequest();
        request.setNombre("Cristian");
        request.setApellidos("Buelvas");
        request.setEmail(randomEmail);
        request.setTelefono("+573001112233");
        request.setNotificationMethod("email");
        request.setPassword("password123");

        // Mock para evitar error por correo duplicado
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        User result = userService.createUser(request);

        // Assert
        assertNotNull(result.getId());
        assertEquals("Cristian", result.getNombre());
        assertEquals("Buelvas", result.getApellidos());
        assertEquals(randomEmail, result.getEmail());
        assertEquals(500000L, result.getBalance());
        assertTrue(result.getTransactions().isEmpty());
        verify(userRepository).save(any(User.class));
    }
}
