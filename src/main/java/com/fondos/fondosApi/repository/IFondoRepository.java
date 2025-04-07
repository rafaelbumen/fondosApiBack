package com.fondos.fondosApi.repository;

import com.fondos.fondosApi.model.Fondo;

import java.util.List;
import java.util.Optional;

public interface IFondoRepository {
    Fondo save(Fondo fund);
    Optional<Fondo> findById(String id);
    List<Fondo> findAll();
}

