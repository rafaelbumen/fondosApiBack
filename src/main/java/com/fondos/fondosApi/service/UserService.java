package com.fondos.fondosApi.service;

import com.fondos.fondosApi.dto.UserRequest;
import com.fondos.fondosApi.model.Transaction;
import com.fondos.fondosApi.model.User;
import com.fondos.fondosApi.repository.IUserRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final IUserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(IUserRepository repository) {
        this.repository = repository;
    }

    public User createUser(UserRequest request) {

        if (repository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setNombre(request.getNombre());
        user.setApellidos(request.getApellidos());
        user.setEmail(request.getEmail());
        user.setTelefono(request.getTelefono());
        user.setNotificationMethod(request.getNotificationMethod());
        user.setBalance(500000L);
        user.setTransactions(new ArrayList<>());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // 👈 aquí se encripta

        return repository.save(user);
    }

    public List<User> getAll() {
        return repository.findAll();
    }

    public Optional<User> getById(String id) {
        return repository.findById(id);
    }

    public List<Transaction> getTransacciones(String userId) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        return user.getTransactions();
    }

    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }
}
