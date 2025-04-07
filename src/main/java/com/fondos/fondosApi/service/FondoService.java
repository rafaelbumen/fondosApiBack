package com.fondos.fondosApi.service;

import com.fondos.fondosApi.model.Fondo;
import com.fondos.fondosApi.repository.IFondoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FondoService {

    private final IFondoRepository fondoRepository;

    public FondoService(IFondoRepository fondoRepository) {
        this.fondoRepository = fondoRepository;
    }

    public List<Fondo> getAll() {
        return fondoRepository.findAll();
    }

    public Optional<Fondo> getById(String id) {
        return fondoRepository.findById(id);
    }

    public Fondo save(Fondo fondo) {
        return fondoRepository.save(fondo);
    }
}
