package com.fondos.fondosApi.repository;

import com.fondos.fondosApi.model.User;

import java.util.List;
import java.util.Optional;


public interface IUserRepository {
    User save(User user);
    Optional<User> findById(String id);
    List<User> findAll();
    Optional<User> findByEmail(String email);
}